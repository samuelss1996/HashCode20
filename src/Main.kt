import java.io.File
import java.util.*
import kotlin.math.pow

val inPrefix = "files/"
val outPrefix = "files/out/"
val files = arrayOf("a_example.in", "b_small.in", "c_medium.in", "d_quite_big.in", "e_also_big.in")
val indexOffset = 0

fun main() {
    files.forEachIndexed { index, it ->
        val slices = PizzaSlices(File(inPrefix + it), File("$outPrefix${index+indexOffset}.out"))

        slices.greedy()
        slices.writeSolution()
    }
}

class PizzaSlices(inFile: File, val outFile: File) {
    val solutionFilter: Array<Boolean>
    val values: Array<Int>
    val target: Int
    val size: Int

    var optimalFilter: Array<Boolean>
    var optimalValue: Int = 0

    init {
        val scanner = Scanner(inFile)

        target = scanner.nextInt()
        size = scanner.nextInt()

        solutionFilter = Array(size) {false}
        values = Array(size) {0}

        optimalFilter = solutionFilter.copyOf()

        (0 until size).forEach {
            values[it] = scanner.nextInt()
        }
    }

    fun bruteForce() {
        val max = 2.0.pow(size).toLong()

        for(i in 0 until max) {
            if(i % 50000000 == 0.toLong()) {
                println("${100.0 * i / max}%")
                println("Size: $size, Target: $target")
                println("Values: ${values.joinToString()}")
                println("Optimal filter: ${optimalFilter.joinToString()}")
                println("Optimal value: ${optimalValue}")
            }

            if(i > 0) nextSolution()
            checkOptimality()
        }
    }

    fun greedy() {
        for(i in (0 until size).reversed()) {
            solutionFilter[i] = true

            if(!checkOptimality()) {
                solutionFilter[i] = false
            }
        }
    }

    fun writeSolution() {
        outFile.printWriter().use { writer ->
            writer.println(optimalFilter.count { it })

            for (i in 0 until size) {
                if (optimalFilter[i]) {
                    writer.print(i)
                    writer.print(" ")
                }
            }
        }
    }

    private fun nextSolution() {
        swapFilter(0)
    }

    private fun swapFilter(position: Int) {
        if(solutionFilter[position]) {
            solutionFilter[position] = false
            swapFilter(position + 1)
        }
        else {
            solutionFilter[position] = true
        }
    }

    private fun checkOptimality(): Boolean {
        val newValue = calculateValue()

        if(newValue in (optimalValue + 1)..target) {
            optimalValue = newValue
            optimalFilter = solutionFilter.copyOf()

            return true
        }

        return false
    }

    private fun calculateValue(): Int {
        var value = 0

        for(i in 0 until size) {
            value += if(solutionFilter[i]) values[i] else 0
        }

        return value
    }
}