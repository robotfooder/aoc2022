fun main() {


    fun runRounds(monkeys: List<Monkey>, rounds: Int, reliefer: (Long) -> Long): Long {

        for (round in 1..rounds) {

            monkeys.forEach { monkey ->
                monkey.inspectItems(reliefer).forEach {
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
            }.toLong()
    }

    fun part1(input: List<String>): Long {
        val rounds = 20

        val monkeys = input
            .chunkedBy { it.isBlank() }
            .map { it.toMonkey() }

        return runRounds(monkeys, rounds) {
            it / 3
        }


    }

    fun part2(input: List<String>): Long {

        val rounds = 10000

        val monkeys = input
            .chunkedBy { it.isBlank() }
            .map { it.toMonkey() }

        val modValue = monkeys
            .map { it.tester.testValue }
            .reduce { acc, i ->
                acc * i
            }


        return runRounds(monkeys, rounds) {
            it % modValue
        }


    }

    val day = "11"

// test if implementation meets criteria from the description, like:
    runTestLong(10605, day, ::part1)
    runTestLong(2713310158, day, ::part2)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toMonkey(): Monkey {
    val startingItems = this[1]
        .substringAfter("Starting items: ")
        .split(",")
        .map { it.trim() }
        .map(String::toLong)

    val id = this[0].substringAfter("Monkey ").replace(":", "").toInt()
    val ops = this[2].substringAfter("Operation: new = old ")
    val testValue = this[3].substringAfter("Test: divisible by ").toLong()
    val toTrue = this[4].substringAfter("If true: throw to monkey ").toInt()
    val toFalse = this[5].substringAfter("If false: throw to monkey ").toInt()


    return Monkey(
        id,
        startingItems.toMutableList(),
        ops,
        Tester(testValue, toTrue, toFalse)
    )

}

data class Tester(val testValue: Long, val toTrue: Int, val toFalse: Int)

data class Monkey(
    val id: Int,
    var items: MutableList<Long>,
    private val operation: String,
    val tester: Tester,
) {
    var inspectedItems: Long = 0

    fun inspectItems(reliefer: (Long) -> Long): List<Pair<Int, Long>> {
        val result = items.map { inspectItem(it, reliefer) }
        items.clear()
        return result

    }

    private fun performOperation(item: Long): Long {

        val (operator, modifier) = operation.split(" ")
        val modValue = if (modifier == "old") item else modifier.toLong()
        return if (operator == "+") item + modValue else item * modValue
    }

    private fun inspectItem(item: Long, reliefer: (Long) -> Long): Pair<Int, Long> {
        val worryLevel = performOperation(item)
        if(worryLevel<0){
            error(this)
        }
        val worryLevelAfterRelief = reliefer(worryLevel)

        val toMonkey = runTests(worryLevelAfterRelief)
        this.inspectedItems++
        return Pair(toMonkey, worryLevelAfterRelief)

    }


    private fun runTests(worryLevel: Long): Int {

        return if (worryLevel % tester.testValue == 0L) tester.toTrue else tester.toFalse


    }


}


