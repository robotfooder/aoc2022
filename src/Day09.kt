data class Step(val dir: Direction, val length: Int)
data class Move(val x: Int, val y: Int)
enum class Direction(val move: Move) {
    UP(Move(0, 1)),
    DOWN(Move(0, -1)),
    LEFT(Move(-1, 0)),
    RIGHT(Move(1, 0));

    companion object {
        fun fromString(dir: String): Direction {

            return when (dir) {
                "U" -> UP
                "D" -> DOWN
                "R" -> RIGHT
                "L" -> LEFT
                else -> error(dir)
            }
        }
    }
}


fun main() {


    fun getDiff(first: Int, second: Int): Int {
        val diff = first - second
        return if (diff > 1) {
            1
        } else if (diff < -1) {
            -1
        } else {
            diff
        }

    }

    fun part1(input: List<String>): Int {

        val steps = input.map { it.toStep() }

        var headPosition = Pair(0, 0)
        val tailPositions = mutableSetOf<Pair<Int, Int>>()
        tailPositions.add(Pair(0, 0))

        for (step in steps) {

            repeat(step.length) {
                headPosition += step.dir.move
                val lastTailPosition = tailPositions.last()

                if (!headPosition.isTouching(lastTailPosition)) {
                    val yDiff = getDiff(headPosition.second, lastTailPosition.second)
                    val xDiff = getDiff(headPosition.first, lastTailPosition.first)
                    val newTailPos = Pair(lastTailPosition.first + xDiff, lastTailPosition.second + yDiff)
                    tailPositions.add(newTailPos)
                }

            }

        }

        return tailPositions.size
    }


    fun part2(input: List<String>): Int {
        val tailPositions = mutableSetOf<Pair<Int, Int>>()

        val steps = input.map { it.toStep() }
        val knots = MutableList(10) {
            Pair(0, 0)
        }

        for (step in steps) {
            repeat(step.length) {
                knots[0] = knots[0] + step.dir.move

                for (i in 0 until knots.size - 1) {
                    if (!knots[i].isTouching(knots[i + 1])) {
                        val yDiff = getDiff(knots[i].second, knots[i + 1].second)
                        val xDiff = getDiff(knots[i].first, knots[i + 1].first)
                        knots[i + 1] = Pair(knots[i + 1].first + xDiff, knots[i + 1].second + yDiff)
                    }
                }

                tailPositions.add(knots.last())

            }
        }

        return tailPositions.size


    }

    val day = "09"

// test if implementation meets criteria from the description, like:
    runTest(46, day, ::part1)
    runTest(36, day, ::part2)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private operator fun Pair<Int, Int>.plus(move: Move): Pair<Int, Int> = Pair(this.first + move.x, this.second + move.y)


private fun Pair<Int, Int>.isTouching(otherPair: Pair<Int, Int>): Boolean {

    val xDIff = this.first - otherPair.first
    val yDIff = this.second - otherPair.second

    val intRange = -1..1
    return xDIff in intRange && yDIff in intRange

}


fun String.toStep() = Step(
    Direction.fromString(substringBefore(" ").uppercase()), substringAfter(" ").toInt()
)
