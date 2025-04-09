data class Student(val name: String,
                   val age: Int,
                   val id: Int,
                   val residence: String)

fun main() {
    val dave = Student("Dave", 15, 1234567, "here")
    println(dave)
    println(dave.age)
    println(dave.id)
}
