package models

@JvmInline
value class HubOfferUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HubOfferUserId("")
    }
}