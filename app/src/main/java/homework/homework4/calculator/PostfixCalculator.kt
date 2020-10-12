package homework.homework4.calculator

import java.util.Stack

class PostfixCalculator {
    private val stringUtilities = StringUtilities()

    private fun getResultOfOperation(valueA: Double, valueB: Double, operator: String): Double {
        return when (operator) {
            "+" -> valueA + valueB
            "-" -> valueA - valueB
            "*" -> valueA * valueB
            "/" -> valueA / valueB
            else -> throw IllegalArgumentException("Unknown operator")
        }
    }

    fun calculate(input: String): Double {
        val stack = Stack<Double>()
        val tokens = input.split(" ")

        for (token in tokens) {
            when {
                stringUtilities.isOperator(token) && stack.size >= 2 -> {
                    val operandB = stack.pop()
                    val operandA = stack.pop()
                    stack.push(getResultOfOperation(operandA, operandB, token))
                }
                stringUtilities.isRealNumber(token) -> {
                    stack.push(token.toDouble())
                }
                else -> throw IllegalStateException("Incorrect input")
            }
        }

        if (stack.size > 1) {
            throw IllegalStateException("Something went wrong. The stack is not empty. Maybe you missed the operator")
        }

        return stack.pop()
    }
}
