import Shape.*
import Result.*
import java.lang.IllegalArgumentException

enum class Shape(val points: Int) { ROCK(1), PAPER(2), SCISSORS(3) }
enum class Result(val points: Int) { WIN(6), DRAW(3), LOOSE(0) }


fun main() {

    fun mapToShape(shape: String): Shape {
        return when (shape) {
            "X", "A" -> ROCK
            "Y", "B" -> PAPER
            "Z", "C" -> SCISSORS
            else -> {
                throw IllegalArgumentException("Could not convert $shape to a shape")
            }
        }
    }

    fun mapToResult(result: String): Result {
        return when (result) {
            "X" -> LOOSE
            "Y" -> DRAW
            "Z" -> WIN
            else -> {
                throw IllegalArgumentException("Could not convert $result to a result")
            }
        }
    }

    fun calculateResult(theirHand: Shape, myHand: Shape): Result {

        if (theirHand == myHand) {
            return DRAW
        }

        return when (myHand) {
            ROCK -> if (theirHand == SCISSORS) WIN else LOOSE
            PAPER -> if (theirHand == ROCK) WIN else LOOSE
            SCISSORS -> if (theirHand == PAPER) WIN else LOOSE
        }

    }

    fun calculate(play: Pair<Shape, Shape>): Int {
        val (theirGesture, myGesture) = play
        val result = calculateResult(theirGesture, myGesture)

        return myGesture.points + result.points

    }




    fun getShapeToMatchResult(strategy: Pair<Shape, Result>): Pair<Shape, Shape> {

        val (theirShape, expectedResult) = strategy
        val myShape = Shape.values().find { calculateResult(theirShape, it) == expectedResult }!!

        return theirShape to myShape

    }

    fun part1(input: List<String>): Int {

        return input
            .map { it.split(" ") }
            .map { mapToShape(it[0]) to mapToShape(it[1]) }
            .sumOf { calculate(it) }

    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .map { mapToShape(it[0]) to mapToResult(it[1]) }
            .map { getShapeToMatchResult(it) }
            .sumOf { calculate(it) }

    }

    fun runTest(expected: Int, day: String, testFunction: (List<String>) -> Int) {
        val actual = testFunction(readInput("Day${day}_test"))
        check(expected == actual)
        {
            "Failing for day $day, ${testFunction}. " +
                    "Expected $expected but got $actual"
        }
    }


    val day = "02"

    // test if implementation meets criteria from the description, like:
    runTest(15, day, ::part1)
    runTest(12, day, ::part2)


    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}


