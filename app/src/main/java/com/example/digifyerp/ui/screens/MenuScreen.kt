package com.example.digifyerp.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.R
import com.example.digifyerp.model.Transaction
import com.example.digifyerp.theme.DealBannerBg
import com.example.digifyerp.theme.DesktopBannerBg
import com.example.digifyerp.theme.DigifyBackground
import com.example.digifyerp.theme.DigifyPrimaryBlue
import com.example.digifyerp.theme.DigifyRed
import com.example.digifyerp.theme.TextMuted
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.theme.TextSecondary
import com.example.digifyerp.ui.components.BankAccountsDialog
import com.example.digifyerp.ui.components.CashInHandDialog
import com.example.digifyerp.ui.components.ExpensesDialog
import com.example.digifyerp.ui.components.OnlineStoreDialog
import com.example.digifyerp.ui.components.PurchaseBillsDialog
import com.example.digifyerp.ui.components.SaleInvoicesDialog
import com.example.digifyerp.ui.components.ThermalReceiptDialog

@Composable
fun MenuScreen(
    transactions: List<Transaction> = emptyList(),
    storeName: String = "Gautam malik",
    onNavigateToDesktop: () -> Unit = {},
    onOpenReports: () -> Unit = {},
    onOpenSettings: () -> Unit = {},
    onLogout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var carouselIndex by remember { mutableIntStateOf(0) }

    var isSaleExpanded by remember { mutableStateOf(false) }
    var isPurchaseExpanded by remember { mutableStateOf(false) }
    var isGrowExpanded by remember { mutableStateOf(false) }
    var isSupportExpanded by remember { mutableStateOf(false) }

    var showOnlineStoreDialog by remember { mutableStateOf(false) }
    var showExpensesDialog by remember { mutableStateOf(false) }
    var showPurchaseBillsDialog by remember { mutableStateOf(false) }
    var showSaleInvoicesDialog by remember { mutableStateOf(false) }
    var showBankAccountsDialog by remember { mutableStateOf(false) }
    var showCashInHandDialog by remember { mutableStateOf(false) }
    var selectedReceiptTxn by remember { mutableStateOf<Transaction?>(null) }

    Box(modifier = modifier.fillMaxSize().background(DigifyBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp)
        ) {
            // Carousel Banner Section (WA0024 & WA0032)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { carouselIndex = (carouselIndex + 1) % 2 },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    if (carouselIndex == 0) {
                        // Slide 1: Deal Sale (WA0032)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(Color(0xFF881A43), Color(0xFF530C29))
                                    )
                                )
                                .padding(18.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "DEAL SALE",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFFC7F9CC))
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "HURRY UP!",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1B4332)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Now, become a Digify Soft Member and get exclusive benefits at upto 60% off!",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.9f),
                                    lineHeight = 18.sp
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Button(
                                    onClick = {
                                        Toast.makeText(context, "Opening Digify Soft Deals...", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                    shape = RoundedCornerShape(20.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "Buy Now",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DigifyRed
                                    )
                                }
                            }
                        }
                    } else {
                        // Slide 2: Desktop App Promo (WA0024)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DesktopBannerBg)
                                .padding(18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Join 10,000+ Store Owners Already Using Our Desktop App",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary,
                                        lineHeight = 19.sp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(
                                        onClick = onNavigateToDesktop,
                                        colors = ButtonDefaults.buttonColors(containerColor = DigifyRed),
                                        shape = RoundedCornerShape(20.dp),
                                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "Try for Free",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Icon(
                                    imageVector = Icons.Default.Computer,
                                    contentDescription = "Desktop App",
                                    tint = DigifyPrimaryBlue,
                                    modifier = Modifier.size(64.dp)
                                )
                            }
                        }
                    }
                }

                // Carousel Dots
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (carouselIndex == 0) 18.dp else 8.dp, 6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (carouselIndex == 0) DigifyPrimaryBlue else Color(0xFFD0D7DE))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(if (carouselIndex == 1) 18.dp else 8.dp, 6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (carouselIndex == 1) DigifyPrimaryBlue else Color(0xFFD0D7DE))
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Section 1: My Business (WA0024 & WA0032)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 12.dp)) {
                        Text(
                            text = "My Business",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Sale Item with Accordion
                        MenuExpandableItem(
                            icon = Icons.Default.CurrencyRupee,
                            title = "Sale",
                            isExpanded = isSaleExpanded,
                            onToggle = { isSaleExpanded = !isSaleExpanded }
                        )
                        AnimatedVisibility(visible = isSaleExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF8FAFC))
                                    .padding(start = 56.dp, top = 6.dp, bottom = 6.dp, end = 16.dp)
                            ) {
                                SubMenuItem("Sale Invoices") { showSaleInvoicesDialog = true }
                                SubMenuItem("Estimate / Quotation") { showSaleInvoicesDialog = true }
                                SubMenuItem("Payment In") { showSaleInvoicesDialog = true }
                                SubMenuItem("Sale Order") { showSaleInvoicesDialog = true }
                                SubMenuItem("Delivery Challan") { showSaleInvoicesDialog = true }
                                SubMenuItem("Sale Return / Credit Note") { showSaleInvoicesDialog = true }
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // Purchase Item with Accordion
                        MenuExpandableItem(
                            icon = Icons.Default.ShoppingCart,
                            title = "Purchase",
                            isExpanded = isPurchaseExpanded,
                            onToggle = { isPurchaseExpanded = !isPurchaseExpanded }
                        )
                        AnimatedVisibility(visible = isPurchaseExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF8FAFC))
                                    .padding(start = 56.dp, top = 6.dp, bottom = 6.dp, end = 16.dp)
                            ) {
                                SubMenuItem("Purchase Bills") { showPurchaseBillsDialog = true }
                                SubMenuItem("Payment Out") { showPurchaseBillsDialog = true }
                                SubMenuItem("Purchase Order") { showPurchaseBillsDialog = true }
                                SubMenuItem("Purchase Return / Debit Note") { showPurchaseBillsDialog = true }
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // Expenses
                        MenuLinkItem(
                            icon = Icons.Default.Receipt,
                            title = "Expenses",
                            onClick = { showExpensesDialog = true }
                        )

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // My Online Store
                        MenuLinkItem(
                            icon = Icons.Default.Store,
                            title = "My Online Store",
                            onClick = { showOnlineStoreDialog = true }
                        )

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // Reports
                        MenuLinkItem(
                            icon = Icons.Default.Assessment,
                            title = "Reports",
                            onClick = onOpenReports
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Section 2: Cash & Bank (WA0024 & WA0032)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 12.dp)) {
                        Text(
                            text = "Cash & Bank",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        MenuLinkItem(
                            icon = Icons.Default.AccountBalance,
                            title = "Bank Accounts",
                            onClick = { showBankAccountsDialog = true }
                        )

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        MenuLinkItem(
                            icon = Icons.Default.Payments,
                            title = "Cash In-Hand",
                            onClick = { showCashInHandDialog = true }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Section 3: More Features & Support (WA0033)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 12.dp)) {
                        MenuExpandableItem(
                            icon = Icons.AutoMirrored.Filled.TrendingUp,
                            title = "Grow Your Business",
                            isExpanded = isGrowExpanded,
                            onToggle = { isGrowExpanded = !isGrowExpanded }
                        )
                        AnimatedVisibility(visible = isGrowExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF8FAFC))
                                    .padding(start = 56.dp, top = 6.dp, bottom = 6.dp, end = 16.dp)
                            ) {
                                SubMenuItem("Digital Business Cards") {}
                                SubMenuItem("Promotional SMS Campaigns") {}
                                SubMenuItem("Customer Greeting Cards") {}
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        MenuLinkItem(
                            icon = Icons.Default.Settings,
                            title = "Settings",
                            onClick = onOpenSettings
                        )

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        MenuExpandableItem(
                            icon = Icons.Default.Headphones,
                            title = "Help & Support",
                            isExpanded = isSupportExpanded,
                            onToggle = { isSupportExpanded = !isSupportExpanded }
                        )
                        AnimatedVisibility(visible = isSupportExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF8FAFC))
                                    .padding(start = 56.dp, top = 6.dp, bottom = 6.dp, end = 16.dp)
                            ) {
                                SubMenuItem("WhatsApp Support: +91 74250 16636") {
                                    Toast.makeText(context, "Opening WhatsApp Support (+91 74250 16636)...", Toast.LENGTH_SHORT).show()
                                }
                                SubMenuItem("Call Customer Care: +91 74250 16636") {
                                    Toast.makeText(context, "Calling Digify Care (+91 74250 16636)...", Toast.LENGTH_SHORT).show()
                                }
                                SubMenuItem("Video Tutorials & Knowledgebase") {
                                    Toast.makeText(context, "Opening Digify Video Guides...", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        MenuLinkItem(
                            icon = Icons.Default.Star,
                            title = "Rate this app",
                            onClick = { Toast.makeText(context, "Thank you for rating Digify ERP 5 stars!", Toast.LENGTH_SHORT).show() }
                        )
                    }
                }
            }

            // Logout / Switch Account Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLogout() },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Logout",
                                tint = DigifyRed,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = "Logout / Switch Account",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DigifyRed
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = DigifyRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Section 4: App Version & Privacy (WA0033)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "App Version",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "29.4.0",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Privacy Policy",
                    fontSize = 13.sp,
                    color = DigifyPrimaryBlue,
                    modifier = Modifier
                        .clickable { Toast.makeText(context, "https://digifysoft.in/privacy-policy", Toast.LENGTH_SHORT).show() }
                        .padding(vertical = 4.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                // Brand Footer: Replaces Vyapar with Digify Soft Solutions (WA0033)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.digify_logo),
                        contentDescription = "Digify Soft Solutions",
                        modifier = Modifier
                            .height(38.dp)
                            .padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Crafted by Digify Soft Solutions Pvt Ltd.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "www.digifysoft.in",
                        fontSize = 11.sp,
                        color = DigifyPrimaryBlue
                    )
                }
            }
        }

        // Interactive Dialogs for Menu items
        if (showOnlineStoreDialog) {
            OnlineStoreDialog(
                storeName = storeName,
                onDismiss = { showOnlineStoreDialog = false }
            )
        }

        if (showExpensesDialog) {
            ExpensesDialog(
                onDismiss = { showExpensesDialog = false }
            )
        }

        if (showPurchaseBillsDialog) {
            PurchaseBillsDialog(
                onDismiss = { showPurchaseBillsDialog = false }
            )
        }

        if (showSaleInvoicesDialog) {
            SaleInvoicesDialog(
                transactions = transactions,
                onPrintReceipt = { selectedReceiptTxn = it },
                onDismiss = { showSaleInvoicesDialog = false }
            )
        }

        if (showBankAccountsDialog) {
            BankAccountsDialog(
                onDismiss = { showBankAccountsDialog = false }
            )
        }

        if (showCashInHandDialog) {
            CashInHandDialog(
                onDismiss = { showCashInHandDialog = false }
            )
        }

        selectedReceiptTxn?.let { txn ->
            ThermalReceiptDialog(
                transaction = txn,
                onDismiss = { selectedReceiptTxn = null }
            )
        }
    }
}

@Composable
private fun MenuExpandableItem(
    icon: ImageVector,
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = DigifyPrimaryBlue,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun MenuLinkItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = DigifyPrimaryBlue,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SubMenuItem(
    title: String,
    onClick: () -> Unit
) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        color = TextSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    )
}
