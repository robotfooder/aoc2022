fun main() {


    fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)


    fun part1(input: List<String>): Int {
    val SIGNAL_START_LENGTH = 4

        return input
            .map { it.windowed(SIGNAL_START_LENGTH) }
            .flatten()
            .indexOfFirst { it.allUnique() } + SIGNAL_START_LENGTH

    }

    fun part2(input: List<String>): Int {
        val MESSAGE_START_LENGTH = 14

        return input
            .map { it.windowed(MESSAGE_START_LENGTH) }
            .flatten()
            .indexOfFirst { it.allUnique() } + MESSAGE_START_LENGTH


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





