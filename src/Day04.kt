fun main() {


    fun isOverLapping(it: Pair<IntRange, IntRange>): Boolean {
        val (first, second) = it
        return first.toSet().containsAll(second.toSet()) || second.toSet().containsAll(first.toSet())

    }

    fun part1(input: List<String>): Int = input
        .map { it.split(",") }
        .map { it.first() to it.last() }
        .map { it.ranges() }
        .count { isOverLapping(it) }


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


    val day = "04"

    // test if implementation meets criteria from the description, like:
    runTest(2, day, ::part1)
    //runTest(70, day, ::part2)


    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}

fun Pair<String, String>.ranges(): Pair<IntRange, IntRange> {
    val (e1, e2) = this
    return e1.ranges() to e2.ranges()
}

fun String.ranges(): IntRange {
    val numbers = this.split("-").map { it.toInt() }
    return IntRange(numbers.first(), numbers.last())

}
