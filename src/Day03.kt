fun main() {


    fun analyzeRuckContent(ruckContent: String): Int {

        val compartment1 = ruckContent.substring(0, ruckContent.length / 2).toCharArray()
        val compartment2 = ruckContent.substring(ruckContent.length / 2).toCharArray()
        check(compartment2.size == compartment1.size) {
            "Check $ruckContent"
        }

        return compartment2
            .filter { compartment1.contains(it) }
            .distinct()
            .sumOf { if (it.isUpperCase()) it.code - 38 else it.code - 96 }

    }

    fun part1(input: List<String>): Int {

        return input.sumOf { analyzeRuckContent(it) }

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
    runTest(70, day, ::part2)


    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}


