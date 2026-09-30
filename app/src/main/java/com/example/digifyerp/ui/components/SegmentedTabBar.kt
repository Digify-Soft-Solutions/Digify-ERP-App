package com.example.digifyerp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.theme.DigifyRed
import com.example.digifyerp.theme.DigifyRedLight
import com.example.digifyerp.theme.TextSecondary

enum class HomeSubTab {
    TRANSACTION_DETAILS,
    PARTY_DETAILS
}

@Composable
fun SegmentedTabBar(
    selectedTab: HomeSubTab,
    onTabSelected: (HomeSubTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Tab 1: Transaction Details
        PillTab(
            text = "Transaction Details",
            isSelected = selectedTab == HomeSubTab.TRANSACTION_DETAILS,
            onClick = { onTabSelected(HomeSubTab.TRANSACTION_DETAILS) },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Tab 2: Party Details
        PillTab(
            text = "Party Details",
            isSelected = selectedTab == HomeSubTab.PARTY_DETAILS,
            onClick = { onTabSelected(HomeSubTab.PARTY_DETAILS) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PillTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .border(
                width = 1.5.dp,
                color = if (isSelected) DigifyRed else Color(0xFFD0D7DE),
                shape = shape
            )
            .background(
                if (isSelected) DigifyRedLight.copy(alpha = 0.5f) else Color.White
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) DigifyRed else TextSecondary
        )
    }
}
