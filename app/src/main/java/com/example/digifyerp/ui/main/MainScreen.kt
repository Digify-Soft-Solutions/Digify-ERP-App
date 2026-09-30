package com.example.digifyerp.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.digifyerp.data.DigifyErpRepository
import com.example.digifyerp.theme.DigifyBackground
import com.example.digifyerp.theme.DigifyPrimaryBlue
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.ui.components.AppTab
import com.example.digifyerp.ui.components.DigifyBottomBar
import com.example.digifyerp.ui.components.DigifyTopAppBar
import com.example.digifyerp.ui.screens.DashboardScreen
import com.example.digifyerp.ui.screens.GetDesktopScreen
import com.example.digifyerp.ui.screens.HomeScreen
import com.example.digifyerp.ui.screens.ItemsScreen
import com.example.digifyerp.ui.screens.LoginScreen
import com.example.digifyerp.ui.screens.MenuScreen

@Composable
fun MainScreen(
    repository: DigifyErpRepository = remember { DigifyErpRepository() },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isLoggedIn by repository.isLoggedIn.collectAsStateWithLifecycle()
    var currentTab by remember { mutableStateOf(AppTab.HOME) }
    var showStoreDialog by remember { mutableStateOf(false) }

    val storeName by repository.storeName.collectAsStateWithLifecycle()
    val transactions by repository.transactions.collectAsStateWithLifecycle()
    val parties by repository.parties.collectAsStateWithLifecycle()
    val items by repository.items.collectAsStateWithLifecycle()
    val dashboardStats by repository.dashboardStats.collectAsStateWithLifecycle()

    var editedStoreName by remember(storeName) { mutableStateOf(storeName) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showReportsDialog by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen(
            onLoginSuccess = { phone, businessName ->
                repository.login(phone, businessName)
            },
            onSkipToDemo = {
                repository.login("+91 98765 43210", "Gautam malik")
            }
        )
        return
    }

    Scaffold(
        topBar = {
            DigifyTopAppBar(
                storeName = storeName,
                onStoreClick = { showStoreDialog = true },
                onNotificationClick = { showNotificationsDialog = true },
                onSettingsClick = { showSettingsDialog = true }
            )
        },
        bottomBar = {
            DigifyBottomBar(
                currentTab = currentTab,
                onTabSelected = { currentTab = it }
            )
        },
        containerColor = DigifyBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.HOME -> {
                    HomeScreen(
                        transactions = transactions,
                        parties = parties,
                        onAddSale = { partyName, amount, balance, itemsSummary ->
                            repository.addSale(partyName, amount, balance, itemsSummary)
                            Toast.makeText(context, "Sale recorded for $partyName!", Toast.LENGTH_SHORT).show()
                        },
                        onAddParty = { name, phone, gst, balance, balanceType ->
                            repository.addParty(name, phone, gst, balance, balanceType)
                            Toast.makeText(context, "Party $name added successfully!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                AppTab.DASHBOARD -> {
                    DashboardScreen(
                        stats = dashboardStats,
                        onOpenReports = { showReportsDialog = true }
                    )
                }

                AppTab.ITEMS -> {
                    ItemsScreen(
                        items = items,
                        onAddItem = { name, salePrice, purchasePrice, stock, unit ->
                            repository.addItem(name, salePrice, purchasePrice, stock, unit)
                            Toast.makeText(context, "Item '$name' added to Digify Inventory!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                AppTab.MENU -> {
                    MenuScreen(
                        onNavigateToDesktop = { currentTab = AppTab.GET_DESKTOP },
                        onOpenReports = { showReportsDialog = true },
                        onOpenSettings = { showSettingsDialog = true },
                        onLogout = { repository.logout() }
                    )
                }

                AppTab.GET_DESKTOP -> {
                    GetDesktopScreen()
                }
            }
        }
    }

    // Switch Store / Business Name Dialog
    if (showStoreDialog) {
        AlertDialog(
            onDismissRequest = { showStoreDialog = false },
            title = {
                Text("Business Profile", fontWeight = FontWeight.Bold, color = TextPrimary)
            },
            text = {
                Column {
                    Text("Change business / firm name:", fontSize = 13.sp, color = TextPrimary)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = editedStoreName,
                        onValueChange = { editedStoreName = it },
                        label = { Text("Store / Company Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editedStoreName.isNotBlank()) {
                            repository.updateStoreName(editedStoreName.trim())
                        }
                        showStoreDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DigifyPrimaryBlue)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStoreDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Notifications Dialog
    if (showNotificationsDialog) {
        com.example.digifyerp.ui.components.NotificationsDialog(
            onDismiss = { showNotificationsDialog = false }
        )
    }

    // Settings Dialog
    if (showSettingsDialog) {
        com.example.digifyerp.ui.components.SettingsDialog(
            currentStoreName = storeName,
            onSaveStoreName = { repository.updateStoreName(it) },
            onDismiss = { showSettingsDialog = false }
        )
    }

    // Reports Dialog
    if (showReportsDialog) {
        com.example.digifyerp.ui.components.ReportsDialog(
            onDismiss = { showReportsDialog = false }
        )
    }
}
