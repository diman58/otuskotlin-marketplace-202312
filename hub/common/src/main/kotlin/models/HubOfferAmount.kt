data class HubOfferAmount(private val amount: String) {
    fun asString() = amount
    companion object {
        val NONE = HubOfferAmount("")
    }
}