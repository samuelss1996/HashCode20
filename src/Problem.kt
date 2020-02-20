import java.io.File
import java.util.*

class Problem(inFile: File, val outFile: File) {
    val bookCount: Int
    val libraryCount: Int
    val scanningDays: Int

    val scores: Array<Int>
    val libraries: Array<Library>

    init {
        val scanner = Scanner(inFile)

        bookCount = scanner.nextInt()
        libraryCount = scanner.nextInt()
        scanningDays = scanner.nextInt()

        scores = Array(bookCount) { scanner.nextInt() }
        libraries = Array(libraryCount) { Library(scanner) }
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

class Library(scanner: Scanner) {
    val bookCount: Int
    val signupDays: Int
    val booksPerDay: Int
    val books: Array<Int>

    init {
        bookCount = scanner.nextInt()
        signupDays = scanner.nextInt()
        booksPerDay = scanner.nextInt()

        books = Array(bookCount) { scanner.nextInt() }
    }
}