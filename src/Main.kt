import java.io.File
import java.util.*
import kotlin.math.abs

val inPrefix = "files/"
val outPrefix = "files/out/"
val files = arrayOf("a_example.in", "b_small.in", "c_medium.in", "d_quite_big.in", "e_also_big.in")
val indexOffset = 0


fun main() {
    val millis = kotlin.system.measureTimeMillis {
        files.forEachIndexed { index, it ->
            val problem = Problem(File(inPrefix + it), File("$outPrefix${index+indexOffset}.out"))
            val algorithm = Genetics(problem)

            problem.writeSolution(algorithm.solve())
        }
    }

    println("Time: ${millis / 1000.0}")
}


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


class Problem(inFile: File, val outFile: File) {
    val values: Array<Int>
    val target: Int
    val size: Int

    init {
        val scanner = Scanner(inFile)

        target = scanner.nextInt()
        size = scanner.nextInt()
        values = Array(size) {0}

        (0 until size).forEach {
            values[it] = scanner.nextInt()
        }
    }

    fun writeSolution(individual: Individual) {
        outFile.printWriter().use { writer ->
            writer.println(individual.countTrues())

            for (i in 0 until size) {
                if (individual.solution[i]) {
                    writer.print(i)
                    writer.print(" ")
                }
            }
        }
    }
}
