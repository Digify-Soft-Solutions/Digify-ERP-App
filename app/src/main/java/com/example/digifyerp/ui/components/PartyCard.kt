package com.example.digifyerp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.model.BalanceType
import com.example.digifyerp.model.Party
import com.example.digifyerp.theme.DigifyGreen
import com.example.digifyerp.theme.DigifyPinkGive
import com.example.digifyerp.theme.TextMuted
import com.example.digifyerp.theme.TextPrimary
import com.example.digifyerp.theme.TextSecondary
import java.util.Locale

@Composable
fun PartyCard(
    party: Party,
    onClick: (Party) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick(party) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = party.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = party.lastTransactionDate,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                val color = when (party.balanceType) {
                    BalanceType.YOU_WILL_GET -> DigifyGreen
                    BalanceType.YOU_WILL_GIVE -> DigifyPinkGive
                    BalanceType.SETTLED -> TextSecondary
                }

                val label = when (party.balanceType) {
                    BalanceType.YOU_WILL_GET -> "You'll Get"
                    BalanceType.YOU_WILL_GIVE -> "You'll Give"
                    BalanceType.SETTLED -> "Settled"
                }

                Text(
                    text = String.format(Locale.ENGLISH, "₹ %,.0f", party.balanceAmount),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
