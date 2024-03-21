package models

class ExchangeOfferAmount(private val amount: String) {
    fun asString() = amount
    companion object {
        val NONE = ExchangeOfferAmount("")
    }
}