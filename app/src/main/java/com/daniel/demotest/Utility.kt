package com.daniel.demotest

class Utility {

    fun add(a: Int, b: Int, rule: DiscountRule): Float {
        return (a + b) * rule.rate
    }
}

class DiscountRule(val type: Int) {
    val rate: Float get() {
        return when (type) {
            1 -> 0.6f
            2 -> 0.8f
            else -> 1f
        }
    }
}