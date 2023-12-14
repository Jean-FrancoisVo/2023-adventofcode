data class Rank(val card: Char) : Comparable<Rank> {
    override fun compareTo(other: Rank): Int = position().compareTo(other.position())

    private fun position(): Int = when (card) {
        'A' -> 13
        'K' -> 12
        'Q' -> 11
        'J' -> 10
        'T' -> 9
        '9' -> 8
        '8' -> 7
        '7' -> 6
        '6' -> 5
        '5' -> 4
        '4' -> 3
        '3' -> 2
        '2' -> 1
        else -> 0
    }
}

data class HandBid(val hand: String, val bid: Int) : Comparable<HandBid> {
    override fun compareTo(other: HandBid): Int {
        return if (this.strength() == other.strength()) {
            val s = hand.toCharArray().map { Rank(it) }
            val o = other.hand.toCharArray().map { Rank(it) }
            s.zip(o).map { it.first.compareTo(it.second) }.firstOrNull { it != 0 } ?: 0
        } else {
            this.strength().compareTo(other.strength())
        }
    }

    private fun strength(): Int {
        val sameCardsCounts = this.hand.groupBy { it }.values.map { it.count() }
        return when {
            sameCardsCounts.contains(5) -> 7
            sameCardsCounts.contains(4) -> 6
            sameCardsCounts.contains(3) && sameCardsCounts.contains(2) -> 5
            sameCardsCounts.contains(3) -> 4
            sameCardsCounts.count { it == 2 } == 2 -> 3
            sameCardsCounts.contains(2) -> 2
            else -> 1
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val handsAndBids = input.map { it.split(" ") }.map { HandBid(it.first(), it.last().toInt()) }
        println(handsAndBids.sorted().mapIndexed { index, handBid -> (index + 1) * handBid.bid })
        return handsAndBids.sorted().mapIndexed {index, handBid -> (index + 1) * handBid.bid }.sum()
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInputPart1 = readInput("Day07_test")
    val input = readInput("Day07")
    check(part1(testInputPart1) == 6440)
    part1(input).println()

//    check(part2(testInputPart1) == 71503)
//    part2(input).println()
}
