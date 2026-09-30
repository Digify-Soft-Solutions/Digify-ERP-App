package com.example.digifyerp.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.ui.graphics.vector.ImageVector
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

data class ExpenseItem(
    val title: String,
    val category: String,
    val amount: Double,
    val paymentMode: String,
    val date: String,
    val icon: ImageVector
)

@Composable
fun ExpensesDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("All") }
    var showAddForm by remember { mutableStateOf(false) }

    var newExpenseTitle by remember { mutableStateOf("") }
    var newExpenseAmount by remember { mutableStateOf("") }

    val expenses = remember {
        mutableStateListOf(
            ExpenseItem("Office Electricity Bill", "Utilities", 1200.0, "Paid via UPI", "28 Sept, 26", Icons.Default.ElectricBolt),
            ExpenseItem("Local Courier & Dispatch", "Logistics", 300.0, "Cash", "25 Sept, 26", Icons.Default.LocalShipping),
            ExpenseItem("Fiber High-Speed Internet", "Office", 999.0, "Bank Transfer", "20 Sept, 26", Icons.Default.Wifi),
            ExpenseItem("Staff Refreshments & Tea", "Pantry", 450.0, "Cash", "18 Sept, 26", Icons.Default.Restaurant),
            ExpenseItem("Invoice Paper & Stationery", "Office", 650.0, "Paid via UPI", "15 Sept, 26", Icons.Default.Receipt)
        )
    }

    val totalExpense = expenses.sumOf { it.amount }

    val filteredExpenses = expenses.filter {
        selectedCategory == "All" || it.category.equals(selectedCategory, ignoreCase = true)
    }

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
                                .background(DigifyRed.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Receipt,
                                contentDescription = null,
                                tint = DigifyRed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Business Expenses",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Track all operational outflows",
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

                // Total Expense Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
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
                            Text(text = "Total Expenses (This Month)", fontSize = 11.sp, color = TextSecondary)
                            Text(
                                text = String.format(Locale.ENGLISH, "₹ %,.2f", totalExpense),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DigifyRed
                            )
                        }

                        Button(
                            onClick = { showAddForm = !showAddForm },
                            colors = ButtonDefaults.buttonColors(containerColor = DigifyRed),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = if (showAddForm) "Cancel" else "Add", fontSize = 12.sp)
                        }
                    }
                }

                // Add Expense Quick Form
                if (showAddForm) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Record New Expense", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newExpenseTitle,
                                onValueChange = { newExpenseTitle = it },
                                label = { Text("Expense Description (e.g. Office Rent)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newExpenseAmount,
                                onValueChange = { newExpenseAmount = it },
                                label = { Text("Amount (₹)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    val amt = newExpenseAmount.toDoubleOrNull() ?: 0.0
                                    if (newExpenseTitle.isNotBlank() && amt > 0) {
                                        expenses.add(
                                            0,
                                            ExpenseItem(
                                                title = newExpenseTitle.trim(),
                                                category = "Office",
                                                amount = amt,
                                                paymentMode = "Cash",
                                                date = "30 Sept, 26",
                                                icon = Icons.Default.Receipt
                                            )
                                        )
                                        newExpenseTitle = ""
                                        newExpenseAmount = ""
                                        showAddForm = false
                                        Toast.makeText(context, "Expense added successfully!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Please enter valid title and amount", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = DigifyGreen)
                            ) {
                                Text("Save Expense")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Category Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("All", "Utilities", "Logistics", "Office", "Pantry").forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DigifyRed.copy(alpha = 0.12f),
                                selectedLabelColor = DigifyRed
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(6.dp))

                // Expense List
                expenses.forEach { item ->
                    if (selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF1F5F9)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = TextPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = item.title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${item.date} • ${item.paymentMode}",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Text(
                                text = String.format(Locale.ENGLISH, "- ₹ %,.2f", item.amount),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DigifyRed
                            )
                        }
                        HorizontalDivider(color = Color(0xFFF8FAFC))
                    }
                }
            }
        }
    }
}
