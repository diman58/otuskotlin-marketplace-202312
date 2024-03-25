package models

@JvmInline
value class HubOfferLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HubOfferLock("")
    }
}