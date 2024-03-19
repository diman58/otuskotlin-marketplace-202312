package models

class ExchangeOfferAmount(private val amount: String) {
    companion object {
        val NONE = ExchangeOfferAmount("")
    }
}