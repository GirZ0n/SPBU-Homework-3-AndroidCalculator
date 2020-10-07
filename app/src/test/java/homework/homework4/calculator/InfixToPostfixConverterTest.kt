package homework.homework4.androidcalculator

import InfixToPostfixConverter
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException

internal class InfixToPostfixConverterTest {

    @Test
    fun convert_JustNumber_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("346346", infixConverter.convert("346346"))
    }

    @Test
    fun convert_JustNumberWithUnaryMinus_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("-62694894", infixConverter.convert("-62694894"))
    }

    @Test
    fun convert_SimpleExpressionWithPlus_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("-2 3 +", infixConverter.convert("-2 + 3"))
    }

    @Test
    fun convert_SimpleExpressionWithMinus_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("3 344 -", infixConverter.convert("3 - 344"))
    }

    @Test
    fun convert_SimpleExpressionWithMultiplication_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("3323 778 *", infixConverter.convert("3323 * 778"))
    }

    @Test
    fun convert_SimpleExpressionWithDivision_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("235 12 /", infixConverter.convert("235 / 12"))
    }

    @Test
    fun convert_ComplexExpression1_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("4 3 + 10 * -1 +", infixConverter.convert("( 4 + 3 ) * 10 + -1"))
    }

    @Test
    fun convert_ComplexExpression2_MustWork() {
        val infixConverter = InfixToPostfixConverter()
        assertEquals("4 3 + 1245 -3 * / 5 +", infixConverter.convert("( 4 + 3 ) / ( 1245 * -3 ) + 5"))
    }

    @Test
    fun convert_InvalidExpression1_ExceptionThrow() {
        val infixConverter = InfixToPostfixConverter()
        assertThrows(IllegalStateException::class.java) {
            infixConverter.convert("( ( ( ( ( ( 5 )")
        }
    }

    @Test
    fun convert_InvalidExpression2_ExceptionThrow() {
        val infixConverter = InfixToPostfixConverter()
        assertThrows(IllegalStateException::class.java) {
            infixConverter.convert("dsngjsdbgjsb")
        }
    }
}
