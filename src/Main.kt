import algorithm.library.BruteForceLibrary
import algorithm.library.GreedyLibrary
import java.io.File

val inPrefix = "files/"
val outPrefix = "files/out/"

val filesIn = arrayOf("a.txt", "b.txt", "c.txt", "d.txt", "e.txt", "f.txt")
val filesOut = arrayOf("a.out", "b.out", "c.out", "d.out", "e.out", "f.out")

fun main() {
    val millis = kotlin.system.measureTimeMillis {
        filesIn.forEachIndexed { index, it ->
            val fileIn = File(inPrefix + it)
            val fileOut = File(outPrefix + filesOut[index])
            val problem = Problem(fileIn, fileOut)
            val algorithm = GreedyLibrary(problem)

            problem.writeSolution(algorithm.solve())
        }
    }

    println("Time: ${millis / 1000.0}")
}