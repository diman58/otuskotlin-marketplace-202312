

class SqlSelectBuilder {

    private var tableName: String? = null
    private var columns = mutableListOf<String>()
    private var whereClause: String? = null

    fun from(table: String) {
        tableName = table
    }

    fun select(vararg cols: String) {
        if (cols.isNotEmpty())
            columns.addAll(cols)
    }

    fun where(block: Where.() -> Unit) {
        val where = Where().apply(block)
        whereClause = where.toString()
    }

    fun build(): String {
        if (tableName == null) throw Exception("Select can't be used without table")
        val selectClause = if (columns.isEmpty()) "select *" else "select ${columns.joinToString(", ")}"
        return "$selectClause from ${tableName}${whereClause?.let { " where $it" } ?: ""}"
    }

}