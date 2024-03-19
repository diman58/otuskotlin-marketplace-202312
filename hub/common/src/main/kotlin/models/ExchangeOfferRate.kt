package models

class ExchangeOfferRate(private val rate: String) {
    companion object {
        val NONE = ExchangeOfferRate("")
    }
}