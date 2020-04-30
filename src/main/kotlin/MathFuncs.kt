import kotlin.math.sqrt

object MathFuncs {


    fun solveSquareEquation(a: Double, b: Double, c: Double): MutableCollection<Double> {
        val res = mutableSetOf<Double>()
        val discr = b * b - 4 * a * c
        var x = (-b + sqrt(discr)) / (2 * a)
        res.add(x)
        x = (-b - sqrt(discr)) / (2 * a)
        res.add(x)
        return res
    }

    fun solveLinearEquation(k: Double, c: Double): Double {
        return -c / k
    }

    fun checkSquare(x: Double, a: Double, b: Double, c: Double): Boolean {
        return (a * x * x + b * x + c) == 0.0
    }

    fun checkLinear(x: Double, k: Double, c: Double): Boolean {
        return (k * x + c) == 0.0
    }

}