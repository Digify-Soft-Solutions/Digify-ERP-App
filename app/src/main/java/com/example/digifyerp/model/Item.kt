package com.example.digifyerp.model

import java.util.UUID

data class Item(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val itemCode: String = "",
    val unit: String = "PCS",
    val salePrice: Double,
    val purchasePrice: Double = 0.0,
    val taxRatePercent: Double = 18.0,
    val openingStock: Double = 0.0,
    val currentStock: Double = 0.0,
    val minStockAlert: Double = 5.0,
    val hsnCode: String = ""
)
