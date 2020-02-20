class Greedy(problem: Problem) : Algorithm(problem) {
    private val individual = SolutionWrapper(problem, Array(problem.size){false})

    override fun solve() : SolutionWrapper {
        for(i in (0 until problem.size).reversed()) {
            individual.setValue(i, true)

            if(!individual.isValid()) {
                individual.setValue(i, false)
            }
        }

        return individual
    }
}