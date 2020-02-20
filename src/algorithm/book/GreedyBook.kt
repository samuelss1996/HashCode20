package algorithm.book

import Library
import algorithm.library.LibraryAlgorithm

class GreedyBook(algorithm: LibraryAlgorithm) : BookAlgorithm(algorithm) {

    override fun solve() {
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