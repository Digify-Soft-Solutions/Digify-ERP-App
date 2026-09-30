package com.example.digifyerp.model

data class MonthlySalePoint(
    val monthName: String,
    val amount: Double
)

data class DashboardStats(
    val totalSaleMonth: Double = 2500.0,
    val monthLabel: String = "Sept",
    val growthPercent: Int = 100,
    val youllGet: Double = 100.0,
    val youllGive: Double = 0.0,
    val expensesMonth: Double = 0.0,
    val monthlyHistory: List<MonthlySalePoint> = listOf(
        MonthlySalePoint("Jul", 0.0),
        MonthlySalePoint("Aug", 0.0),
        MonthlySalePoint("Sept", 2500.0)
    )
)
