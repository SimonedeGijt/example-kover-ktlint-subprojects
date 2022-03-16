package org.example.greeting

import org.springframework.stereotype.Service

@Service
class GreetingService {
    val storedGreetings = listOf("hello")

    fun getGreetings(): List<String> = storedGreetings
}
