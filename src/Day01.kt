fun main() {
    fun groupedElves(input: List<String>): List<List<Int>> = input
        .chunkedBy { it.isBlank() }
        .map { elf ->
            elf.map { it.toInt() }
        }


    fun part1(input: List<String>): Int {
        return groupedElves(input)
            .maxOf { it.sum() }

    }


    fun part2(input: List<String>): Int {
        return groupedElves(input)
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
