import kotlin.math.pow

class BruteForce(problem: Problem) : Algorithm(problem) {
    private var current = SolutionWrapper(problem, Array(problem.size) { false })
    private var optimal = current

    override fun solve() : SolutionWrapper {
        val max = 2.0.pow(problem.size).toLong()

        for(i in 0 until max) {
            if(i % 50000000 == 0.toLong()) {
                println("${100.0 * i / max}%")
                println("Size: ${problem.size}, Target: ${problem.target}")
                println("Values: ${problem.values.joinToString()}")
                println("Optimal filter: ${current.solution.joinToString()}")
                println("Optimal value: ${current.calculateValue()}")
            }

            if(i > 0) nextSolution()

            if(optimal.isWorseThan(current)) {
                optimal = current.copyOf()
            }
        }

        return optimal
    }

    private fun nextSolution() {
        swapFilter(0)
    }

    private fun swapFilter(position: Int) {
        if(current.solution[position]) {
            current.setValue(position, false)
            swapFilter(position + 1)
        }
        else {
            current.setValue(position, true)
        }
    }
}