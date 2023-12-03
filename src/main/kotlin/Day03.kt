import kotlin.math.max

fun main() {
    fun solvePart1(line: String, lineIndex: Int, coords: List<List<Char>>): Sequence<Int> {
        val numbers = "\\d+".toRegex().findAll(line)
        return numbers.map {
            val foundRange = it.range
            val searchRange = IntRange(foundRange.first - 1, foundRange.last + 1)
            val ups = coords.elementsAt(lineIndex - 1).`in`(searchRange)
            val downs = coords.elementsAt(lineIndex + 1).`in`(searchRange)
            val left = coords[lineIndex].getOrElse(foundRange.first - 1) { '.' }
            val right = coords[lineIndex].getOrElse(foundRange.last + 1) { '.' }
            val hasSymbolAround = (ups + downs + left + right).any { c -> c != '.' }
            if (hasSymbolAround) {
                it.value.toInt()
            } else {
                0
            }
        }
    }

    fun findGear(list: List<Char>, start: Int = 0): MatchResult? = "\\*".toRegex().find(list.joinToString(separator = ""), start)

    fun solvePart2(line: String, lineIndex: Int, coords: List<List<Char>>): List<Pair<Int, Pair<Int?, Int>>> {
        val numbers = "\\d+".toRegex().findAll(line)
        return numbers.map {
            val foundRange = it.range
            val searchRange = IntRange(foundRange.first - 1, foundRange.last + 1)
            val ups = coords.elementsAt(lineIndex - 1).`in`(searchRange)
            val downs = coords.elementsAt(lineIndex + 1).`in`(searchRange)
            val left = coords[lineIndex].getOrElse(foundRange.first - 1) { '.' }
            val right = coords[lineIndex].getOrElse(foundRange.last + 1) { '.' }

            val isUp = findGear(ups)?.range
            val posDown = findGear(downs)?.range
            val hasGearAround = (ups + downs + left + right).any { c -> c == '*' }
            if (hasGearAround) {
                if (isUp != null) {
                    Pair(it.value.toInt(), Pair(findGear(coords[lineIndex - 1], max(searchRange.first, 0))!!.range.first, lineIndex - 1))
                } else if (posDown != null) {
                    Pair(it.value.toInt(), Pair(findGear(coords[lineIndex + 1], max(searchRange.first, 0))!!.range.first, lineIndex + 1))
                } else {
                    Pair(it.value.toInt(), Pair(findGear(coords[lineIndex], max(searchRange.first, 0))?.range?.first, lineIndex))
                }
            } else {
                Pair(0, Pair(0, 0))
            }
        }.toList()
    }

    fun part1(input: List<String>): Int {
        val coords = input.map { it.toList() }
        return input.mapIndexed { idx, line -> solvePart1(line, idx, coords) }
            .flatMap { it }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val coords = input.map { it.toList() }
        return input.flatMapIndexed { idx, line -> solvePart2(line, idx, coords) }
            .groupBy({ it.second }, { it.first })
            .filter { it.key != 0 to 0 && it.value.size > 1 }
            .map { it.value.reduce { acc, i -> acc * i } }
            .sum()
    }

    val testInputPart1 = readInput("Day03_test")
    val input = readInput("Day03")
    check(part1(testInputPart1) == 4361)
    part1(input).println()

    val testInputPart2 = readInput("Day03_test")
    check(part2(testInputPart2) == 467835)
    part2(input).println()
    check(part2(input) == 84883664)
}

fun<T> List<List<T>>.elementsAt(index: Int): List<T> = this.getOrElse(index) { emptyList() }
fun<T> List<T>.`in`(range: IntRange): List<T> = this.filterIndexed { idx, _ -> range.contains(idx) }
