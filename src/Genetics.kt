import kotlin.math.floor
import kotlin.random.Random

const val POPULATION_SIZE = 100
const val MAX_ITERATIONS = 1000
const val REPRODUCTION_PROBABILITY = 0.9
const val MUTATION_PROBABILITY = 0.05
const val NUM_DESCENDANTS = 2

enum class GenerationStrategy{RANDOM, GREEDY}
data class BestSolution(val iteration: Int, val individual: Individual)

class Genetics(problem: Problem) : Algorithm(problem) {

    override fun solve(): Individual {
        var population = mutableListOf<Individual>()

        println("POBLACION INICIAL")

        for(i in 0 until POPULATION_SIZE) {
            val generationStrategy = if(i < POPULATION_SIZE / 2) GenerationStrategy.RANDOM else GenerationStrategy.GREEDY
            val individual = this.generateIndividual(generationStrategy)

            population.add(individual)
            println("INDIVIDUO $i = $individual")
        }

        var bestSolution = BestSolution(1, population.first())

        for(iteration in 1..MAX_ITERATIONS) {
            println("\nITERACION: $iteration, SELECCION")
            val tournamentsWinners = this.celebrateTournaments(population)

            println("\nITERACION: $iteration, CRUCE ")
            val newGeneration = this.celebrateBreedingSeason(tournamentsWinners)

            println("ITERACION: $iteration, MUTACION")
            this.travelToTheNuclearPlant(newGeneration)

            population = population.sortedBy { it.calculateHeuristicCost() }.take(2).sortedByDescending { it.calculateHeuristicCost() }.toMutableList()
            population.addAll(newGeneration.sortedBy { it.calculateHeuristicCost() })

            println("\nITERACION: $iteration, REEMPLAZO")
            population.forEachIndexed { index, individual -> println("INDIVIDUO $index = $individual")}

            bestSolution = minOf(bestSolution, BestSolution(iteration, population.filter { it.isValid() }.minBy { it.calculateHeuristicCost() }!!), compareBy { it.individual.calculateHeuristicCost() })
        }

        return bestSolution.individual
    }

    private fun generateIndividual(generationStrategy: GenerationStrategy): Individual {
        val solution = Array(problem.size) {false}

        when(generationStrategy) {
            GenerationStrategy.GREEDY -> {
                return Greedy(problem).solve()
            }

            GenerationStrategy.RANDOM -> {
                val individual = Individual(problem, Array(problem.size) {false})
                individual.randomize()

                return individual
            }
        }
    }

    private fun celebrateTournaments(population: List<Individual>): List<Individual> {
        val winners = mutableListOf<Individual>()

        for(tournament in 0 until population.size - 2) {
            val participant1 = floor(Random.nextDouble() * population.size).toInt()
            val participant2 = floor(Random.nextDouble() * population.size).toInt()
            val winner = minOf(participant1, participant2, compareBy { population[it].calculateHeuristicCost() })

            winners.add(Individual(problem, population[winner].solution.copyOf()))
            println("\tTORNEO $tournament: $participant1 $participant2 GANA $winner")
        }

        return winners
    }

    private fun celebrateBreedingSeason(population: List<Individual>): List<Individual> {
        val newGeneration = mutableListOf<Individual>()

        for(i in population.indices step 2) {
            val random = Random.nextDouble()
            val parent1 = population[i]
            val parent2 = population[i + 1]

            println("\tCRUCE: ($i, ${i + 1})")
            println("\t\tPADRE: = $parent1")
            println("\t\tPADRE: = $parent2")

            if (random < REPRODUCTION_PROBABILITY) {
                newGeneration.addAll(generateDescendants(parent1, parent2))
            } else {
                println("\t\tNO SE CRUZA\n")

                newGeneration.add(parent1)
                newGeneration.add(parent2)
            }
        }

        return newGeneration
    }

    private fun generateDescendants(parent1: Individual, parent2: Individual): List<Individual> {
        val result = mutableListOf<Individual>()

        for(unused in 0 until NUM_DESCENDANTS) {
            val individual = Individual(problem, Array(problem.size) {false})

            for(j in individual.solution.indices) {
                if(parent1.solution[j] == parent2.solution[j]) {
                    individual.solution[j] = parent1.solution[j]
                }
                else {
                    individual.solution[j] = Random.nextBoolean()
                }
            }

            result.add(individual)
        }

        return result
    }

    private fun travelToTheNuclearPlant(population: List<Individual>) {
        population.forEachIndexed { index, individual ->
            for(i in 1 until problem.size) {
                if(!individual.solution[i - 1] && individual.solution[i]) {
                    if(Random.nextDouble() < MUTATION_PROBABILITY) {
                        individual.solution[i - 1] = true
                        individual.solution[i] = false
                    }
                }
            }
        }
    }
}