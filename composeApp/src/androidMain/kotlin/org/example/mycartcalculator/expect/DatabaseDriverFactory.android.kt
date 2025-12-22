package org.example.mycartcalculator.expect

import android.content.Context
import android.util.Log
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.mycartcalculator.database.CartDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        Log.d("DB", "DB name = cart.db")
        //context.deleteDatabase("cart.db") // this is useful when there is a change in the database and uninstalling is not enough

        return AndroidSqliteDriver(
            CartDatabase.Schema,
            context,
            "cart.db"
        )
    }
}
