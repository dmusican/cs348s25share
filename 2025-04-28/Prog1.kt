fun doit1() {
    var s: String = "hello"
    println(s.count())

    var t: String? = "bye"
    if (t == null) {
        println("don't do that")
    } else {
        println(t.count())
    }

    var u: String? = "bye"
    if (u == null) {
        println("don't do that")
    } else {
        println(u.count())
    }
}
