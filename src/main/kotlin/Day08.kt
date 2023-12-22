fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input.first()
        val regex = "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex()

        input.drop(2)
            .map { regex.matchEntire(it)!!.groupValues }
            .fold(mutableMapOf<String, Pair<String, String>>()) { acc, values ->
                acc[values[1]] = (values[2] to values[3])
                acc
            }

//        val values = regex.matchEntire(input[2])!!.groupValues
//        mapOf(values[1] to (values[2] to values[3]))
        return 1
    }

//    fun part2(input: List<String>): Int {
//
//    }

    val testInputPart1 = readInput("Day08_test")
//    val input = readInput("Day08")
    check(part1(testInputPart1) == 2)
//    part1(input).println()
}
