fun main() {

    val rounds = 20

    fun part1(input: List<String>): Int {

        val monkeys = input
            .chunkedBy { it.isBlank() }
            .map { it.toMonkey() }

        repeat(rounds) {

            monkeys.forEach { monkey ->
                val output = monkey.inspectItems()
                output.forEach {
                    val (toMonkey, worryLevel) = it
                    monkeys[toMonkey].items.add(worryLevel)

                }
            }
        }




        return monkeys
            .map { it.inspectedItems }
            .sortedDescending()
            .take(2)
            .reduce { acc, i ->
                acc * i
            }


    }


    fun part2(input: List<String>): Int {


        return 0


    }

    val day = "11"

// test if implementation meets criteria from the description, like:
    runTest(10605, day, ::part1)
    //runTest(0, day, ::part2)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toMonkey(): Monkey {
    val startingItems = this[1]
        .substringAfter("Starting items: ")
        .split(",")
        .map { it.trim() }
        .map(String::toInt)

    val ops = this[2].substringAfter("Operation: new = old ")
    val testValue = this[3].substringAfter("Test: divisible by ").toInt()
    val toTrue = this[4].substringAfter("If true: throw to monkey ").toInt()
    val toFalse = this[5].substringAfter("If false: throw to monkey ").toInt()


    return Monkey(startingItems.toMutableList(), ops, Tester(testValue, toTrue, toFalse))

}

data class Tester(val testValue: Int, val toTrue: Int, val toFalse: Int)

class Monkey(
    val items: MutableList<Int>,
    private val operation: String,
    private val tester: Tester
) {
    var inspectedItems: Int = 0

    fun inspectItems(): List<Pair<Int, Int>> {
        val result = items.map { inspectItem(it) }
        items.clear()
        return result

    }

    private fun performOperation(item: Int): Int {

        val (operator, modifier) = operation.split(" ")

        val modValue = if (modifier == "old") item else modifier.toInt()
        val calculatedValue = if (operator == "+") item + modValue else item * modValue

        return calculatedValue / 3
    }

    private fun inspectItem(item: Int): Pair<Int, Int> {
        val worryLevel = performOperation(item)
        val toMonkey = runTests(worryLevel)
        this.inspectedItems++
        return Pair(toMonkey, worryLevel)

    }

    private fun runTests(worryLevel: Int): Int {

        return if (worryLevel % tester.testValue == 0) {
            tester.toTrue
        } else {
            tester.toFalse
        }


    }


}



