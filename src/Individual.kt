import java.util.*
import kotlin.math.abs

class Individual(val problem: Problem, val solution: Array<Boolean>) {
    private val random = Random()
    private var value = 0
    private var upToDate = false

    fun copyOf(): Individual {
        return Individual(problem, solution.copyOf())
    }

    fun isWorseThan(individual: Individual): Boolean {
        return individual.calculateValue() in (calculateValue() + 1)..problem.target
    }

    fun isValid(): Boolean {
        return calculateValue() <= problem.target
    }

    fun setValue(index: Int, value: Boolean) {
        solution[index] = value
        upToDate = false
    }

    fun randomize() {
        for(i in solution.indices) {
            solution[i] = random.nextBoolean()
        }

        upToDate = false
    }

    fun calculateValue(): Int {
        if(!upToDate) {
            value = 0

            for(i in solution.indices) {
                value += if(solution[i]) problem.values[i] else 0
            }

            upToDate = true
        }

        return value
    }

    fun calculateHeuristicCost(): Int {
        return abs(calculateValue() - problem.target)
    }

    fun countTrues(): Int {
        return solution.count { it }
    }
}