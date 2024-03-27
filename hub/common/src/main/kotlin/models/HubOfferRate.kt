package models

data class HubOfferRate(private val rate: String) {
    fun asString() = rate
    companion object {
        val NONE = HubOfferRate("")
    }
}