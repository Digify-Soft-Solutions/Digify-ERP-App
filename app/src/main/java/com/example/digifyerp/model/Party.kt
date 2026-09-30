package com.example.digifyerp.model

import java.util.UUID

enum class BalanceType {
    YOU_WILL_GET,
    YOU_WILL_GIVE,
    SETTLED
}

data class Party(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val phoneNumber: String = "",
    val gstNumber: String = "",
    val billingAddress: String = "",
    val balanceAmount: Double = 0.0,
    val balanceType: BalanceType = BalanceType.YOU_WILL_GET,
    val lastTransactionDate: String = "30 Sept, 26"
)
