import algorithm.library.BruteForce
import algorithm.library.Genetics
import java.io.File

val inPrefix = "files/"
val outPrefix = "files/out/"

val filesIn = arrayOf("a_example.txt")
val filesOut = arrayOf("a_example.out")

fun main() {
    val millis = kotlin.system.measureTimeMillis {
        filesIn.forEachIndexed { index, it ->
            val fileIn = File(inPrefix + it)
            val fileOut = File(outPrefix + filesOut[index])
            val problem = Problem(fileIn, fileOut)
            val algorithm = BruteForce(problem)

            problem.writeSolution(algorithm.solve())
        }
    }

    println("Time: ${millis / 1000.0}")
}