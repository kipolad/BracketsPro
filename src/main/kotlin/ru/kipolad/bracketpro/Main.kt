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
                print(queueManager.getWrongIndex())
                break
            }
        }
    }
    if (isNormal) {
        val index = queueManager.getWrongIndex()
        if (index != BracketQueueManager.INDEX_NONE) {
            print(index)
        } else {
            print("Success")
        }
    }
}
