

fun buildUser(block: UserBuilder.() -> Unit) = UserBuilder().apply(block).build()
