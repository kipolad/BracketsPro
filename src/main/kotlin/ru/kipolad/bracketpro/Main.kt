package ru.kipolad.bracketpro

import java.util.*

fun main() {
    print("Введите строку: ")
    val console = Scanner(System.`in`)
    val str = console.nextLine()
    val queueManager = BracketQueueManager()
    val chars = str.toCharArray()
    var isNormal = true
    for (i in chars.indices) {
        if (queueManager.isBracketChar(chars[i])) {
            isNormal = queueManager.isBracketNormal(chars[i], i)
            if (!isNormal) {
                printWrongStringWithIndex(str, queueManager.getWrongIndex())
                break
            }
        }
    }
    if (isNormal) {
        val index = queueManager.getWrongIndex()
        if (index != BracketQueueManager.INDEX_NONE) {
            printWrongStringWithIndex(str, index)
        } else {
            printSuccessString(str)
        }
    }
}

private fun printWrongStringWithIndex(str: String, index: Int) {
    System.out.printf("%s: %d %n", str, index)
}

private fun printSuccessString(str: String) {
    System.out.printf("%s: Success! %n", str)
}
