import kotlinx.cinterop.ExperimentalForeignApi
//import rs_example.rust_add

@OptIn(ExperimentalForeignApi::class)
fun rsAdd(a: Int, b: Int): Int = TODO() /* rust_add(a, b) */
