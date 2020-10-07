package homework.homework4.calculator

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.Stack

class InfixToPostfixConverter {
    private val stringUtilities = StringUtilities()

    private fun operatorHandling(token: String, stack: Stack<String>): String {
        var result = ""
        while (
            stack.isNotEmpty() &&
            stringUtilities.isLessPriority(token, stack.peek()) &&
            !stringUtilities.isOpenBracket(stack.peek())
        ) {
            result += stack.pop() + " "
        }
        stack.push(token)
        return result
    }

    private fun closeBracketHandling(stack: Stack<String>): String {
        var result = ""
        while (stack.isNotEmpty() && !stringUtilities.isOpenBracket(stack.peek())) {
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
            if (stringUtilities.isOpenBracket(stack.peek())) {
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
                stringUtilities.isOperator(token) -> {
                    outputString += operatorHandling(token, stack)
                }
                stringUtilities.isCloseBracket(token) -> {
                    outputString += closeBracketHandling(stack)
                }
                stringUtilities.isOpenBracket(token) -> {
                    stack.push(token)
                }
                stringUtilities.isRealNumber(token) -> {
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
