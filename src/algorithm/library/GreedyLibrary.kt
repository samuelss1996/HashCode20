package algorithm.library

import Problem
import SolutionWrapper
import algorithm.book.GreedyBook

class GreedyLibrary(problem: Problem) : LibraryAlgorithm(problem) {

    override fun solve() : SolutionWrapper {
        libraries = problem.libraries.sortedByDescending { it.estimateReward() }.toTypedArray()
        GreedyBook(this).solve()

        return SolutionWrapper(problem, libraries)
    }
}