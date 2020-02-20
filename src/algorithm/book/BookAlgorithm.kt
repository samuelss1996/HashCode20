package algorithm.book

import Library
import algorithm.library.LibraryAlgorithm

abstract class BookAlgorithm(val algorithm: LibraryAlgorithm) {
    val idleDays: Array<Int>

    init {
        idleDays = Array(algorithm.libraries.size) { 0 }

        var currentIdle = 0
        idleDays.indices.forEach { index ->
            currentIdle += algorithm.libraries[index].signupDays
            idleDays[index] = currentIdle
        }
    }

    abstract fun solve()
}