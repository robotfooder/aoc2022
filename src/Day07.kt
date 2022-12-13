class MyDir(
    val name: String,
    val subDirs: MutableList<MyDir> = mutableListOf(),
    val parent: MyDir?,
    private val files: MutableList<MyFile> = mutableListOf(),
    var dirSize: Int = 0,
) {
    fun addFile(size: Int, name: String): MyDir {
        files.add(MyFile(size, name))
        dirSize += size
        var parentDir = parent
        while (parentDir != null) {
            parentDir.dirSize += size
            parentDir = parentDir.parent
        }
        return this

    }

    fun addDirectory(name: String): MyDir {
        subDirs.add(MyDir(name = name, parent = this))
        return this

    }

    fun getDirectoriesBySize(sizeTest: (Int) -> Boolean): List<MyDir> {
        return subDirs.filter { sizeTest(it.dirSize) } +
                subDirs.flatMap { it.getDirectoriesBySize(sizeTest) }
    }
}

data class MyFile(val size: Int, val name: String)

fun main() {

    fun changeDir(cwd: MyDir, dir: String): MyDir {

        return if (dir == "..") cwd.parent ?: throw IllegalStateException("No parent")
        else cwd.subDirs
            .find { it.name == dir }
            ?: if (cwd.name == dir) cwd
            else throw IllegalArgumentException("Could not find $dir in ${cwd.name}")

    }

    fun doCommand(data: List<String>, cwd: MyDir): MyDir {
        return if (data[1] == "cd") changeDir(cwd, data[2]) else cwd
    }

    fun parseFileSystem(input: List<String>): MyDir {
        val root = MyDir(name = "/", parent = null)
        var cwd = root

        input.forEach { row ->
            val data = row.split(" ")
            cwd = when (data[0]) {
                "$" -> doCommand(data, cwd)
                "dir" -> cwd.addDirectory(data[1])
                else -> cwd.addFile(data[0].toInt(), data[1])
            }

        }
        return root

    }


    fun part1(input: List<String>): Int {
        val dirLimit = 100000

        val fileSystem: MyDir = parseFileSystem(input)

        return fileSystem
            .getDirectoriesBySize { it <= dirLimit }
            .sumOf { it.dirSize }

    }

    fun part2(input: List<String>): Int {
        val totDiscSpace = 70000000
        val updateReqSpace = 30000000
        val fileSystem: MyDir = parseFileSystem(input)
        val freeSpace = totDiscSpace - fileSystem.dirSize
        val spaceNeeded = updateReqSpace - freeSpace
        return fileSystem
            .getDirectoriesBySize { it >= spaceNeeded }
            .minBy { it.dirSize }.dirSize


    }


    fun runTest(expected: Int, day: String, testFunction: (List<String>) -> Int) {
        val actual = testFunction(readTestInput(day))
        check(expected == actual)
        {
            "Failing for day $day, ${testFunction}. " +
                    "Expected $expected but got $actual"
        }
    }


    val day = "07"

// test if implementation meets criteria from the description, like:
    runTest(95437, day, ::part1)
    runTest(24933642, day, ::part2)


    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}





