fun main() {
    fun solvePart1(input: String): String {
        val regex = "\\d".toRegex()
        val numbers = regex.findAll(input).toList()
        return "${numbers.first().value}${numbers.last().value}"
    }

    fun solvePart2(input: String): String {
        val regex = "(?=(one|two|three|four|five|six|seven|eight|nine|\\d))".toRegex()
        val numbers = regex.findAll(input).toList()
        return "${numbers.first().groupValues[1].toDigit()}${numbers.last().groupValues[1].toDigit()}"
    }

    fun part1(input: List<String>): Int = input.sumOf { solvePart1(it).toInt() }

    fun part2(input: List<String>): Int = input.sumOf { solvePart2(it).toInt() }

    val testInputPart1 = readInput("Day01part1_test")
    val testInputPart2 = readInput("Day01part2_test")
    check(part1(testInputPart1) == 142)
    check(part2(testInputPart2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
