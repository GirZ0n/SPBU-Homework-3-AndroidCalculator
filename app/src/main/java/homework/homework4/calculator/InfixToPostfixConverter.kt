import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.Stack

class InfixToPostfixConverter {
    private fun isOpenBracket(input: String) = input == "("

    private fun isCloseBracket(input: String) = input == ")"

    private fun isLessPriority(operatorA: String, operatorB: String): Boolean {
        val priorityA = if (operatorA == "/" || operatorA == "*") 1 else 0
        val priorityB = if (operatorB == "/" || operatorB == "*") 1 else 0
        return priorityA <= priorityB
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

    private fun operatorHandling(token: String, stack: Stack<String>): String {
        var result = ""
        while (stack.isNotEmpty() && isLessPriority(token, stack.peek()) && !isOpenBracket(stack.peek())) {
            result += stack.pop() + " "
        }
        stack.push(token)
        return result
    }

    private fun closeBracketHandling(stack: Stack<String>): String {
        var result = ""
        while (stack.isNotEmpty() && !isOpenBracket(stack.peek())) {
            result += stack.pop() + " "
        }

        if (stack.isNotEmpty()) {
            stack.pop()
        } else {
            throw IllegalStateException("Missing bracket")
        }

        return result
    }

    private fun stackIsNotEmptyHandling(stack: Stack<String>): String {
        var result = ""
        while (stack.isNotEmpty()) {
            if (isOpenBracket(stack.peek())) {
                throw IllegalStateException("Missing bracket")
            }

            result += stack.pop() + ""
        }
        return result
    }

    fun convert(infixNotation: String): String {
        if (infixNotation.isEmpty()) {
            throw IllegalArgumentException("String must not be empty")
        }

        val stack = Stack<String>()
        var outputString = ""
        val tokens = infixNotation.split(" ")

        for (token in tokens) {
            when {
                isOperator(token) -> {
                    outputString += operatorHandling(token, stack)
                }
                isCloseBracket(token) -> {
                    outputString += closeBracketHandling(stack)
                }
                isOpenBracket(token) -> {
                    stack.push(token)
                }
                isNumber(token) -> {
                    outputString += "$token "
                }
                else -> {
                    throw IllegalStateException("Invalid character")
                }
            }
        }

       outputString += stackIsNotEmptyHandling(stack)

        return outputString.trim()
    }
}
