package com.example.digifyerp.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.theme.DigifyBlueLight
import com.example.digifyerp.theme.DigifyPrimaryBlue
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.theme.TextSecondary

@Composable
fun QuickLinksCard(
    isPartyTab: Boolean,
    onAddTxnClick: () -> Unit = {},
    onSaleReportClick: () -> Unit = {},
    onImportPartyClick: () -> Unit = {},
    onShowAllClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Quick Links",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                if (!isPartyTab) {
                    // Transaction Quick Links
                    QuickLinkItem(
                        icon = Icons.Default.Bolt,
                        label = "60% OFF",
                        iconBg = Color(0xFFFF4D4D),
                        iconTint = Color(0xFFFFD700),
                        onClick = onShowAllClick
                    )
                    QuickLinkItem(
                        icon = Icons.Default.PostAdd,
                        label = "Add Txn",
                        iconBg = DigifyPrimaryBlue,
                        iconTint = Color.White,
                        onClick = onAddTxnClick
                    )
                    QuickLinkItem(
                        icon = Icons.AutoMirrored.Filled.ReceiptLong,
                        label = "Sale Report",
                        iconBg = Color(0xFF60A5FA),
                        iconTint = Color.White,
                        onClick = onSaleReportClick
                    )
                    QuickLinkItem(
                        icon = Icons.Default.ChevronRight,
                        label = "Show All",
                        iconBg = Color(0xFF93C5FD),
                        iconTint = Color.White,
                        onClick = onShowAllClick
                    )
                } else {
                    // Party Quick Links
                    QuickLinkItem(
                        icon = Icons.Default.ContactPhone,
                        label = "Import Party",
                        iconBg = Color(0xFF3B82F6),
                        iconTint = Color.White,
                        onClick = onImportPartyClick
                    )
                    QuickLinkItem(
                        icon = Icons.Default.Description,
                        label = "Party State...",
                        iconBg = Color(0xFF60A5FA),
                        iconTint = Color.White,
                        onClick = onShowAllClick
                    )
                    QuickLinkItem(
                        icon = Icons.Default.Settings,
                        label = "Party Settings",
                        iconBg = Color(0xFF93C5FD),
                        iconTint = Color.White,
                        onClick = onShowAllClick
                    )
                    QuickLinkItem(
                        icon = Icons.Default.ChevronRight,
                        label = "Show All",
                        iconBg = Color(0xFFBFDBFE),
                        iconTint = Color.White,
                        onClick = onShowAllClick
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickLinkItem(
    icon: ImageVector,
    label: String,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
