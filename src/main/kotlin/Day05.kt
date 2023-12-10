data class Mapping(val source: Long, val destination: Long, val range: Long)

fun main() {
    fun findBackward(mappings: List<List<Mapping>>, target: Long): Long {
        var next = target
        for (mapping in mappings.reversed()) {
            val map = mapping.map { next.isInRangeOfDestination(it) }.find { it != null }
            if (map != null) {
                next = next - map.destination + map.source
            }
        }
        return next
    }

    fun readSeeds(input: List<String>) = input.first()
        .substringAfter(": ")
        .split(" ")
        .map { it.toLong() }

    fun readMappings(input: List<String>): List<List<Mapping>> =
        input.drop(2).filter { it.isNotEmpty() }.fold(mutableListOf<MutableList<Mapping>>()) { acc, line ->
            if (line.isDeclaringMapping()) {
                acc.addLast(mutableListOf())
            } else {
                val (dest, src, range) = line.split(" ").map { it.toLong() }
                acc.last().add(Mapping(src, dest, range))
                acc.last().sortBy { a -> a.destination }
            }
            acc
        }

    fun part1(input: List<String>): Long {
        val seeds = readSeeds(input)
        val mappings = readMappings(input)
        val max = mappings.last().last()
        var i = 1L
        var destination = 0L
        do {
            val found = findBackward(mappings, i)
            if (found in seeds) {
                destination = i
            }
        } while (++i in 0..<max.destination + max.range && destination == 0L)
        return destination
    }

    fun part2(input: List<String>): Long {
        val seeds = readSeeds(input)
        val mappings = readMappings(input)
        val max = mappings.last().last()
        var i = 1L
        var destination = 0L
        do {
            val found = findBackward(mappings, i)
            if (isInSeedsRange(found, seeds)) {
                destination = i - 1
            }
        } while (++i in 0..<max.destination + max.range && destination == 0L)
        return destination
    }

    val testInputPart1 = readInput("Day05_test")
    val input = readInput("Day05")
    check(part1(testInputPart1) == 35L)
    part1(input).println()

    val testInputPart2 = readInput("Day05_test")
    check(part2(testInputPart2) == 46L)
    part2(input).println()
}

private fun Long.isInRangeOfDestination(map: Mapping): Mapping? =
    if (map.destination < this && this < map.destination + map.range) {
        map
    } else {
        null
    }

private fun String.isDeclaringMapping(): Boolean = this.contains("^[a-z]".toRegex())

private fun isInSeedsRange(found: Long, seeds: List<Long>): Boolean = seeds.chunked(2)
    .map { it.first() <= found && found <= it.first() + it.last() }
    .any { it }
