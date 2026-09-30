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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.theme.DigifyPrimaryBlue
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.theme.TextSecondary

enum class AppTab {
    HOME,
    DASHBOARD,
    ITEMS,
    MENU,
    GET_DESKTOP
}

@Composable
fun DigifyBottomBar(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(color = Color(0xFFE5ECF4), thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                label = "HOME",
                icon = if (currentTab == AppTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                selected = currentTab == AppTab.HOME,
                onClick = { onTabSelected(AppTab.HOME) }
            )

            BottomNavItem(
                label = "DASHBOARD",
                icon = if (currentTab == AppTab.DASHBOARD) Icons.Filled.InsertChart else Icons.Outlined.InsertChart,
                selected = currentTab == AppTab.DASHBOARD,
                onClick = { onTabSelected(AppTab.DASHBOARD) }
            )

            BottomNavItem(
                label = "ITEMS",
                icon = if (currentTab == AppTab.ITEMS) Icons.Filled.AllInbox else Icons.Outlined.AllInbox,
                selected = currentTab == AppTab.ITEMS,
                onClick = { onTabSelected(AppTab.ITEMS) }
            )

            BottomNavItem(
                label = "MENU",
                icon = if (currentTab == AppTab.MENU) Icons.Filled.Menu else Icons.Outlined.Menu,
                selected = currentTab == AppTab.MENU,
                onClick = { onTabSelected(AppTab.MENU) }
            )

            DesktopNavItem(
                selected = currentTab == AppTab.GET_DESKTOP,
                onClick = { onTabSelected(AppTab.GET_DESKTOP) }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) DigifyPrimaryBlue else TextSecondary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) DigifyPrimaryBlue else TextSecondary
        )
    }
}

@Composable
private fun DesktopNavItem(
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Windows 4-color grid icon matching Vyapar screenshot
        Row(modifier = Modifier.size(22.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(1.dp).background(Color(0xFFF25022)))
                Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(1.dp).background(Color(0xFF00A4EF)))
            }
            Column(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(1.dp).background(Color(0xFF7FBA00)))
                Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(1.dp).background(Color(0xFFFFB900)))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "GET DESKTOP",
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) DigifyPrimaryBlue else TextSecondary
        )
    }
}
