package models

data class HubOfferCurrency(private val name: String) {
    fun asString() = name
    companion object {
        val NONE = HubOfferCurrency("")
    }
}
