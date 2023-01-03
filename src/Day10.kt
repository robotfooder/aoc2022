fun main() {


    fun part1(input: List<String>): Int {


        val signalStrengths = listOf(20, 60, 100, 140, 180, 220)

        return input
            .flatMap { it.toNumberList() }
            .runningFold(1) { acc, num -> acc + num }
            .filterIndexed { index, _ -> signalStrengths.contains(index + 1) }
            .mapIndexed { index, i -> i * signalStrengths[index] }
            .sum()


    }


    fun part2(input: List<String>): Int {
        val cycles = input
            .flatMap { it.toNumberList() }
            .runningFold(1) { acc, num -> acc + num }
            .chunked(40)


        cycles.forEach { rows ->
            rows.forEachIndexed { index, cycleVal ->
                if (index in cycleVal - 1..cycleVal + 1) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }

        return 0


    }

    val day = "10"

// test if implementation meets criteria from the description, like:
    runTest(13140, day, ::part1)
    runTest(0, day, ::part2)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun String.toNumberList(): List<Int> {
    val test = this.split(" ")
    return if (test[0] == "noop") {
        listOf(0)
    } else if (test[0] == "addx") {
        listOf(0, test[1].toInt())
    } else {
        error(this)
    }
}


