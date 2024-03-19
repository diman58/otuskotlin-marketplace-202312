package models

@JvmInline
value class ExchangeOfferUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ExchangeOfferUserId("")
    }
}