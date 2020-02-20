package algorithm.book

import Library
import algorithm.library.LibraryAlgorithm

class GreedyBookAlgorithm(algorithm: LibraryAlgorithm) : BookAlgorithm(algorithm) {

    override fun solve() {
        algorithm.problem.books.sortedByDescending { it.score }.forEach { book ->
            algorithm.libraries.forEachIndexed libraries@{ index, library ->
                if(library.howManyCanScan(idleDays[index]) > 0) {
                    library.addBook(book.id)
                    return@libraries
                }
            }
        }
    }
}