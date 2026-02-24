package com.example.ktshw

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform