import kotlin.math.pow

fun main() {
    fun solvePart(line: String): Int {
        val (left, right) = line
            .substringAfter(":")
            .split("|")
            .map { "\\d+".toRegex().findAll(it).map { a -> a.value } }
            .map { it.toSet() }
        return (left intersect right).size
    }

    fun part1(input: List<String>): Int = input.sumOf { 2.0.pow(solvePart(it) - 1).toInt() }

    fun part2(input: List<String>): Int = input
        .foldIndexed(MutableList(input.size) { 1 }) { index, acc, s ->
            for (i in 1..solvePart(s)) {
                acc[index + i] += acc[index]
            }
            acc
        }.sum()

    val testInputPart1 = readInput("Day04_test")
    val input = readInput("Day04")
    check(part1(testInputPart1) == 13)
    part1(input).println()

    val testInputPart2 = readInput("Day04_test")
    check(part2(testInputPart2) == 30)
    part2(input).println()
}
