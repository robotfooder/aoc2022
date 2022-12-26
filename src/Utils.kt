import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "main/resources/$name/input.txt")
    .readLines()

fun readTestInput(name: String) = File("src", "main/resources/$name/input_test.txt")
    .readLines()

fun <T> List<T>.chunkedBy(selector: (T) -> Boolean) =
    fold(mutableListOf(mutableListOf<T>())) { acc, ele ->

        if (selector(ele)) {
            acc.add(mutableListOf())
        } else {
            acc.last().add(ele)
        }
        acc
    }


/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun String.splitToPair(index: Int): Pair<String, String> = this.substring(0, index) to this.substring(index)

fun Char.score(): Int = if (this.isUpperCase()) this.code - 38 else this.code - 96

fun runTest(expected: Int, day: String, testFunction: (List<String>) -> Int) {
    val actual = testFunction(readTestInput(day))
    check(expected == actual)
    {
        "Failing for day $day, ${testFunction}. " +
                "Expected $expected but got $actual"
    }
}

