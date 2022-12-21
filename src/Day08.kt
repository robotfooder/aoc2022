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

    fun buildGrid(input: List<String>): Grid {

        return Grid(input
            .flatMapIndexed { rowIndex, row ->
                row.toList()
                    .mapIndexed { colIndex, value -> Cell(rowIndex, colIndex, value.digitToInt()) }
            })
    }

    fun part1(input: List<String>): Int {

        val grid = buildGrid(input)

        val rows = input.count()
        val columns = input[0].toList().count()
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


    fun score(cellValue: Int, neighbors: List<Cell>): Int {

        val smaller = neighbors.takeWhile { it.value < cellValue }

        return if (neighbors.size > smaller.size) {
            smaller.size + 1
        } else {
            smaller.size
        }


    }

    fun score(cell: Cell, grid: Grid): Int {
        val left = score(cell.value, grid.getLeftNeighbors(cell).reversed())
        val right = score(cell.value, grid.getRightNeighbors(cell))
        val top = score(cell.value, grid.getTopNeighbors(cell).reversed())
        val bottom = score(cell.value, grid.getBottomNeighbors(cell))
        return left * right * top * bottom
    }

    fun part2(input: List<String>): Int {
        val grid = buildGrid(input)

        return grid.cells.maxOfOrNull { score(it, grid) }!!
    }

    val day = "08"

// test if implementation meets criteria from the description, like:
    runTest(21, day, ::part1)
    runTest(8, day, ::part2)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}