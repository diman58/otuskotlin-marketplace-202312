import kotlin.test.Test
import kotlin.test.assertEquals

/*
* Реализовать функцию, которая преобразует список словарей строк в ФИО
* Функцию сделать с использованием разных функций для разного числа составляющих имени
* Итого, должно получиться 4 функции
*
* Для успешного решения задания, требуется раскомментировать тест, тест должен выполняться успешно
* */
class HomeWork1Test {

    @Test
    fun mapListToNamesTest() {
        val input = listOf(
            mapOf(
                "first" to "Иван",
                "middle" to "Васильевич",
                "last" to "Рюрикович",
            ),
            mapOf(
                "first" to "Петька",
            ),
            mapOf(
                "first" to "Сергей",
                "last" to "Королев",
            ),
        )
        val expected = listOf(
            "Рюрикович Иван Васильевич",
            "Петька",
            "Королев Сергей",
        )
        val res = mapListToNames(input)
        assertEquals(expected, res)
    }

    private fun mapListToNames(input: List<Map<String, String>>): List<String> {
        return input.map { namesMap -> when {
            namesMap.size == 1 -> oneWord(namesMap)
            namesMap.size == 2 -> twoWords(namesMap)
            else -> threeWords(namesMap)
        } }
    }

    private fun threeWords(input: Map<String, String>): String {
        return "${input["last"]} ${input["first"]} ${input["middle"]}"
    }

    private fun twoWords(input: Map<String, String>): String {
        return "${input["last"]} ${input["first"]}"
    }

    private fun oneWord(input: Map<String, String>): String {
        return input.values.first()
    }
}
