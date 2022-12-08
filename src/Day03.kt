fun main() {

    fun part1(input: List<String>): Int = input
        .map { it.split(it.length / 2) }
        .map { (c1, c2) -> c1.toSet().intersect(c2.toSet()) }
        .sumOf { matchedChars ->
            matchedChars
                .sumOf { if (it.isUpperCase()) it.code - 38 else it.code - 96 }
        }

    fun part2(input: List<String>): Int {
        return input
            .size

    }

    fun runTest(expected: Int, day: String, testFunction: (List<String>) -> Int) {
        val actual = testFunction(readInput("Day${day}_test"))
        check(expected == actual)
        {
            "Failing for day $day, ${testFunction}. " +
                    "Expected $expected but got $actual"
        }
    }


    val day = "03"

    // test if implementation meets criteria from the description, like:
    runTest(157, day, ::part1)
    //runTest(70, day, ::part2)


    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}


