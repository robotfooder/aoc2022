class MyDir(
    val name: String,
    val subDirs: MutableList<MyDir> = mutableListOf(),
    val parent: MyDir?,
    val files: MutableList<MyFile> = mutableListOf(),
    var dirSize: Int = 0,
) {
    fun addFile(myFile: MyFile) {
        files.add(myFile)
        dirSize += myFile.size
        var parentDir = parent
        while (parentDir != null) {
            parentDir.dirSize += myFile.size
            parentDir = parentDir.parent
        }

    }
}

data class MyFile(val size: Int, val name: String)

fun main() {


    fun parseFileSystem(input: List<String>): MyDir {
        val fileSystem = MyDir(name = "/", parent = null)

        var currentDir = fileSystem
        for (row in input) {
            val data = row.split(" ")
            if (data[0] == "$") {
                val command = data[1]
                if (command == "cd") {
                    val dir = data[2]
                    currentDir = if (dir == "..") {
                        currentDir.parent ?: throw IllegalStateException("No parent")
                    } else {
                        currentDir.subDirs
                            .find { it.name == dir }
                            ?: if (currentDir.name == dir) currentDir else throw IllegalArgumentException("Could not find $dir")
                    }
                }
            } else if (data[0] == "dir") {
                currentDir.subDirs.add(MyDir(name = data[1], parent = currentDir))
            } else {
                currentDir.addFile(MyFile(data[0].toInt(), data[1]))
            }
        }
        return fileSystem

    }

    fun getSmallDirectories(subDirs: MutableList<MyDir>): List<MyDir> =
        subDirs.filter { it.dirSize <= 100000 } +
                subDirs.flatMap { getSmallDirectories(it.subDirs) }


    fun part1(input: List<String>): Int {
        val fileSystem: MyDir = parseFileSystem(input)
        val smallDirs = getSmallDirectories(fileSystem.subDirs)
        return smallDirs.sumOf { it.dirSize }

    }

    fun part2(input: List<String>): Int {
        return input.size

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
    //runTest(26, day, ::part2)


    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}





