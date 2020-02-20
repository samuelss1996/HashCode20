import java.util.*
import kotlin.math.abs

class SolutionWrapper(val problem: Problem, val solution: Array<Library>) {

    fun isBetterThan(other: SolutionWrapper?): Boolean {
        return calculateFinalScore() > other?.calculateFinalScore() ?: 0
    }

    fun calculateFinalScore(): Int {
        var score = 0

        solution.forEach { library ->
            library.chosenBooks.forEach { bookId ->
                score += problem.books[bookId].score
            }
        }

        return score
    }

    fun calculateHeuristicCost(): Int {
        return -calculateFinalScore()
    }
}