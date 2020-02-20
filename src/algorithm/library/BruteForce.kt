package algorithm.library

import Library
import Problem
import SolutionWrapper
import algorithm.book.GreedyBookAlgorithm

class BruteForce(problem: Problem) : LibraryAlgorithm(problem) {
    private var optimal: SolutionWrapper? = null

    override fun solve(): SolutionWrapper {
        libraries = problem.libraries.clone()
        val indexes = Array(libraries.size) {0}

        var i = 0
        while (i < libraries.size) {
            if (indexes[i] < i) {
                swap(libraries, if (i % 2 == 0) 0 else indexes[i], i)

                val solution = solveWithCurrentOrder(libraries)
                if(solution.isBetterThan(optimal)) {
                    optimal = solution
                }

                indexes[i]++
                i = 0
            } else {
                indexes[i] = 0
                i++
            }
        }

        return optimal!!
    }

    private fun solveWithCurrentOrder(libraries: Array<Library>): SolutionWrapper {
        GreedyBookAlgorithm(this).solve()
        return SolutionWrapper(problem, libraries)
    }

    private fun swap(array: Array<Library>, a: Int, b: Int) {
        val temp = array[a]
        array[a] = array[b]
        array[b] = temp
    }
}