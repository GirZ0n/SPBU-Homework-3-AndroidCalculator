package homework.homework4.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expressionField = findViewById<TextView>(R.id.expression)
        equalButtonInit(expressionField)
        clearButtonInit(expressionField)
        deleteButtonInit(expressionField)
        bracketButtonsInit(expressionField)
        operatorButtonsInit(expressionField)
        numberButtonsInit(expressionField)
    }

    private fun equalButtonInit(expressionField: TextView) {
        val infixToPostfixConverter = InfixToPostfixConverter()
        val postfixCalculator = PostfixCalculator()
        val equalButton = findViewById<Button>(R.id.equal_button)
        equalButton.setOnClickListener {
            val expression = expressionField.text.toString().trim()
            val answer = try {
                val postfix = infixToPostfixConverter.convert(expression)
                postfixCalculator.calculate(postfix).toString()
            } catch (exception: IllegalStateException) {
                // Произошла проблема при конвертации / вычислении
                "ERROR"
            } catch (exception: IllegalArgumentException) {
                // На вход подана пустая строка
                ""
            }
            expressionField.text = answer
        }
    }

    private fun clearButtonInit(expressionField: TextView) {
        val clearButton = findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener {
            expressionField.text = ""
        }
    }

    @SuppressLint("SetTextI18n")
    private fun deleteButtonInit(expressionField: TextView) {
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val stringUtilities = StringUtilities()
        deleteButton.setOnClickListener {
            val expression = expressionField.text.trim().toString()
            val lastToken = getLastToken(expression)
            val newExpression = if (stringUtilities.isNumber(lastToken)) {
                // Удаляем последнюю цифру
                expression.substring(0 until expression.lastIndex)
            } else {
                // Убираем последний токен
                expression.substringBeforeLast(getLastToken(expression))
            }

            // Если нужно, то добавляем пробел в конце (для отделения нового токена от последнего)
            expressionField.text = if (!newExpression.isBlank()) newExpression else ""
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bracketButtonsInit(expressionField: TextView) {
        val bracketButtons = arrayOf(R.id.open_bracket_button, R.id.close_bracket_button).map {
            findViewById<Button>(it)
        }
        for (button in bracketButtons) {
            button.setOnClickListener {
                // Если в выражении есть ошибка, то перед добавлением новой строки очищаем его
                if (expressionField.text.contains("ERROR")) {
                    expressionField.text = ""
                }
                // Добавляем скобку в конец выражения и пробел для отделения нового токена от последнего
                expressionField.text = expressionField.text.toString() + button.text + " "
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun operatorButtonsInit(expressionField: TextView) {
        val operatorButtons = arrayOf(
            R.id.plus_button, R.id.minus_button,
            R.id.multiplication_button, R.id.division_button
        ).map { findViewById<Button>(it) }
        for (button in operatorButtons) {
            button.setOnClickListener {
                // Если в выражении есть ошибка, то перед добавлением новой строки очищаем его
                if (expressionField.text.contains("ERROR")) {
                    expressionField.text = ""
                }
                // Добавляем оператор в конец выражения и пробел для отделения нового токена от последнего
                expressionField.text = expressionField.text.toString() + button.text + " "
            }
        }
    }

    private fun numberButtonsInit(expressionField: TextView) {
        val numberButtons = arrayOf(
            R.id.zero_button, R.id.one_button, R.id.two_button,
            R.id.three_button, R.id.four_button, R.id.five_button,
            R.id.six_button, R.id.seven_button, R.id.eight_button,
            R.id.nine_button
        ).map { findViewById<Button>(it) }

        for (button in numberButtons) {
            val buttonText = button.text.toString()
            button.setOnClickListener {
                val expression = expressionField.text.toString()
                val lastToken = getLastToken(expression)
                // Делаем так, чтобы циферка приписалась красиво
                val newExpression: String = when {
                    // Если в выражении есть ошибка или оно пустое,
                    // то просто число и пробел для отделения нового токена от последнего
                    expression.contains("ERROR") || expression.isBlank() -> {
                        "$buttonText "
                    }
                    // Если последний символ последнего токена является цифрой,
                    // то новую цифру следует приписать к предыдущей.
                    // Для этого убираем отделительный пробел,
                    // приписываем цифру и снова приписываем пробел
                    lastToken.last().isDigit() -> {
                        expression.trim() + buttonText + " "
                    }
                    // Если последний токен это минус, то нужно проверить, является ли он унитарным.
                    // Если да, то число нужно приписать к минусу.
                    // Для этого убираем отделительный пробел,
                    // приписываем цифру и снова приписываем пробел
                    lastToken.last() == '-' && isUnaryNegative(expression) -> {
                        expression.trim() + buttonText + " "
                    }
                    // Во всех остальных случаях добавляем нашу цифру как отдельный токен
                    else -> {
                        "$expression$buttonText "
                    }
                }
                expressionField.text = newExpression
            }
        }
    }

    private fun getLastToken(expression: String) = expression.trim().substringAfterLast(" ")

    private fun isUnaryNegative(expression: String): Boolean {
        val expressionWithoutLastToken = expression.substringBeforeLast(getLastToken(expression))
        val penultimateToken = getLastToken(expressionWithoutLastToken)
        val stringUtilities = StringUtilities()
        return penultimateToken.isBlank() || stringUtilities.isOperator(penultimateToken)
    }
}