import MathFuncs.checkLinear
import MathFuncs.checkSquare
import MathFuncs.solveLinearEquation
import MathFuncs.solveSquareEquation
import annotations.NTest
import annotations.NTestMethod

@NTest
class TestClass {


    @NTestMethod
    fun testSolveSquareEquation(): Boolean {
        val coeffs = CoeffsInSquareEquation(1.0, -6.0, -7.0)
        val ans = solveSquareEquation(coeffs.a, coeffs.b, coeffs.c)
        return assertSquareSolutions(ans, coeffs)
    }

    private fun assertSquareSolutions(ans: MutableCollection<Double>, coeffs: CoeffsInSquareEquation): Boolean {
        var assertion = true
        ans.forEach {
            if (!checkSquare(it, coeffs.a, coeffs.b, coeffs.c)) {
                assertion = false
            }
        }
        return assertion
    }

    private fun assertLinearSolutions(ans: Double, coeffs: CoeffsInLinearEquation): Boolean {
        return checkLinear(ans, coeffs.k, coeffs.c)
    }

    @NTestMethod
    fun testSolveLinearEquation(): Boolean {
        val coeffs = CoeffsInLinearEquation(1.0, 2.0)
        val ans = solveLinearEquation(coeffs.k, coeffs.c)
        return assertLinearSolutions(ans, coeffs)
    }


    fun notAnnotatedMethodHere(): String {  //Метод для проверки аннотаций
        return "I'm here"
    }

    class CoeffsInSquareEquation(
            val a: Double,
            val b: Double,
            val c: Double
    )

    class CoeffsInLinearEquation(
            val k: Double,
            val c: Double
    )


}