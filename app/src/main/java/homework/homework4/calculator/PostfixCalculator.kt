import java.util.Stack

class PostfixCalculator {
    private fun getResultOfOperation(valueA: Double, valueB: Double, operator: String): Double {
        return when (operator) {
            "+" -> valueA + valueB
            "-" -> valueA - valueB
            "*" -> valueA * valueB
            "/" -> valueA / valueB
            else -> throw IllegalArgumentException("Unknown operator")
        }
    }

    private fun isOperator(input: String): Boolean {
        return input == "+" || input == "-" || input == "*" || input == "/"
    }

    private fun isNumber(input: String): Boolean {
        if (input.isEmpty()) {
            return false
        }
        val stringForCheck = if (input.first() == '-') input.substring(1) else input
        return stringForCheck.isNotEmpty() && stringForCheck.none { !it.isDigit() }
    }

    fun calculate(input: String): Double {
        val stack = Stack<Double>()
        val tokens = input.split(" ")

        for (token in tokens) {
            when {
                isOperator(token) && stack.size >= 2 -> {
                    val operandB = stack.pop()
                    val operandA = stack.pop()
                    stack.push(getResultOfOperation(operandA, operandB, token))
                }
                isNumber(token) -> {
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
