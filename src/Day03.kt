fun main() {

    fun part1(input: List<String>): Int = input
        .map { it.split(it.length / 2) }
        .flatMap { (c1, c2) -> c1.toSet() intersect c2.toSet() }
        .sumOf { it.score() }


    fun part2(input: List<String>): Int {
        return input
            .chunked(3)
            .flatMap { it[0].toSet() intersect it[1].toSet() intersect it[2].toSet() }
            .sumOf { it.score() }


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
    runTest(70, day, ::part2)


    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}


