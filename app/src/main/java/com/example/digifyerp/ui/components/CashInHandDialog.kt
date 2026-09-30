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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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

data class CashEntry(
    val title: String,
    val time: String,
    val amount: Double,
    val isInflow: Boolean
)

@Composable
fun CashInHandDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var currentCash by remember { mutableDoubleStateOf(14250.0) }
    var showAdjustForm by remember { mutableStateOf(false) }
    var adjustAmount by remember { mutableStateOf("") }
    var adjustNote by remember { mutableStateOf("") }
    var isAddingCash by remember { mutableStateOf(true) }

    val entries = remember {
        mutableStateListOf(
            CashEntry("Cash Sale (Counter Order)", "Today 04:30 PM", 2500.0, true),
            CashEntry("Cash Received - Kush", "Today 02:15 PM", 2500.0, true),
            CashEntry("Office Snacks & Tea", "Today 01:10 PM", 500.0, false),
            CashEntry("Local Courier Charges", "Today 11:20 AM", 300.0, false),
            CashEntry("Opening Cash Balance", "Today 09:00 AM", 10050.0, true)
        )
    }

    val todayInflow = entries.filter { it.isInflow }.sumOf { it.amount }
    val todayOutflow = entries.filter { !it.isInflow }.sumOf { it.amount }

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
                                .background(DigifyGreen.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = DigifyGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Cash In-Hand",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Physical drawer & cash register",
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

                // Current Cash In Hand Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = "Current Cash In Hand", fontSize = 11.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = String.format(Locale.ENGLISH, "₹ %,.2f", currentCash),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = DigifyGreen
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Today In: +₹ ${todayInflow.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = DigifyGreen)
                            Text(text = "Today Out: -₹ ${todayOutflow.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = DigifyRed)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Cash In / Cash Out action buttons
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            isAddingCash = true
                            showAdjustForm = true
                        },
                        modifier = Modifier.weight(1f).height(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DigifyGreen),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "+ Cash In", fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            isAddingCash = false
                            showAdjustForm = true
                        },
                        modifier = Modifier.weight(1f).height(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DigifyRed),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "- Cash Out", fontSize = 12.sp)
                    }
                }

                // Adjust Cash Form
                if (showAdjustForm) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = if (isAddingCash) "Record Cash Inflow (+)" else "Record Cash Outflow (-)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAddingCash) DigifyGreen else DigifyRed
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = adjustNote,
                                onValueChange = { adjustNote = it },
                                label = { Text("Reason / Description") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = adjustAmount,
                                onValueChange = { adjustAmount = it },
                                label = { Text("Amount (₹)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    val amt = adjustAmount.toDoubleOrNull() ?: 0.0
                                    if (adjustNote.isNotBlank() && amt > 0) {
                                        if (isAddingCash) {
                                            currentCash += amt
                                            entries.add(0, CashEntry(adjustNote.trim(), "Just now", amt, true))
                                            Toast.makeText(context, "₹ $amt added to Cash Register!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            currentCash = (currentCash - amt).coerceAtLeast(0.0)
                                            entries.add(0, CashEntry(adjustNote.trim(), "Just now", amt, false))
                                            Toast.makeText(context, "₹ $amt deducted from Cash Register!", Toast.LENGTH_SHORT).show()
                                        }
                                        adjustAmount = ""
                                        adjustNote = ""
                                        showAdjustForm = false
                                    } else {
                                        Toast.makeText(context, "Please enter valid reason and amount", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = if (isAddingCash) DigifyGreen else DigifyRed)
                            ) {
                                Text("Confirm Entry")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(8.dp))

                // Cash Entries Ledger
                Text(text = "Today's Cash Ledger", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(6.dp))

                entries.forEach { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (entry.isInflow) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (entry.isInflow) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                    contentDescription = null,
                                    tint = if (entry.isInflow) DigifyGreen else DigifyRed,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = entry.title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                Text(text = entry.time, fontSize = 11.sp, color = TextSecondary)
                            }
                        }

                        Text(
                            text = (if (entry.isInflow) "+ " else "- ") + String.format(Locale.ENGLISH, "₹ %,.2f", entry.amount),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (entry.isInflow) DigifyGreen else DigifyRed
                        )
                    }
                    HorizontalDivider(color = Color(0xFFF8FAFC))
                }
            }
        }
    }
}
