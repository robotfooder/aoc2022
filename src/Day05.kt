fun main() {


    fun part1(input: List<String>): String {

        val startSTack = input
            .filter { it.contains("[") }
            .map { it.chunked(4) }
            .map { strings ->
                strings.map { it.trim() }
                    .map { it.replace("[", "") }
                    .map { it.replace("]", "") }
            }
            .fold(mutableListOf<ArrayDeque<String>>()) { acc, row ->

                row.forEachIndexed { index, s ->
                    if (acc.size - 1 < index) {
                        acc.add(ArrayDeque())
                    }
                    if (s.isNotEmpty()) {
                        val stack = acc[index]
                        stack.add(s)
                    }
                }

                acc
            }
        return "$startSTack"
    }

    fun part2(input: List<String>): Int = input.size


    fun runTest(expected: String, day: String, testFunction: (List<String>) -> String) {
        val actual = testFunction(readTestInput(day))
        check(expected == actual)
        {
            "Failing for day $day, ${testFunction}. " +
                    "Expected $expected but got $actual"
        }
    }


    val day = "05"

// test if implementation meets criteria from the description, like:
    runTest("CMZ", day, ::part1)
    //runTest(4, day, ::part2)


    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}



