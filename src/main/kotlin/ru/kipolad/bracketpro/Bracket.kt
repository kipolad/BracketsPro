package ru.kipolad.bracketpro

enum class Bracket(val openBracket: Char, val closeBracket: Char) {
    ROUND('(', ')'),
    SQUARE('[', ']'),
    CROCHET('{', '}'),
    ANGLE('<', '>')
}