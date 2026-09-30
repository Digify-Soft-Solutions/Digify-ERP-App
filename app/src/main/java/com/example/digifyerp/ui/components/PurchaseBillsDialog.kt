package com.example.digifyerp.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.digifyerp.theme.DigifyGreen
import com.example.digifyerp.theme.DigifyPrimaryBlue
import com.example.digifyerp.theme.DigifyRed
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.theme.TextSecondary
import java.util.Locale

data class PurchaseBill(
    val billNumber: String,
    val vendorName: String,
    val itemsSummary: String,
    val amount: Double,
    val isPaid: Boolean,
    val date: String
)

@Composable
fun PurchaseBillsDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf("All") }
    var showAddForm by remember { mutableStateOf(false) }

    var newVendor by remember { mutableStateOf("") }
    var newAmount by remember { mutableStateOf("") }
    var newItems by remember { mutableStateOf("") }

    val bills = remember {
        mutableStateListOf(
            PurchaseBill("PB-101", "SuperTech Hardware Dist.", "10x Barcode Scanners 2D", 8500.0, true, "28 Sept, 26"),
            PurchaseBill("PB-102", "National Paper Mills", "50x Thermal Rolls 80mm", 4200.0, false, "25 Sept, 26"),
            PurchaseBill("PB-103", "Global POS Tech Solutions", "2x Bluetooth POS Printers", 5800.0, true, "20 Sept, 26")
        )
    }

    val totalPurchases = bills.sumOf { it.amount }
    val pendingAmount = bills.filter { !it.isPaid }.sumOf { it.amount }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(DigifyPrimaryBlue.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                tint = DigifyPrimaryBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Purchase Bills",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Vendor purchases & inward stock",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Purchase Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Total Purchases", fontSize = 11.sp, color = TextSecondary)
                            Text(
                                text = String.format(Locale.ENGLISH, "₹ %,.2f", totalPurchases),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Pending to Pay", fontSize = 11.sp, color = DigifyRed)
                            Text(
                                text = String.format(Locale.ENGLISH, "₹ %,.2f", pendingAmount),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = DigifyRed
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Add Bill Button / Form Toggle
                Button(
                    onClick = { showAddForm = !showAddForm },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DigifyPrimaryBlue)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (showAddForm) "Cancel" else "Add Purchase Bill")
                }

                if (showAddForm) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "New Purchase Bill", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newVendor,
                                onValueChange = { newVendor = it },
                                label = { Text("Vendor / Supplier Name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newItems,
                                onValueChange = { newItems = it },
                                label = { Text("Items (e.g. 5x Thermal Printers)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newAmount,
                                onValueChange = { newAmount = it },
                                label = { Text("Total Bill Amount (₹)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    val amt = newAmount.toDoubleOrNull() ?: 0.0
                                    if (newVendor.isNotBlank() && amt > 0) {
                                        bills.add(
                                            0,
                                            PurchaseBill(
                                                billNumber = "PB-${bills.size + 101}",
                                                vendorName = newVendor.trim(),
                                                itemsSummary = newItems.ifBlank { "General Inventory Supplies" },
                                                amount = amt,
                                                isPaid = true,
                                                date = "30 Sept, 26"
                                            )
                                        )
                                        newVendor = ""
                                        newAmount = ""
                                        newItems = ""
                                        showAddForm = false
                                        Toast.makeText(context, "Purchase Bill added!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Please fill vendor name & amount", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = DigifyGreen)
                            ) {
                                Text("Save Purchase Bill")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Filters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("All", "Paid", "Pending").forEach { f ->
                        FilterChip(
                            selected = selectedFilter == f,
                            onClick = { selectedFilter = f },
                            label = { Text(f, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DigifyPrimaryBlue.copy(alpha = 0.12f),
                                selectedLabelColor = DigifyPrimaryBlue
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(8.dp))

                // Bills List
                bills.forEach { bill ->
                    val show = when (selectedFilter) {
                        "All" -> true
                        "Paid" -> bill.isPaid
                        "Pending" -> !bill.isPaid
                        else -> true
                    }

                    if (show) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = CardDefaults.outlinedCardBorder()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = bill.vendorName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                        Text(text = "Bill #${bill.billNumber} • ${bill.date}", fontSize = 11.sp, color = TextSecondary)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (bill.isPaid) Color(0xFFDCFCE7) else Color(0xFFFEE2E2))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = if (bill.isPaid) "PAID" else "UNPAID",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (bill.isPaid) DigifyGreen else DigifyRed
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = bill.itemsSummary, fontSize = 11.sp, color = TextSecondary, modifier = Modifier.weight(1f))
                                    Text(
                                        text = String.format(Locale.ENGLISH, "₹ %,.2f", bill.amount),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
