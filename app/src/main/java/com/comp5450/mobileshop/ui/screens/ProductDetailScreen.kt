package com.comp5450.mobileshop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.comp5450.mobileshop.data.Product
import com.comp5450.mobileshop.data.ShoeSizeRegion
import com.comp5450.mobileshop.ui.ShopViewModel
import com.comp5450.mobileshop.ui.components.ProductHeroArt
import com.comp5450.mobileshop.ui.theme.AccentOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    vm: ShopViewModel,
    onBack: () -> Unit,
) {
    var colorIndex by remember(product.id) { mutableIntStateOf(0) }
    val region by vm.sizeRegion.collectAsState(initial = ShoeSizeRegion.EU)
    val sizes = remember(region) { vm.sizesForRegion(region) }
    var selectedSize by remember(product.id, region) { mutableStateOf(sizes.getOrNull(2) ?: sizes.first()) }
    var descExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(region) {
        val next = vm.sizesForRegion(region)
        selectedSize = next.getOrNull(2) ?: next.first()
    }

    val color = product.colorOptions.getOrElse(colorIndex) { product.colorOptions.first() }
    val count by vm.cartItemCount.collectAsState(initial = 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        product.categoryLabel,
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* bag is on main tab; icon is decorative / count only */ }) {
                        BadgedBox(
                            badge = {
                                if (count > 0) Badge { Text(count.toString()) }
                            },
                        ) {
                            Icon(Icons.Outlined.ShoppingBag, contentDescription = "Bag")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    formatUsd(product.price),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Button(
                    onClick = {
                        vm.addToBag(product, color.name, selectedSize)
                    },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                    modifier = Modifier.height(52.dp),
                ) {
                    Text("Add to Bag")
                }
            }
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(280.dp),
            ) {
                ProductHeroArt(
                    accent = color.swatch,
                    modifier = Modifier.fillMaxSize(),
                    bottomBias = 0.45f,
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                product.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = AccentOrange, modifier = Modifier.size(20.dp))
                Spacer(Modifier.padding(4.dp))
                Text(
                    "${product.rating} rating",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                if (descExpanded) product.description else product.description.take(90) + if (product.description.length > 90) "…" else "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (descExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
            )
            if (product.description.length > 90) {
                Text(
                    if (descExpanded) "Show less" else "Read More",
                    color = AccentOrange,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { descExpanded = !descExpanded }
                        .padding(top = 4.dp),
                )
            }
            Spacer(Modifier.height(20.dp))
            Text("Select Color", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                product.colorOptions.forEachIndexed { index, option ->
                    val selected = index == colorIndex
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(option.swatch)
                            .border(
                                width = if (selected) 3.dp else 1.dp,
                                color = if (selected) AccentOrange else Color.LightGray,
                                shape = CircleShape,
                            )
                            .clickable { colorIndex = index },
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("Size", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ShoeSizeRegion.entries.forEach { r ->
                    val sel = r == region
                    Text(
                        r.name,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (sel) AccentOrange else Color(0xFFECECEC))
                            .clickable { vm.setSizeRegion(r) }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        color = if (sel) Color.White else Color.Black,
                        fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                sizes.forEach { s ->
                    val sel = s == selectedSize
                    Text(
                        s,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (sel) AccentOrange.copy(alpha = 0.2f) else Color(0xFFF5F5F5))
                            .border(
                                1.dp,
                                if (sel) AccentOrange else Color.Transparent,
                                RoundedCornerShape(20.dp),
                            )
                            .clickable { selectedSize = s }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        color = if (sel) AccentOrange else Color.Black,
                        fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
        }
    }
}
