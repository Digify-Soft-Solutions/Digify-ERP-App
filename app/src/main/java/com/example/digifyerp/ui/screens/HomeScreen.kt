package com.example.digifyerp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.model.Party
import com.example.digifyerp.model.Transaction
import com.example.digifyerp.theme.DigifyBackground
import com.example.digifyerp.theme.DigifyRed
import com.example.digifyerp.ui.components.AddPartyDialog
import com.example.digifyerp.ui.components.AddSaleDialog
import com.example.digifyerp.ui.components.HomeSubTab
import com.example.digifyerp.ui.components.PartyCard
import com.example.digifyerp.ui.components.QuickLinksCard
import com.example.digifyerp.ui.components.SegmentedTabBar
import com.example.digifyerp.ui.components.ThermalReceiptDialog
import com.example.digifyerp.ui.components.TransactionCard

@Composable
fun HomeScreen(
    transactions: List<Transaction>,
    parties: List<Party>,
    onAddSale: (partyName: String, amount: Double, balance: Double, itemsSummary: String) -> Unit,
    onAddParty: (name: String, phone: String, gst: String, balance: Double, balanceType: com.example.digifyerp.model.BalanceType) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedSubTab by remember { mutableStateOf(HomeSubTab.TRANSACTION_DETAILS) }
    var showAddSaleDialog by remember { mutableStateOf(false) }
    var showAddPartyDialog by remember { mutableStateOf(false) }
    var thermalReceiptTxn by remember { mutableStateOf<Transaction?>(null) }

    Box(modifier = modifier.fillMaxSize().background(DigifyBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Segmented Pills: Transaction Details / Party Details
            item {
                SegmentedTabBar(
                    selectedTab = selectedSubTab,
                    onTabSelected = { selectedSubTab = it }
                )
            }

            // Quick Links Card
            item {
                QuickLinksCard(
                    isPartyTab = selectedSubTab == HomeSubTab.PARTY_DETAILS,
                    onAddTxnClick = { showAddSaleDialog = true },
                    onSaleReportClick = {
                        Toast.makeText(context, "Opening Digify Sale Report...", Toast.LENGTH_SHORT).show()
                    },
                    onImportPartyClick = { showAddPartyDialog = true },
                    onShowAllClick = {
                        Toast.makeText(context, "Digify Soft - All Tools Available", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(6.dp))
            }

            // List based on selected tab
            if (selectedSubTab == HomeSubTab.TRANSACTION_DETAILS) {
                items(transactions, key = { it.id }) { txn ->
                    TransactionCard(
                        transaction = txn,
                        onPrintClick = { thermalReceiptTxn = it },
                        onShareClick = {
                            Toast.makeText(context, "Sharing Invoice #${it.invoiceNumber}...", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            } else {
                items(parties, key = { it.id }) { party ->
                    PartyCard(
                        party = party,
                        onClick = {
                            Toast.makeText(context, "Party: ${it.name} (${it.phoneNumber})", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }

        // Floating Action Button Centered at Bottom: Red Pill matching WA0028 & WA0029
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            if (selectedSubTab == HomeSubTab.TRANSACTION_DETAILS) {
                Button(
                    onClick = { showAddSaleDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DigifyRed),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CurrencyRupee,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Add New Sale",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            } else {
                Button(
                    onClick = { showAddPartyDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DigifyRed),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Add New Party",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        // Add Sale Dialog
        if (showAddSaleDialog) {
            AddSaleDialog(
                onDismiss = { showAddSaleDialog = false },
                onSaveSale = onAddSale
            )
        }

        // Add Party Dialog
        if (showAddPartyDialog) {
            AddPartyDialog(
                onDismiss = { showAddPartyDialog = false },
                onSaveParty = onAddParty
            )
        }

        // Thermal Receipt Dialog
        thermalReceiptTxn?.let { txn ->
            ThermalReceiptDialog(
                transaction = txn,
                onDismiss = { thermalReceiptTxn = null }
            )
        }
    }
}
