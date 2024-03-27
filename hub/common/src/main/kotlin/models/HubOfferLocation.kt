package models

data class HubOfferLocation(private val location: String) {
    fun asString() = location
    companion object {
        val NONE = HubOfferLocation("")
    }
}