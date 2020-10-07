package homework.homework4.calculator

class StringUtilities {
    fun isOpenBracket(input: String) = input == "("

    fun isCloseBracket(input: String) = input == ")"

    fun isNumber(input: String): Boolean {
        if (input.isEmpty()) {
            return false
        }
        val stringForCheck = if (input.first() == '-') input.substring(1) else input
        return stringForCheck.isNotEmpty() && stringForCheck.none { !it.isDigit() }
    }

    fun isOperator(input: String): Boolean {
        return input == "+" || input == "-" || input == "*" || input == "/"
    }

    fun isLessPriority(operatorA: String, operatorB: String): Boolean {
        val priorityA = if (operatorA == "/" || operatorA == "*") 1 else 0
        val priorityB = if (operatorB == "/" || operatorB == "*") 1 else 0
        return priorityA <= priorityB
    }
}
