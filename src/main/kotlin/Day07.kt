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

data class Rank2(val card: Char) : Comparable<Rank2> {
    override fun compareTo(other: Rank2): Int = position().compareTo(other.position())

    private fun position(): Int = when (card) {
        'A' -> 13
        'K' -> 12
        'Q' -> 11
        'T' -> 10
        '9' -> 9
        '8' -> 8
        '7' -> 7
        '6' -> 6
        '5' -> 5
        '4' -> 4
        '3' -> 3
        '2' -> 2
        'J' -> 1
        else -> 0
    }
}

data class HandBid(val hand: String, val bid: Int) : Comparable<HandBid> {
    override fun compareTo(other: HandBid): Int = if (strength() == other.strength()) {
        val s = hand.toCharArray().map { Rank(it) }
        val o = other.hand.toCharArray().map { Rank(it) }
        s.zip(o).map { it.first.compareTo(it.second) }.firstOrNull { it != 0 } ?: 0
    } else {
        strength().compareTo(other.strength())
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

data class HandBid2(val hand: String, val bid: Int) : Comparable<HandBid2> {
    override fun compareTo(other: HandBid2): Int = if (strength() == other.strength()) {
        val s = hand.toCharArray().map { Rank(it) }
        val o = other.hand.toCharArray().map { Rank(it) }
        s.zip(o).map { it.first.compareTo(it.second) }.firstOrNull { it != 0 } ?: 0
    } else {
        strength().compareTo(other.strength())
    }

    private fun strength(): Int {
        val sameCardsCounts = hand.groupBy { it }.values.map { it.count() }
        val countJ = hand.count { it == 'J' }
        val aa = when {
            sameCardsCounts.contains(5) -> 7
            sameCardsCounts.contains(4) -> if (countJ == 0) 6 else 7
            sameCardsCounts.contains(3) && sameCardsCounts.contains(2) -> when (countJ) {
                0 -> 5
                2 -> 7
                3 -> 7
                else -> 5
            }
            sameCardsCounts.contains(3) -> when (countJ) {
                0 -> 4
                1 -> 6
                else -> 4
            }

            sameCardsCounts.count { it == 2 } == 2 -> when (countJ) {
                0 -> 3
                1 -> 5
                2 -> 6
                else -> 3
            }

            sameCardsCounts.contains(2) -> if (countJ == 0) 2 else 4
            else -> 1
        }
        return aa
    }
}

fun main() {

    fun part3(lines: List<String>): Int {
        data class Hand(val cards: List<Int>, val groups: List<Int>, val bid: Int)
        val values = listOf('T', 'Q', 'K', 'A')
        return lines
            .map { it.split(" ") }.map { (text, bid) ->
                val cards = text.map { card -> values.indexOf(card).let { if (it > -1) it + 10 else card.digitToIntOrNull() ?: 1 } }
                println(cards)
                val groups = (2..13)
                    .map { swap -> cards.map { if (it == 1) swap else it }.groupBy { it }.map { it.value.size }.sortedByDescending { it } }
                    .sortedWith(compareBy({ it[0] }, { it.getOrNull(1) }))
                    .last()
                println(groups)
                Hand(cards, groups, bid.toInt())
            }
            .sortedWith(compareBy({ it.groups[0] }, { it.groups.getOrNull(1) }, { it.cards[0] }, { it.cards[1] }, { it.cards[2] }, { it.cards[3] }, { it.cards[4] }))
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
    }


    fun part1(input: List<String>): Int {
        val handsAndBids = input.map { it.split(" ") }.map { HandBid(it.first(), it.last().toInt()) }
        println(handsAndBids.sorted().mapIndexed { index, handBid -> (index + 1) * handBid.bid })
        return handsAndBids.sorted().mapIndexed {index, handBid -> (index + 1) * handBid.bid }.sum()
    }

    fun part2(input: List<String>): Int {
        val handsAndBids = input.map { it.split(" ") }.map { HandBid2(it.first(), it.last().toInt()) }
        println(handsAndBids.sorted().mapIndexed { index, handBid -> handBid.bid })
        return handsAndBids.sorted().mapIndexed {index, handBid -> (index + 1) * handBid.bid }.sum()
    }

    val testInputPart1 = readInput("Day07_test")
    val input = readInput("Day07")
//    check(part1(testInputPart1) == 6440)
//    part1(input).println()
//    println(HandBid2("JJJ33", 1).compareTo(HandBid2("22233", 2)))
    check(part3(testInputPart1) == 5905)
    part3(input).println()
}
