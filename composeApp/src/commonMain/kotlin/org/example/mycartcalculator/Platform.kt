package org.example.mycartcalculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform