fun main() {
    fun getAllElves(input: List<String>): List<List<Int>> {
        val allElves = mutableListOf<List<Int>>()
        var perElf = mutableListOf<Int>()
        var firstRowPerElf = true
        for (s in input) {
            if (s == "") {
                perElf = mutableListOf()
                firstRowPerElf = true
            } else {
                if (firstRowPerElf) {
                    allElves.add(perElf)
                    firstRowPerElf = false
                }
                perElf.add(s.toInt())
            }
        }
        return allElves
    }

    fun part1(input: List<String>): Int {
        return getAllElves(input)
            .maxOf { it.sum() }

    }


    fun part2(input: List<String>): Int {
        return getAllElves(input)
            .sortedByDescending { it.sum() }
            .take(3)
            .sumOf { it.sum() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
