

class Where {

    private var conditions = mutableListOf<String>()

    infix fun String.eq(s: Any?): Where {
        val condition = when(s) {
            is Number -> "$this = $s"
            null -> "$this is null"
            else -> "$this = '$s'"
        }
        conditions.add(condition)
        return this@Where
    }

    infix fun String.nonEq(s: Any?): Where {
        val condition = when (s) {
            is Number -> "$this != $s"
            null -> "$this !is null"
            else -> "$this != '$s'"
        }
        conditions.add(condition)
        return this@Where
    }

    infix fun or(function: Where.() -> Unit) {
        val condition = Where().apply(function)
        if (condition.conditions.isNotEmpty())
            conditions.add(condition.conditions.joinToString(" or ", prefix = "(", postfix = ")"))
    }

    override fun toString(): String = conditions.joinToString(" and ")
}