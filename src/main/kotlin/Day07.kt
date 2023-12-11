data class HandBid(val value: String, val bid: Int) : Comparable<HandBid> {
    override fun compareTo(other: HandBid): Int {
        TODO("Not yet implemented")
    }

    fun strength(): Int {
        val hand = this.value.groupBy { it }.values.map { it.count() }
        return when {
            hand.contains(5) -> 7
            hand.contains(4) -> 6
            hand.contains(3) && hand.contains(2) -> 5
            hand.contains(3) -> 4
            hand.count { it == 2 } == 2 -> 3
            hand.contains(2) -> 2
            else -> 1
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val handsAndBids = input.map { it.split(" ") }.map { HandBid(it.first(), it.last().toInt()) }
        println(handsAndBids[4])
        println(handsAndBids[4].strength())
//        val handStrengths = handsAndBids.map { p -> p.value.groupBy { it }}
//        println(handStrengths[2].values.map { it.size })
//        println(handStrengths)
        return 1
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
