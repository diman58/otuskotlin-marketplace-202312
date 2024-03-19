package models

data class ExchangeOfferCurrency(private val name: String) {
    companion object {
        val NONE = ExchangeOfferCurrency("")
    }
}
