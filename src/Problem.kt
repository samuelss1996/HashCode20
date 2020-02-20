import java.io.File
import java.util.*

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

    fun writeSolution(solutionWrapper: SolutionWrapper) {
        outFile.printWriter().use { writer ->
            writer.println(solutionWrapper.countTrues())

            for (i in 0 until size) {
                if (solutionWrapper.solution[i]) {
                    writer.print(i)
                    writer.print(" ")
                }
            }
        }
    }
}

class Library(val problem: Problem, val id: Int, scanner: Scanner) {
    val bookCount: Int
    val signupDays: Int
    val booksPerDay: Int
    val books: Array<Int>

    //Solution
    val chosenBooks = mutableListOf<Int>()

    init {
        bookCount = scanner.nextInt()
        signupDays = scanner.nextInt()
        booksPerDay = scanner.nextInt()

        books = Array(bookCount) { scanner.nextInt() }
    }

    fun howManyCanScan(idleDays: Int): Int {
        val availableDays = problem.scanningDays - idleDays
        return availableDays * booksPerDay
    }

    fun addBook(bookId: Int) {
        chosenBooks.add(bookId)
    }
}

class Book(val problem: Problem, val id: Int, val score: Int)