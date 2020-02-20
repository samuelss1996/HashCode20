package algorithm.book

import Library
import algorithm.library.LibraryAlgorithm

class GreedyBookAlgorithm(algorithm: LibraryAlgorithm) : BookAlgorithm(algorithm) {

    override fun solve() {
        algorithm.libraries.forEach { library ->
            library.clearBooks()
        }

        algorithm.problem.books.sortedByDescending { it.score }.forEach books@{ book ->
            algorithm.libraries.forEachIndexed { index, library ->
                if(library.howManyCanScan(idleDays[index]) > 0 && library.hasBookAvailable(book.id)) {
                    library.addBook(book.id)
                    return@books
                }
            }
        }
    }
}