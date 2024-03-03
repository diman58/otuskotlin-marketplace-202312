

data class SqlSelect(
    val action: String,
    val target: String,
    val table: String,
    val filterWhere: String
)
