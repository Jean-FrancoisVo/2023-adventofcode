import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/main/resources/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.toDigit(): String = when {
    this == "one" -> "1"
    this == "two" -> "2"
    this == "three" -> "3"
    this == "four" -> "4"
    this == "five" -> "5"
    this == "six" -> "6"
    this == "seven" -> "7"
    this == "eight" -> "8"
    this == "nine" -> "9"
    else -> this
}

fun <T> around(i: Int, j: Int, a: List<List<T>>) = listOf(
    a[i - 1][j - 1], a[i - 1][j + 0], a[i - 1][j + 1],
    a[i + 0][j - 1],                  a[i + 0][j + 1],
    a[i + 1][j - 1], a[i + 1][j + 0], a[i + 1][j + 1],
)
