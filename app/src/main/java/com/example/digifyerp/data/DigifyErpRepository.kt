package com.example.digifyerp.data

import com.example.digifyerp.model.BalanceType
import com.example.digifyerp.model.DashboardStats
import com.example.digifyerp.model.Item
import com.example.digifyerp.model.MonthlySalePoint
import com.example.digifyerp.model.Party
import com.example.digifyerp.model.Transaction
import com.example.digifyerp.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DigifyErpRepository {

    private val _storeName = MutableStateFlow("Gautam malik")
    val storeName: StateFlow<String> = _storeName.asStateFlow()

    private val _companyTitle = MutableStateFlow("Digify Soft Solutions")
    val companyTitle: StateFlow<String> = _companyTitle.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userPhone = MutableStateFlow("+91 98765 43210")
    val userPhone: StateFlow<String> = _userPhone.asStateFlow()

    fun login(phone: String, businessName: String) {
        _userPhone.value = phone
        if (businessName.isNotBlank()) {
            _storeName.value = businessName
        }
        _isLoggedIn.value = true
    }

    fun logout() {
        _isLoggedIn.value = false
    }


    val supportPhoneNumber = "+91 74250 16636"

    private val _transactions = MutableStateFlow<List<Transaction>>(
        listOf(
            Transaction(
                id = "txn_1",
                invoiceNumber = "1",
                partyName = "Kush",
                date = "30 Sept, 26",
                totalAmount = 2500.00,
                balanceAmount = 100.00,
                type = TransactionType.SALE,
                notes = "GST Invoice generated via Digify Soft",
                itemsSummary = "ERP Cloud Subscription & Setup"
            ),
            Transaction(
                id = "txn_2",
                invoiceNumber = "2",
                partyName = "Sharma Enterprises",
                date = "29 Sept, 26",
                totalAmount = 5600.00,
                balanceAmount = 0.00,
                type = TransactionType.SALE,
                notes = "Paid via UPI / QR",
                itemsSummary = "Thermal Printer 80mm + Rolls"
            ),
            Transaction(
                id = "txn_3",
                invoiceNumber = "3",
                partyName = "Verma Logistics",
                date = "28 Sept, 26",
                totalAmount = 3200.00,
                balanceAmount = 3200.00,
                type = TransactionType.SALE,
                notes = "Payment pending in 15 days",
                itemsSummary = "Barcode Scanner 2D USB"
            )
        )
    )
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _parties = MutableStateFlow<List<Party>>(
        listOf(
            Party(
                id = "party_1",
                name = "Kush",
                phoneNumber = "+91 74250 16636",
                gstNumber = "08AAAAA0000A1Z5",
                billingAddress = "Jaipur, Rajasthan",
                balanceAmount = 100.00,
                balanceType = BalanceType.YOU_WILL_GET,
                lastTransactionDate = "30 Sept, 26"
            ),
            Party(
                id = "party_2",
                name = "Sharma Enterprises",
                phoneNumber = "+91 98290 12345",
                gstNumber = "08BCDEF1234G1Z8",
                billingAddress = "Kota, Rajasthan",
                balanceAmount = 0.00,
                balanceType = BalanceType.SETTLED,
                lastTransactionDate = "29 Sept, 26"
            ),
            Party(
                id = "party_3",
                name = "Verma Logistics",
                phoneNumber = "+91 94140 67890",
                gstNumber = "08XYZAB5678C1Z2",
                billingAddress = "Jodhpur, Rajasthan",
                balanceAmount = 3200.00,
                balanceType = BalanceType.YOU_WILL_GET,
                lastTransactionDate = "28 Sept, 26"
            )
        )
    )
    val parties: StateFlow<List<Party>> = _parties.asStateFlow()

    private val _items = MutableStateFlow<List<Item>>(
        listOf(
            Item(
                id = "item_1",
                name = "Digify ERP Cloud Subscription",
                itemCode = "DIG-ERP-01",
                unit = "YEAR",
                salePrice = 2500.00,
                purchasePrice = 1200.00,
                currentStock = 50.0,
                minStockAlert = 5.0,
                hsnCode = "997331"
            ),
            Item(
                id = "item_2",
                name = "Thermal Receipt Paper 80mm (Box)",
                itemCode = "TH-80MM",
                unit = "BOX",
                salePrice = 1200.00,
                purchasePrice = 850.00,
                currentStock = 18.0,
                minStockAlert = 5.0,
                hsnCode = "4802"
            ),
            Item(
                id = "item_3",
                name = "USB Barcode Scanner 2D High Speed",
                itemCode = "SCN-2D-USB",
                unit = "PCS",
                salePrice = 2800.00,
                purchasePrice = 1950.00,
                currentStock = 9.0,
                minStockAlert = 3.0,
                hsnCode = "8471"
            ),
            Item(
                id = "item_4",
                name = "Bluetooth Thermal Printer 58mm",
                itemCode = "PRN-BT-58",
                unit = "PCS",
                salePrice = 4500.00,
                purchasePrice = 3100.00,
                currentStock = 2.0, // Low stock alert!
                minStockAlert = 5.0,
                hsnCode = "8443"
            ),
            Item(
                id = "item_5",
                name = "Thermal POS Printer 80mm Auto-Cutter",
                itemCode = "PRN-80-CUT",
                unit = "PCS",
                salePrice = 7500.00,
                purchasePrice = 5400.00,
                currentStock = 6.0,
                minStockAlert = 2.0,
                hsnCode = "8443"
            )
        )
    )
    val items: StateFlow<List<Item>> = _items.asStateFlow()

    private val _dashboardStats = MutableStateFlow(
        DashboardStats(
            totalSaleMonth = 11300.00,
            monthLabel = "Sept",
            growthPercent = 100,
            youllGet = 3300.00,
            youllGive = 0.00,
            expensesMonth = 1500.00,
            monthlyHistory = listOf(
                MonthlySalePoint("Jul", 0.0),
                MonthlySalePoint("Aug", 2100.0),
                MonthlySalePoint("Sept", 11300.0)
            )
        )
    )
    val dashboardStats: StateFlow<DashboardStats> = _dashboardStats.asStateFlow()

    fun updateStoreName(newName: String) {
        _storeName.value = newName
    }

    fun addSale(
        partyName: String,
        amount: Double,
        balance: Double,
        itemsSummary: String = "General Goods"
    ) {
        val dateFormat = SimpleDateFormat("dd MMM, yy", Locale.ENGLISH)
        val dateStr = dateFormat.format(Date())
        val nextInvoiceNum = (_transactions.value.size + 1).toString()

        val newTxn = Transaction(
            invoiceNumber = nextInvoiceNum,
            partyName = partyName,
            date = dateStr,
            totalAmount = amount,
            balanceAmount = balance,
            type = TransactionType.SALE,
            itemsSummary = itemsSummary
        )

        _transactions.update { current -> listOf(newTxn) + current }

        // Update or add party
        val existingParty = _parties.value.find { it.name.equals(partyName, ignoreCase = true) }
        if (existingParty != null) {
            _parties.update { list ->
                list.map {
                    if (it.id == existingParty.id) {
                        it.copy(
                            balanceAmount = it.balanceAmount + balance,
                            lastTransactionDate = dateStr
                        )
                    } else it
                }
            }
        } else {
            val newParty = Party(
                name = partyName,
                balanceAmount = balance,
                balanceType = if (balance > 0) BalanceType.YOU_WILL_GET else BalanceType.SETTLED,
                lastTransactionDate = dateStr
            )
            _parties.update { current -> listOf(newParty) + current }
        }

        recalculateStats()
    }

    fun addParty(
        name: String,
        phone: String = "",
        gst: String = "",
        balance: Double = 0.0,
        balanceType: BalanceType = BalanceType.YOU_WILL_GET
    ) {
        val dateFormat = SimpleDateFormat("dd MMM, yy", Locale.ENGLISH)
        val dateStr = dateFormat.format(Date())
        val newParty = Party(
            name = name,
            phoneNumber = phone,
            gstNumber = gst,
            balanceAmount = balance,
            balanceType = balanceType,
            lastTransactionDate = dateStr
        )
        _parties.update { current -> listOf(newParty) + current }
        recalculateStats()
    }

    fun addItem(
        name: String,
        salePrice: Double,
        purchasePrice: Double = 0.0,
        stock: Double = 0.0,
        unit: String = "PCS"
    ) {
        val newItem = Item(
            name = name,
            salePrice = salePrice,
            purchasePrice = purchasePrice,
            openingStock = stock,
            currentStock = stock,
            unit = unit
        )
        _items.update { current -> listOf(newItem) + current }
    }

    fun deleteItem(itemId: String) {
        _items.update { current -> current.filter { it.id != itemId } }
    }

    fun adjustItemStock(itemId: String, delta: Double) {
        _items.update { current ->
            current.map { item ->
                if (item.id == itemId) {
                    val newStock = (item.currentStock + delta).coerceAtLeast(0.0)
                    item.copy(currentStock = newStock)
                } else item
            }
        }
    }

    private fun recalculateStats() {
        val totalSale = _transactions.value.filter { it.type == TransactionType.SALE }.sumOf { it.totalAmount }
        val youllGet = _parties.value.filter { it.balanceType == BalanceType.YOU_WILL_GET }.sumOf { it.balanceAmount }
        val youllGive = _parties.value.filter { it.balanceType == BalanceType.YOU_WILL_GIVE }.sumOf { it.balanceAmount }

        _dashboardStats.update {
            it.copy(
                totalSaleMonth = totalSale,
                youllGet = youllGet,
                youllGive = youllGive,
                monthlyHistory = listOf(
                    MonthlySalePoint("Jul", 0.0),
                    MonthlySalePoint("Aug", 0.0),
                    MonthlySalePoint("Sept", totalSale)
                )
            )
        }
    }

    /**
     * ESC/POS Native Thermal Printer Driver helper
     * Generates standard ESC/POS bytes for 58mm / 80mm Bluetooth/USB thermal printers
     */
    fun generateEscPosThermalReceipt(
        txn: Transaction,
        paperWidthMm: Int = 80
    ): ByteArray {
        val stream = ByteArrayOutputStream()

        // ESC @ -> Initialize printer
        stream.write(byteArrayOf(0x1B, 0x40))

        // Center Align: ESC a 1
        stream.write(byteArrayOf(0x1B, 0x61, 0x01))

        // Double height & width for header: GS ! 0x11
        stream.write(byteArrayOf(0x1D, 0x21, 0x11))
        stream.write("DIGIFY SOFT SOLUTIONS\n".toByteArray(StandardCharsets.UTF_8))

        // Normal text: GS ! 0x00
        stream.write(byteArrayOf(0x1D, 0x21, 0x00))
        stream.write("Smart IT, Smarter Business\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("GSTIN: 08AABCD1234E1Z6 | Web: digifysoft.in\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("--------------------------------\n".toByteArray(StandardCharsets.UTF_8))

        // Left Align: ESC a 0
        stream.write(byteArrayOf(0x1B, 0x61, 0x00))
        stream.write("Invoice No : #${txn.invoiceNumber}\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("Date       : ${txn.date}\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("Customer   : ${txn.partyName}\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("--------------------------------\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("Item                 Qty   Amount\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("--------------------------------\n".toByteArray(StandardCharsets.UTF_8))
        stream.write(String.format(Locale.ENGLISH, "%-20s 1   %.2f\n", txn.itemsSummary.take(20), txn.totalAmount).toByteArray(StandardCharsets.UTF_8))
        stream.write("--------------------------------\n".toByteArray(StandardCharsets.UTF_8))

        // Right Align: ESC a 2
        stream.write(byteArrayOf(0x1B, 0x61, 0x02))
        stream.write(String.format(Locale.ENGLISH, "Total Amount : INR %.2f\n", txn.totalAmount).toByteArray(StandardCharsets.UTF_8))
        stream.write(String.format(Locale.ENGLISH, "Balance Due  : INR %.2f\n", txn.balanceAmount).toByteArray(StandardCharsets.UTF_8))

        // Center Align: ESC a 1
        stream.write(byteArrayOf(0x1B, 0x61, 0x01))
        stream.write("\nThank You for Doing Business!\n".toByteArray(StandardCharsets.UTF_8))
        stream.write("Powered by Digify Soft Solutions\n\n\n".toByteArray(StandardCharsets.UTF_8))

        // Cut paper: GS V 66 0
        stream.write(byteArrayOf(0x1D, 0x56, 0x42, 0x00))

        return stream.toByteArray()
    }
}
