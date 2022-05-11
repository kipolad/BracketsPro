package ru.kipolad.bracketpro

import java.util.*

class BracketQueueManager {

    companion object {
        const val INDEX_NONE = 0
    }

    private val bracketOpenChars: MutableSet<Char> = HashSet()
    private val bracketCloseChars: MutableSet<Char> = HashSet()
    private val queue: Deque<BracketItem>
    private var wrongIndex = INDEX_NONE

    init {
        initBracketSets()
        queue = LinkedList()
    }

    private fun initBracketSets() {
        for (bracket in Bracket.values()) {
            bracketOpenChars.add(bracket.openBracket)
            bracketCloseChars.add(bracket.closeBracket)
        }
    }

    fun isBracketChar(bracket: Char): Boolean =
        bracketOpenChars.contains(bracket) ||
                bracketCloseChars.contains(bracket)

    fun isBracketNormal(bracket: Char, index: Int): Boolean {
        return if (bracketOpenChars.contains(bracket)) {
            handleOpenBracketChar(bracket, index)
            true
        } else {
            handleCloseBracketChar(bracket, index)
        }
    }

    private fun handleOpenBracketChar(bracket: Char, index: Int) {
        val bracketType: Bracket = when (bracket) {
            Bracket.ROUND.openBracket -> Bracket.ROUND
            Bracket.SQUARE.openBracket -> Bracket.SQUARE
            Bracket.CROCHET.openBracket -> Bracket.CROCHET
            Bracket.ANGLE.openBracket -> Bracket.ANGLE
            else -> throw RuntimeException("Unknown bracket type")
        }
        queue.addFirst(BracketItem(bracketType, index + 1))
    }

    private fun handleCloseBracketChar(bracket: Char, index: Int): Boolean {
        if (queue.size != 0) {
            val closeBracket = queue.peek().bracket.closeBracket
            if (closeBracket == bracket) {
                queue.remove()
                return true
            }
        }
        wrongIndex = index + 1
        return false
    }

    fun getWrongIndex(): Int = if (wrongIndex == INDEX_NONE) {
        if (queue.size != 0) {
            queue.peek().index
        } else INDEX_NONE
    } else {
        wrongIndex
    }
}