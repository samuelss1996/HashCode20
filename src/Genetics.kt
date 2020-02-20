import kotlin.math.ceil
import kotlin.math.floor
import kotlin.random.Random

const val POPULATION_SIZE = 1000
const val MAX_ITERATIONS = 1000
const val REPRODUCTION_PROBABILITY = 0.9
const val NUM_DESCENDANTS = 2

val MUTATION_PROBABILITIES = arrayOf(0.05, 0.1, 0.25, 0.5, 0.75, 0.85)
var MUTATION_PROBABILITY = 0.5

enum class GenerationStrategy{RANDOM, GREEDY}
data class BestSolution(val iteration: Int, val solutionWrapper: SolutionWrapper)

class Genetics(problem: Problem) : Algorithm(problem) {

    override fun solve(): SolutionWrapper {
        var population = mutableListOf<SolutionWrapper>()

        for(i in 0 until POPULATION_SIZE) {
            val generationStrategy = if(i < POPULATION_SIZE / 2) GenerationStrategy.RANDOM else GenerationStrategy.GREEDY
            val individual = this.generateIndividual(generationStrategy)

            population.add(individual)
        }

        var bestSolution = BestSolution(1, population.first())

        MUTATION_PROBABILITIES.forEachIndexed { mutIndex, mut ->
            MUTATION_PROBABILITY = mut

            for(iteration in 1..MAX_ITERATIONS) {
                if((iteration - 1) % 10 == 0) {
                    val percent = 100.0 * mutIndex / MUTATION_PROBABILITIES.size + (100.0 / MUTATION_PROBABILITIES.size * iteration / MAX_ITERATIONS)
                    print("${ceil(percent).toInt()} %\r")
                }

                val tournamentsWinners = this.celebrateTournaments(population)
                val newGeneration = this.celebrateBreedingSeason(tournamentsWinners)
                this.travelToTheNuclearPlant(newGeneration)

                population = population.sortedBy { it.calculateHeuristicCost() }.take(2).sortedByDescending { it.calculateHeuristicCost() }.toMutableList()
                population.addAll(newGeneration.sortedBy { it.calculateHeuristicCost() })

                bestSolution = minOf(bestSolution, BestSolution(iteration, population.filter { it.isValid() }.minBy { it.calculateHeuristicCost() }!!), compareBy { it.solutionWrapper.calculateHeuristicCost() })
            }

        }

        println()
        return bestSolution.solutionWrapper
    }

    private fun generateIndividual(generationStrategy: GenerationStrategy): SolutionWrapper {
        when(generationStrategy) {
            GenerationStrategy.GREEDY -> {
                return Greedy(problem).solve()
            }

            GenerationStrategy.RANDOM -> {
                val individual = SolutionWrapper(problem, Array(problem.size) {false})
                individual.randomize()

                return individual
            }
        }
    }

    private fun celebrateTournaments(population: List<SolutionWrapper>): List<SolutionWrapper> {
        val winners = mutableListOf<SolutionWrapper>()

        for(tournament in 0 until population.size - 2) {
            val participant1 = floor(Random.nextDouble() * population.size).toInt()
            val participant2 = floor(Random.nextDouble() * population.size).toInt()
            val winner = minOf(participant1, participant2, compareBy { population[it].calculateHeuristicCost() })

            winners.add(population[winner].copyOf())
        }

        return winners
    }

    private fun celebrateBreedingSeason(population: List<SolutionWrapper>): List<SolutionWrapper> {
        val newGeneration = mutableListOf<SolutionWrapper>()

        for(i in population.indices step 2) {
            val random = Random.nextDouble()
            val parent1 = population[i]
            val parent2 = population[i + 1]

            if (random < REPRODUCTION_PROBABILITY) {
                newGeneration.addAll(generateDescendants(parent1, parent2))
            } else {
                newGeneration.add(parent1)
                newGeneration.add(parent2)
            }
        }

        return newGeneration
    }

    private fun generateDescendants(parent1: SolutionWrapper, parent2: SolutionWrapper): List<SolutionWrapper> {
        val result = mutableListOf<SolutionWrapper>()

        for(unused in 0 until NUM_DESCENDANTS) {
            val individual = SolutionWrapper(problem, Array(problem.size) {false})

            for(j in individual.solution.indices) {
                if(parent1.solution[j] == parent2.solution[j]) {
                    individual.setValue(j, parent1.solution[j])
                }
                else {
                    individual.setValue(j, Random.nextBoolean())
                }
            }

            result.add(individual)
        }

        return result
    }

    private fun travelToTheNuclearPlant(population: List<SolutionWrapper>) {
        population.forEachIndexed { index, individual ->
            for(i in 1 until problem.size) {
                if(!individual.solution[i - 1] && individual.solution[i]) {
                    if(Random.nextDouble() < MUTATION_PROBABILITY) {
                        individual.setValue(i - 1, true)
                        individual.setValue(i, false)
                    }
                }
            }
        }
    }
}