import algorithm.library.Genetics
import java.io.File

val inPrefix = "files/"
val outPrefix = "files/out/"

val filesIn = arrayOf("a_example.in", "b_small.in", "c_medium.in", "d_quite_big.in", "e_also_big.in")
val filesOut = arrayOf("a_example.out", "b_small.out", "c_medium.out", "d_quite_big.out", "e_also_big.out")

fun main() {
    val millis = kotlin.system.measureTimeMillis {
        filesIn.forEachIndexed { index, it ->
            val fileIn = File(inPrefix + it)
            val fileOut = File(outPrefix + filesOut[index])
            val problem = Problem(fileIn, fileOut)
            val algorithm = Genetics(problem)

            problem.writeSolution(algorithm.solve())
        }
    }

    println("Time: ${millis / 1000.0}")
}