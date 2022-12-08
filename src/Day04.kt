fun main() {


    fun isFullyOverLapping(it: Pair<IntRange, IntRange>): Boolean =
        it.first.toSet().containsAll(it.second.toSet()) || it.second.toSet().containsAll(it.first.toSet())


    fun isOverLapping(it: Pair<IntRange, IntRange>): Boolean =
        (it.first.toSet() intersect it.second.toSet()).isNotEmpty()


    fun part1(input: List<String>): Int = input
        .map { it.split(",") }
        .map { it.first().ranges() to it.last().ranges() }
        .count { isFullyOverLapping(it) }


    fun part2(input: List<String>): Int = input
        .map { it.split(",") }
        .map { it.first().ranges() to it.last().ranges() }
        .count { isOverLapping(it) }


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
    runTest(4, day, ::part2)


    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}


fun String.ranges(): IntRange {
    val numbers = this.split("-").map { it.toInt() }
    return IntRange(numbers.first(), numbers.last())

}
