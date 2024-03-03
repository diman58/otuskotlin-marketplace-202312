

fun query(block: SqlSelectBuilder.() -> Unit) = SqlSelectBuilder().apply(block)