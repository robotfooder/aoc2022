data class Grid(val cells: List<Cell>) {
    fun getLeftNeighbors(cell: Cell): List<Cell> {
        return cells.filter { it.row == cell.row && it.col < cell.col }
    }

    fun getRightNeighbors(cell: Cell): List<Cell> {
        return cells.filter { it.row == cell.row && it.col > cell.col }

    }

    fun getTopNeighbors(cell: Cell): List<Cell> {
        return cells.filter { it.col == cell.col && it.row < cell.row }

    }

    fun getBottomNeighbors(cell: Cell): List<Cell> {
        return cells.filter { it.col == cell.col && it.row > cell.row }

    }


}


data class Cell(val row: Int, val col: Int, val value: Int = 0)

fun main() {

    fun part1(input: List<String>): Int {

        val rows = input.count()
        val columns = input[0].toList().count()

        val grid = Grid(input
            .flatMapIndexed { rowIndex, row ->
                row.toList()
                    .mapIndexed { colIndex, value -> Cell(rowIndex, colIndex, value.digitToInt()) }
            })

        val edges = rows + rows + columns + columns - 4

        val visible = grid.cells
            .filter { it.col != 0 && it.row != 0 && it.col != columns - 1 && it.row != rows - 1 }
            .filter {
                with(grid) {
                    getLeftNeighbors(it).all { neighbor -> neighbor.value < it.value }
                        .or(getRightNeighbors(it).all { neighbor -> neighbor.value < it.value })
                        .or(getBottomNeighbors(it).all { neighbor -> neighbor.value < it.value })
                        .or(getTopNeighbors(it).all { neighbor -> neighbor.value < it.value })
                }
            }

        return edges + visible.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val day = "08"

// test if implementation meets criteria from the description, like:
    runTest(21, day, ::part1)
    //runTest(8, day, ::part2)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}