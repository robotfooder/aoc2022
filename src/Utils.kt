import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun <T> List<T>.chunkedBy(selector: (T) -> Boolean): List<List<T>> =
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