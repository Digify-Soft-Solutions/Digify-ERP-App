package com.example.digifyerp.model

import java.util.UUID

enum class TransactionType {
    SALE,
    PURCHASE,
    ESTIMATE,
    PAYMENT_IN,
    PAYMENT_OUT
}

enum class PaymentStatus {
    PAID,
    UNPAID,
    PARTIAL
}

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val invoiceNumber: String,
    val partyName: String,
    val date: String,
    val totalAmount: Double,
    val balanceAmount: Double,
    val type: TransactionType = TransactionType.SALE,
    val paymentStatus: PaymentStatus = if (balanceAmount <= 0.0) PaymentStatus.PAID else if (balanceAmount < totalAmount) PaymentStatus.PARTIAL else PaymentStatus.UNPAID,
    val notes: String = "",
    val itemsSummary: String = "General Goods"
)
