data class Instruction2(val type: String, val value: Int)

fun main() {


    fun part1(input: List<String>): Int {


        val test = input
            .flatMap { it.toInstruction2() }

        var x = 1
        val signalStrengths = listOf(20, 60, 100, 140, 180, 220)
        var signalStrength = 0
        for ((index, instruction) in test.withIndex()) {
            if (signalStrengths.contains(index + 1)) {
                signalStrength += (index + 1) * x
            }
            x += instruction.value
        }


        return signalStrength
    }


    fun part2(input: List<String>): Int {
        return 0


    }

    val day = "10"

// test if implementation meets criteria from the description, like:
    runTest(13140, day, ::part1)
    //runTest(36, day, ::part2)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun String.toInstruction2(): List<Instruction2> {
    val test = this.split(" ")
    return if (test[0] == "noop") {
        listOf(Instruction2(test[0], 0))
    } else if (test[0] == "addx") {
        listOf(Instruction2(test[0], 0), Instruction2(test[0], test[1].toInt()))
    } else {
        error(this)
    }
}


