package models

@JvmInline
value class HubOfferId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HubOfferId("")
    }
}