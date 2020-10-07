package homework.homework4.calculator

class StringUtilities {
    fun isOpenBracket(input: String) = input == "("

    fun isCloseBracket(input: String) = input == ")"

    fun isNumber(input: String): Boolean {
        return input.matches(Regex("-?[0-9]+"))
    }

    fun isOperator(input: String): Boolean {
        return input.matches(Regex("[-+*/]"))
    }

    fun isLessPriority(operatorA: String, operatorB: String): Boolean {
        val priorityA = if (operatorA == "/" || operatorA == "*") 1 else 0
        val priorityB = if (operatorB == "/" || operatorB == "*") 1 else 0
        return priorityA <= priorityB
    }
}
