fun main() {


    fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)


    fun part1(input: List<String>): Int {
        val signalStartLength = 4

        return input
            .map { it.windowed(signalStartLength) }
            .flatten()
            .indexOfFirst { it.allUnique() } + signalStartLength

    }

    fun part2(input: List<String>): Int {
        val messageStartLength = 14

        return input
            .map { it.windowed(messageStartLength) }
            .flatten()
            .indexOfFirst { it.allUnique() } + messageStartLength


    }


    fun runTest(expected: Int, day: String, testFunction: (List<String>) -> Int) {
        val actual = testFunction(readTestInput(day))
        check(expected == actual)
        {
            "Failing for day $day, ${testFunction}. " +
                    "Expected $expected but got $actual"
        }
    }


    val day = "06"

// test if implementation meets criteria from the description, like:
    runTest(11, day, ::part1)
    runTest(26, day, ::part2)


    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}





