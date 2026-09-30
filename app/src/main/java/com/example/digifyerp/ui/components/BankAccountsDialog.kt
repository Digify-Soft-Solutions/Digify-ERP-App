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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCode
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
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.theme.TextSecondary
import java.util.Locale

data class BankAccountItem(
    val bankName: String,
    val accountNumber: String,
    val ifsc: String,
    val balance: Double,
    val isPrimary: Boolean
)

@Composable
fun BankAccountsDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var showAddAccount by remember { mutableStateOf(false) }

    var newBankName by remember { mutableStateOf("") }
    var newAccNum by remember { mutableStateOf("") }
    var newBalance by remember { mutableStateOf("") }

    val accounts = remember {
        mutableStateListOf(
            BankAccountItem("HDFC Bank Current Account", "****4892", "HDFC0001234", 52000.0, true),
            BankAccountItem("ICICI Business Account", "****1105", "ICIC0000543", 32500.0, false)
        )
    }

    val totalBankBalance = accounts.sumOf { it.balance }

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
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = DigifyPrimaryBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Bank Accounts",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Manage current accounts & UPI",
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

                // Summary Balance Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
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
                            Text(text = "Total Bank Balance", fontSize = 11.sp, color = TextSecondary)
                            Text(
                                text = String.format(Locale.ENGLISH, "₹ %,.2f", totalBankBalance),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DigifyGreen
                            )
                        }

                        Button(
                            onClick = { showAddAccount = !showAddAccount },
                            colors = ButtonDefaults.buttonColors(containerColor = DigifyPrimaryBlue),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = if (showAddAccount) "Cancel" else "Add Bank", fontSize = 12.sp)
                        }
                    }
                }

                // Add Bank Account Form
                if (showAddAccount) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Link New Bank Account", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newBankName,
                                onValueChange = { newBankName = it },
                                label = { Text("Bank Name (e.g. State Bank of India)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newAccNum,
                                onValueChange = { newAccNum = it },
                                label = { Text("Account Number") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = newBalance,
                                onValueChange = { newBalance = it },
                                label = { Text("Opening Balance (₹)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    val bal = newBalance.toDoubleOrNull() ?: 0.0
                                    if (newBankName.isNotBlank() && newAccNum.isNotBlank()) {
                                        accounts.add(
                                            BankAccountItem(
                                                bankName = newBankName.trim(),
                                                accountNumber = "****" + newAccNum.takeLast(4),
                                                ifsc = "SBIN0002100",
                                                balance = bal,
                                                isPrimary = false
                                            )
                                        )
                                        newBankName = ""
                                        newAccNum = ""
                                        newBalance = ""
                                        showAddAccount = false
                                        Toast.makeText(context, "Bank account linked successfully!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Please enter bank name and account number", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = DigifyGreen)
                            ) {
                                Text("Link Account")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // UPI Banner
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.QrCode, contentDescription = "UPI", tint = DigifyPrimaryBlue, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Primary UPI VPA ID", fontSize = 11.sp, color = TextSecondary)
                            Text(text = "digifysoft@hdfcbank", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFDCFCE7))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = "ACTIVE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DigifyGreen)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(8.dp))

                // Accounts List
                accounts.forEach { acc ->
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
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = acc.bankName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                        if (acc.isPrimary) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(DigifyPrimaryBlue.copy(alpha = 0.1f))
                                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                                            ) {
                                                Text(text = "PRIMARY", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DigifyPrimaryBlue)
                                            }
                                        }
                                    }
                                    Text(text = "A/C: ${acc.accountNumber} • IFSC: ${acc.ifsc}", fontSize = 11.sp, color = TextSecondary)
                                }

                                Text(
                                    text = String.format(Locale.ENGLISH, "₹ %,.2f", acc.balance),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DigifyGreen
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
