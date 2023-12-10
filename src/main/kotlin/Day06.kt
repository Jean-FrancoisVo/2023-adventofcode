fun main() {
    fun part1(input: List<String>): Int {
        val regex = "\\d+".toRegex()
        val times = regex.findAll(input.first()).map { it.value.toLong() }.toList()
        val distances = regex.findAll(input.last()).map { it.value.toLong() }.toList()
        val inputs = times.zip(distances)
        return inputs.map { i ->
            LongRange(1, i.first - 1).map { it * (i.first - it) }.count { it > i.second }
        }.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val regex = "\\d+".toRegex()
        val times = regex.findAll(input.first()).fold("") { acc, i -> "${acc}${i.value}" }.toLong()
        val distances = regex.findAll(input.last()).fold("") { acc, i -> "${acc}${i.value}" }.toLong()
        return LongRange(1, times - 1).map { it * (times - it) }.count { it > distances }
    }

    val testInputPart1 = readInput("Day06_test")
    val input = readInput("Day06")
    check(part1(testInputPart1) == 288)
    part1(input).println()

    check(part2(testInputPart1) == 71503)
    part2(input).println()
}
