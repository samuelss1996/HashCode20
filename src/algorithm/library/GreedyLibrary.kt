package algorithm.library

import Problem
import SolutionWrapper
import algorithm.book.GreedyBook

class GreedyLibrary(problem: Problem) : LibraryAlgorithm(problem) {
    var optimal: SolutionWrapper? = null

    override fun solve() : SolutionWrapper {
       for(i in 0 until 10) {
           print("Iteration ${i+1}/20\r")

           val rewards = FloatArray(problem.libraries.size) { problem.libraries[it].estimateReward() }
           libraries = problem.libraries.withIndex().sortedByDescending { rewards[it.index] }.map { it.value }.toTypedArray()
           libraries.forEach { it.clearBooks() }

           GreedyBook(this).solve()
           val current = SolutionWrapper(problem, libraries)

           if(current.isBetterThan(optimal)) {
               optimal = current
           }
       }

        println()

        return optimal!!
    }
}