fun main() {
    fun solvePart1(line: String): Int {
        val (id, cubes) = line.split(":")
        val impossibleRed = "(\\d+) red".toRegex().findImpossible(cubes, 12)
        val impossibleGreen = "(\\d+) green".toRegex().findImpossible(cubes, 13)
        val impossibleBlue = "(\\d+) blue".toRegex().findImpossible(cubes, 14)
        return if (impossibleRed || impossibleGreen || impossibleBlue) {
            0
        } else {
            return "\\d+".toRegex().find(id)!!.value.toInt()
        }
    }

    fun solvePart2(line: String): Int {
        val (_, cubes) = line.split(":")
        val minRed = "(\\d+) red".toRegex().findMax(cubes)
        val minGreen = "(\\d+) green".toRegex().findMax(cubes)
        val minBlue = "(\\d+) blue".toRegex().findMax(cubes)
        return minRed * minGreen * minBlue
    }

    fun part1(input: List<String>): Int = input.sumOf { solvePart1(it) }

    fun part2(input: List<String>): Int = input.sumOf { solvePart2(it) }

    val testInputPart1 = readInput("Day02_test")
    val input = readInput("Day02")
    check(part1(testInputPart1) == 8)
    part1(input).println()

    val testInputPart2 = readInput("Day02_test")
    check(part2(testInputPart2) == 2286)
    part2(input).println()
}

private fun Regex.findMax(cubes: String): Int = this.findAll(cubes)
    .map { it.groups.last()!!.value.toInt() }
    .max()

fun Regex.findImpossible(input: String, max: Int) = this.findAll(input)
    .any { it.groups.last()!!.value.toInt() > max }
