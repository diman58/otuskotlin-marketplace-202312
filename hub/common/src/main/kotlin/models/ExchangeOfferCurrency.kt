package models

data class ExchangeOfferCurrency(private val name: String) {
    fun asString() = name
    companion object {
        val NONE = ExchangeOfferCurrency("")
    }
}
