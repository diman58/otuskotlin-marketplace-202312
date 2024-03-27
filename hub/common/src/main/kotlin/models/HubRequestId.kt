package models

@JvmInline
value class HubRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HubRequestId("")
    }
}