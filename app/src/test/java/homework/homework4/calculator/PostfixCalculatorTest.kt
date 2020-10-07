package homework.homework4.calculator

import PostfixCalculator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalStateException

internal class PostfixCalculatorTest {

    @Test
    fun convert_JustNumber_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(346346.0, postfixCalculator.calculate("346346"))
    }

    @Test
    fun convert_JustNumberWithUnaryMinus_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(-62694894.0, postfixCalculator.calculate("-62694894"))
    }

    @Test
    fun convert_SimpleExpressionWithPlus_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(1.0, postfixCalculator.calculate("-2 3 +"))
    }

    @Test
    fun convert_SimpleExpressionWithMinus_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(-341.0, postfixCalculator.calculate("3 344 -"))
    }

    @Test
    fun convert_SimpleExpressionWithMultiplication_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(2585294.0, postfixCalculator.calculate("3323 778 *"))
    }

    @Test
    fun convert_SimpleExpressionWithDivision_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(47.0, postfixCalculator.calculate("235 5 /"))
    }

    @Test
    fun convert_ComplexExpression1_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(69.0, postfixCalculator.calculate("4 3 + 10 * -1 +"))
    }

    @Test
    fun convert_ComplexExpression2_MustWork() {
        val postfixCalculator = PostfixCalculator()
        assertEquals(-244.0, postfixCalculator.calculate("1245 -3 * 3 12 + / 5 +"))
    }

    @Test
    fun convert_InvalidExpression1_ExceptionThrow() {
        val postfixCalculator = PostfixCalculator()
        assertThrows(IllegalStateException::class.java) {
            postfixCalculator.calculate("skdgnjskdngj")
        }
    }

    @Test
    fun convert_InvalidExpression2_ExceptionThrow() {
        val postfixCalculator = PostfixCalculator()
        assertThrows(IllegalStateException::class.java) {
            postfixCalculator.calculate("2 3 4 +")
        }
    }
}
