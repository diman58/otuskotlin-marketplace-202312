package models

@JvmInline
value class ExchangeOfferLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ExchangeOfferLock("")
    }
}