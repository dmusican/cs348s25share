fun addOneAndPrintName(number: Int, name: String) {
    println(number+1)
    println(name)
}  

fun main() {
    // by default, lists are immutable, can't be changed, so add doesn't work
    val things = listOf(12, 19, 27)
    println(things.count())
    for (item in things) {
        println(item)
    }

    val morethings = mutableListOf(21, 29)
    morethings.add(42)
    println(morethings)

    addOneAndPrintName(3, "dave")
}