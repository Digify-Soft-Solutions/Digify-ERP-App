package com.example.digifyerp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.model.Item
import com.example.digifyerp.theme.DigifyBackground
import com.example.digifyerp.theme.DigifyGreen
import com.example.digifyerp.theme.DigifyPrimaryBlue
import com.example.digifyerp.theme.DigifyRed
import com.example.digifyerp.theme.TextMuted
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.theme.TextSecondary
import com.example.digifyerp.ui.components.AddItemDialog
import java.util.Locale

enum class ItemFilter {
    ALL, LOW_STOCK, SERVICES, PRODUCTS
}

@Composable
fun ItemsScreen(
    items: List<Item>,
    onAddItem: (name: String, salePrice: Double, purchasePrice: Double, stock: Double, unit: String) -> Unit,
    onDeleteItem: (itemId: String) -> Unit = {},
    onAdjustStock: (itemId: String, delta: Double) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showAddItemDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(ItemFilter.ALL) }

    // Filter items based on search and selected filter chip
    val filteredItems = items.filter { item ->
        val matchesQuery = searchQuery.isBlank() ||
                item.name.contains(searchQuery, ignoreCase = true) ||
                item.itemCode.contains(searchQuery, ignoreCase = true) ||
                item.hsnCode.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (selectedFilter) {
            ItemFilter.ALL -> true
            ItemFilter.LOW_STOCK -> item.currentStock <= item.minStockAlert
            ItemFilter.SERVICES -> item.unit.equals("YEAR", ignoreCase = true) || item.unit.equals("SERVICE", ignoreCase = true)
            ItemFilter.PRODUCTS -> !item.unit.equals("YEAR", ignoreCase = true) && !item.unit.equals("SERVICE", ignoreCase = true)
        }

        matchesQuery && matchesFilter
    }

    val totalStockValue = items.sumOf { it.currentStock * if (it.purchasePrice > 0) it.purchasePrice else it.salePrice }
    val lowStockCount = items.count { it.currentStock <= it.minStockAlert }

    Box(modifier = modifier.fillMaxSize().background(DigifyBackground)) {
        if (items.isEmpty()) {
            // Empty State (WA0031)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFE2EAF4)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AllInbox,
                        contentDescription = "Empty Inventory",
                        tint = Color(0xFF8CA3C3),
                        modifier = Modifier.size(90.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Hey! You have not added any items yet.",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Add your first item now.",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showAddItemDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DigifyRed),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AllInbox,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add New Item",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 70.dp)
                        .clickable {
                            Toast.makeText(context, "Opening Digify Soft Video Tutorial...", Toast.LENGTH_SHORT).show()
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(DigifyRed.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = null,
                                tint = DigifyRed,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "How to add items on Digify Soft?",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Watch Video",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DigifyRed
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = DigifyRed,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Inventory Screen with Search, KPI Summary, Filter Chips & Cards
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 10.dp, bottom = 100.dp)
            ) {
                // 1. Search Bar
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search item name, code or HSN...", fontSize = 13.sp, color = TextMuted) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = DigifyPrimaryBlue)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted)
                                }
                            } else {
                                IconButton(onClick = {
                                    Toast.makeText(context, "Barcode Scanner Ready", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(imageVector = Icons.Default.QrCode, contentDescription = "Barcode", tint = DigifyPrimaryBlue)
                                }
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = DigifyPrimaryBlue,
                            unfocusedBorderColor = Color(0xFFE2E8F0)
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // 2. Summary KPI Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Total Stock Value",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = String.format(Locale.ENGLISH, "₹ %,.2f", totalStockValue),
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DigifyPrimaryBlue
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(32.dp)
                                    .background(Color(0xFFE2E8F0))
                            )

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Total Items",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${items.size}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(32.dp)
                                    .background(Color(0xFFE2E8F0))
                            )

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Low Stock Alert",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (lowStockCount > 0) DigifyRed else TextSecondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "$lowStockCount Items",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (lowStockCount > 0) DigifyRed else DigifyGreen
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // 3. Filter Chips Row
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedFilter == ItemFilter.ALL,
                                onClick = { selectedFilter = ItemFilter.ALL },
                                label = { Text("All (${items.size})", fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DigifyPrimaryBlue.copy(alpha = 0.15f),
                                    selectedLabelColor = DigifyPrimaryBlue
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == ItemFilter.LOW_STOCK,
                                onClick = { selectedFilter = ItemFilter.LOW_STOCK },
                                label = { Text("Low Stock ($lowStockCount)", fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DigifyRed.copy(alpha = 0.15f),
                                    selectedLabelColor = DigifyRed
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == ItemFilter.SERVICES,
                                onClick = { selectedFilter = ItemFilter.SERVICES },
                                label = { Text("Services", fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DigifyPrimaryBlue.copy(alpha = 0.15f),
                                    selectedLabelColor = DigifyPrimaryBlue
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == ItemFilter.PRODUCTS,
                                onClick = { selectedFilter = ItemFilter.PRODUCTS },
                                label = { Text("Physical Products", fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DigifyPrimaryBlue.copy(alpha = 0.15f),
                                    selectedLabelColor = DigifyPrimaryBlue
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // 4. Items List
                if (filteredItems.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No items match your filter",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                } else {
                    items(filteredItems, key = { it.id }) { item ->
                        val isLowStock = item.currentStock <= item.minStockAlert
                        val itemStockValue = item.currentStock * if (item.purchasePrice > 0) item.purchasePrice else item.salePrice

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                            ) {
                                // Top row: Item Name (constrained with weight) & Stock Badge
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column(modifier = Modifier.weight(1f).padding(end = 10.dp)) {
                                        Text(
                                            text = item.name,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (item.itemCode.isNotBlank()) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(Color(0xFFF1F5F9))
                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = "CODE: ${item.itemCode}",
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = TextSecondary
                                                    )
                                                }
                                            }
                                            if (item.hsnCode.isNotBlank()) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(Color(0xFFF1F5F9))
                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = "HSN: ${item.hsnCode}",
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = TextSecondary
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // Stock Pill Badge
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (isLowStock) Color(0xFFFEE2E2) else Color(0xFFDCFCE7)
                                            )
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (isLowStock) {
                                                Icon(
                                                    imageVector = Icons.Default.WarningAmber,
                                                    contentDescription = null,
                                                    tint = DigifyRed,
                                                    modifier = Modifier.size(13.dp)
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                            }
                                            Text(
                                                text = if (isLowStock) "Low: ${item.currentStock.toInt()} ${item.unit}" else "${item.currentStock.toInt()} ${item.unit}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isLowStock) DigifyRed else DigifyGreen
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = Color(0xFFF8FAFC))
                                Spacer(modifier = Modifier.height(10.dp))

                                // Pricing & Stock Value Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "Sale Price",
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                        Text(
                                            text = String.format(Locale.ENGLISH, "₹ %,.2f", item.salePrice),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = "Purchase Price",
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                        Text(
                                            text = String.format(Locale.ENGLISH, "₹ %,.2f", item.purchasePrice),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = TextSecondary
                                        )
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "Stock Value",
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                        Text(
                                            text = String.format(Locale.ENGLISH, "₹ %,.2f", itemStockValue),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = DigifyPrimaryBlue
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = Color(0xFFF1F5F9))

                                // Bottom Actions Row: Quick Stock Adjust (+/-), Share, Delete
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Stock Increment/Decrement controls
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFF8FAFC))
                                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                onAdjustStock(item.id, -1.0)
                                                Toast.makeText(context, "${item.name} stock decreased (-1)", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Remove,
                                                contentDescription = "Minus Stock",
                                                tint = TextPrimary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        Text(
                                            text = "${item.currentStock.toInt()} ${item.unit}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )

                                        IconButton(
                                            onClick = {
                                                onAdjustStock(item.id, 1.0)
                                                Toast.makeText(context, "${item.name} stock increased (+1)", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Add Stock",
                                                tint = DigifyGreen,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }

                                    // Action buttons: Share & Delete
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            onClick = {
                                                Toast.makeText(context, "Item Details shared via WhatsApp!", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = "Share",
                                                tint = DigifyPrimaryBlue,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(4.dp))

                                        IconButton(
                                            onClick = {
                                                onDeleteItem(item.id)
                                                Toast.makeText(context, "${item.name} removed from inventory", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.DeleteOutline,
                                                contentDescription = "Delete",
                                                tint = DigifyRed,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Centered FAB for adding more items
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Button(
                    onClick = { showAddItemDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DigifyRed),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AllInbox,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add New Item",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        // Add Item Dialog
        if (showAddItemDialog) {
            AddItemDialog(
                onDismiss = { showAddItemDialog = false },
                onSaveItem = onAddItem
            )
        }
    }
}
