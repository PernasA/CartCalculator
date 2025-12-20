package org.example.mycartcalculator.expect

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.mycartcalculator.database.CartDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            CartDatabase.Schema,
            context,
            "cart.db"
        )
}
