package models

@JvmInline
value class ExchangeOfferId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ExchangeOfferId("")
    }
}