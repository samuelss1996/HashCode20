package algorithm.library

import Library
import Problem
import SolutionWrapper

abstract class LibraryAlgorithm(val problem: Problem) {
    lateinit var libraries: Array<Library>

    abstract fun solve() : SolutionWrapper
}