data class Instruction(val qty: Int, val from: Int, val to: Int)

fun main() {


    fun initStack(columns: Int): List<ArrayDeque<String>> = buildList {
        for (x in 0 until columns) {
            add(ArrayDeque())
        }
    }

    fun parseToStack(input: List<String>): List<ArrayDeque<String>> {
        var columns: Int
        return input
            .filter { it.contains("[") }
            .map { it.chunked(4) }
            .also { columns = it.first().size }
            .map { strings ->
                strings.map { it.trim() }
                    .map { it.replace("[", "") }
                    .map { it.replace("]", "") }
            }
            .fold(initStack(columns)) { acc, row ->
                row.forEachIndexed { index, s ->
                    if (s.isNotEmpty()) {
                        val stack = acc[index]
                        stack.add(s)
                    }
                }
                acc
            }
    }


    fun parseInstructions(input: List<String>): List<Instruction> {
        return input
            .filter { it.startsWith("move") }
            .map { it.toInstruction() }


    }

    fun part1(input: List<String>): String {

        val stacks = parseToStack(input)
        val instructions = parseInstructions(input)


        instructions.forEach { instruction ->
            val fromStack = stacks[instruction.from - 1]
            val toStack = stacks[instruction.to - 1]
            fromStack
                .take(instruction.qty)
                .onEach {
                    toStack.addFirst(it)
                    fromStack.remove(it)
                }
        }
        return stacks.joinToString(separator = "") {
            it.first()
        }
    }

    fun part2(input: List<String>): String {
        val stacks = parseToStack(input)
        val instructions = parseInstructions(input)

        instructions.forEach { instruction ->
            val fromStack = stacks[instruction.from - 1]
            val toStack = stacks[instruction.to - 1]
            val take = fromStack.take(instruction.qty)
            toStack.addAll(0, take)
            take.forEach { fromStack.remove(it) }

        }

        return stacks.joinToString(separator = "") {
            if (it.isNotEmpty()) it.first() else ""
        }
    }


    fun runTest(expected: String, day: String, testFunction: (List<String>) -> String) {
        val actual = testFunction(readTestInput(day))
        check(expected == actual)
        {
            "Failing for day $day, ${testFunction}. " +
                    "Expected $expected but got $actual"
        }
    }


    val day = "05"

// test if implementation meets criteria from the description, like:
    runTest("CMZ", day, ::part1)
    runTest("MCD", day, ::part2)


    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

fun String.toInstruction(): Instruction {
    val inputRegex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    val (qty, from, to) = inputRegex
        .matchEntire(this)
        ?.destructured
        ?: throw IllegalArgumentException("Incorrect input line $this")

    return Instruction(qty.toInt(), from.toInt(), to.toInt())
}



