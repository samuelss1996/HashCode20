import java.io.File
import java.util.*
import kotlin.collections.HashSet

class Problem(inFile: File, val outFile: File) {
    val bookCount: Int
    val libraryCount: Int
    val scanningDays: Int

    val books: Array<Book>
    val libraries: Array<Library>

    init {
        val scanner = Scanner(inFile)

        bookCount = scanner.nextInt()
        libraryCount = scanner.nextInt()
        scanningDays = scanner.nextInt()

        books = Array(bookCount) { Book(this, it, scanner.nextInt()) }
        libraries = Array(libraryCount) { Library(this, it, scanner) }
    }

    fun writeSolution(solution: SolutionWrapper) {
        outFile.printWriter().use { writer ->
            val libaries = solution.solution.filter { it.chosenBooks.size > 0 }

            writer.println(libaries.size)

            libaries.forEach { library ->
                writer.print(library.id)
                writer.print(" ")
                writer.print(library.chosenBooks.size)
                writer.println()

                writer.print(library.chosenBooks.joinToString(" "))
                writer.println()
            }
        }
    }
}

class Library(val problem: Problem, val id: Int, scanner: Scanner) {
    val bookCount: Int
    val signupDays: Int
    val booksPerDay: Int
    val books: HashSet<Int>

    //Solution
    val chosenBooks = mutableListOf<Int>()

    init {
        bookCount = scanner.nextInt()
        signupDays = scanner.nextInt()
        booksPerDay = scanner.nextInt()

        books = HashSet(bookCount)
        for(i in 0 until bookCount) {
          books.add(scanner.nextInt())
        }
    }

    fun howManyCanScan(idleDays: Int): Int {
        val availableDays = problem.scanningDays - idleDays
        return availableDays * booksPerDay - chosenBooks.size
    }

    fun hasBookAvailable(bookId: Int): Boolean {
        return books.contains(bookId)
    }

    fun addBook(bookId: Int) {
        chosenBooks.add(bookId)
    }

    fun clearBooks() {
        chosenBooks.clear()
    }
}

class Book(val problem: Problem, val id: Int, val score: Int)