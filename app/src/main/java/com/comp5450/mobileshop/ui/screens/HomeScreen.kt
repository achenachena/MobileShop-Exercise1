package com.comp5450.mobileshop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.comp5450.mobileshop.data.Product
import com.comp5450.mobileshop.data.SampleData
import com.comp5450.mobileshop.ui.ShopViewModel
import com.comp5450.mobileshop.ui.components.ProductHeroArt
import com.comp5450.mobileshop.ui.theme.AccentOrange
import com.comp5450.mobileshop.ui.theme.CategoryInactiveBg
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: ShopViewModel,
    onProductClick: (Product) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showSearch by remember { mutableStateOf(false) }
    var tabIndex by remember { mutableIntStateOf(0) }

    val search by vm.searchQuery.collectAsState(initial = "")
    val selectedCat by vm.selectedCategoryId.collectAsState(initial = SampleData.categories.first().first)
    val lines by vm.cartLines.collectAsState(initial = emptyList())
    val total by vm.cartTotal.collectAsState(initial = 0.0)
    val count by vm.cartItemCount.collectAsState(initial = 0)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(Modifier.padding(24.dp)) {
                    Text("Menu", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))
                    Text("Shop", Modifier.padding(vertical = 12.dp))
                    Text("Orders", Modifier.padding(vertical = 12.dp))
                    Text("Support", Modifier.padding(vertical = 12.dp))
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showSearch = !showSearch }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    NavigationBarItem(
                        selected = tabIndex == 0,
                        onClick = { tabIndex = 0 },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = AccentOrange.copy(alpha = 0.2f),
                            selectedIconColor = AccentOrange,
                            selectedTextColor = AccentOrange,
                        ),
                    )
                    NavigationBarItem(
                        selected = tabIndex == 1,
                        onClick = { tabIndex = 1 },
                        icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = null) },
                        label = { Text("Saved") },
                    )
                    NavigationBarItem(
                        selected = tabIndex == 2,
                        onClick = { tabIndex = 2 },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (count > 0) {
                                        Badge { Text(count.toString()) }
                                    }
                                },
                            ) {
                                Icon(Icons.Outlined.ShoppingBag, contentDescription = "Bag")
                            }
                        },
                        label = { Text("Bag") },
                    )
                    NavigationBarItem(
                        selected = tabIndex == 3,
                        onClick = { tabIndex = 3 },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Profile") },
                    )
                }
            },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                if (showSearch) {
                    OutlinedTextField(
                        value = search,
                        onValueChange = vm::setSearchQuery,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        placeholder = { Text("Search shoes") },
                        singleLine = true,
                    )
                }
                when (tabIndex) {
                    0 -> HomeTabContent(
                        selectedCategoryId = selectedCat,
                        onCategorySelected = vm::selectCategory,
                        products = vm.filteredProducts(),
                        onProductClick = onProductClick,
                        onShopFeatured = { onProductClick(SampleData.featuredProduct) },
                        onQuickAdd = vm::quickAddFromGrid,
                    )
                    1 -> PlaceholderTab("Favorites", "Save items (placeholder for this exercise).")
                    2 -> BagTabScreen(lines = lines, total = total, onRemove = vm::removeLine)
                    3 -> PlaceholderTab("Profile", "Profile & settings (placeholder).")
                }
            }
        }
    }
}

@Composable
private fun HomeTabContent(
    selectedCategoryId: String,
    onCategorySelected: (String) -> Unit,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onShopFeatured: () -> Unit,
    onQuickAdd: (Product) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        FeaturedBanner(onShopFeatured = onShopFeatured)
        Spacer(Modifier.height(16.dp))
        Text(
            "Categories",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SampleData.categories.forEach { (id, label) ->
                val selected = id == selectedCategoryId
                val bg = if (selected) AccentOrange else CategoryInactiveBg
                val fg = if (selected) Color.White else Color.Black
                Text(
                    text = label,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(bg)
                        .clickable { onCategorySelected(id) }
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    color = fg,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            "New Men's",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(products, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    onClick = { onProductClick(product) },
                    onAdd = { onQuickAdd(product) },
                )
            }
        }
    }
}

@Composable
private fun FeaturedBanner(onShopFeatured: () -> Unit) {
    val featured = SampleData.featuredProduct
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(160.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(12.dp),
            ) {
                Column(Modifier.align(Alignment.CenterStart)) {
                    Text(
                        "New Release",
                        color = AccentOrange,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        featured.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onShopFeatured,
                        colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text("Shop Now")
                    }
                }
            }
            Box(
                Modifier
                    .weight(0.95f)
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                ProductHeroArt(
                    accent = featured.colorOptions.firstOrNull()?.swatch ?: AccentOrange,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onAdd: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            ) {
                ProductHeroArt(
                    accent = product.colorOptions.firstOrNull()?.swatch ?: AccentOrange,
                    modifier = Modifier.fillMaxSize(),
                )
                IconButton(
                    onClick = onAdd,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                ) {
                    Text("+", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Column(Modifier.padding(10.dp)) {
                Text(
                    product.categoryLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                )
                Text(
                    product.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    formatUsd(product.price),
                    color = AccentOrange,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun PlaceholderTab(title: String, body: String) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(
            body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
        )
    }
}
