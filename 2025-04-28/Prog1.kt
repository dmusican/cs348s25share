fun checkstring(a: String?): Boolean {
    return (a == null)
    // if (1 == 1) {
    //     return true
    // } else {
    //     return false
    // }

}


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
    if (checkstring(u)){
        println("don't do that")
    } else {
        println(u.count())
    }
}
