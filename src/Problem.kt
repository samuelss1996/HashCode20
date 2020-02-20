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

        println("Score: ${solution.calculateFinalScore()}")
    }
}

class Library(val problem: Problem, val id: Int, scanner: Scanner) {
    val bookCount: Int
    val signupDays: Int
    val booksPerDay: Int
    val books: HashSet<Int>

    //Solution
    val chosenBooks = mutableListOf<Int>()

    // Heuristics
    val optimalScore: Int
    val optimalScoreSd: Float

    init {
        bookCount = scanner.nextInt()
        signupDays = scanner.nextInt()
        booksPerDay = scanner.nextInt()

        books = HashSet(bookCount)
        for(i in 0 until bookCount) {
          books.add(scanner.nextInt())
        }

        var optimalTake = howManyCanScan(signupDays)
        optimalTake = if(optimalTake > 0) optimalTake else 0
        val optimalScoreSet = books.map { problem.books[it].score }.sortedDescending().take(optimalTake)

        optimalScore = optimalScoreSet.sum()
        optimalScoreSd = calculateSD(optimalScoreSet.toIntArray())
    }

    // TODO improve this because it disregards variation of scores
    fun estimateReward(): Float {
        val days = signupDays + books.size / booksPerDay
        return optimalScore.toFloat() * optimalScoreSd / days
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

    fun calculateSD(numArray: IntArray): Float {
        var sum = 0.0
        var standardDeviation = 0.0

        for (num in numArray) {
            sum += num
        }

        val mean = sum / 10

        for (num in numArray) {
            standardDeviation += Math.pow(num - mean, 2.0)
        }

        return Math.sqrt(standardDeviation / 10).toFloat()
    }
}

class Book(val problem: Problem, val id: Int, val score: Int)