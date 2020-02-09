import java.io.File
import java.util.*

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