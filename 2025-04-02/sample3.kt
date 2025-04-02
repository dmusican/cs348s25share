class Student(var name: String, var age: Int) {

    fun display() {
        println(name)
        println(age)
    }



}

fun main() {
    val s1 = Student("Dave", 29)
    val s2 = Student("Liz", 38)
}