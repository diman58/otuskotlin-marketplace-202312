package models

class ExchangeOfferRate(private val rate: String) {
    fun asString() = rate
    companion object {
        val NONE = ExchangeOfferRate("")
    }
}