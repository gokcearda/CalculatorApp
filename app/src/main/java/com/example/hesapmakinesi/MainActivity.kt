package com.example.hesapmakinesi

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var currentInput = ""
    private var firstOperand = ""
    private var secondOperand = ""
    private var operator = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display: TextView = findViewById(R.id.autoCompleteTextView)

        // Button Click Handlers
        val buttonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                appendToInput((it as Button).text.toString(), display)
            }
        }

        findViewById<Button>(R.id.buttonPlus).setOnClickListener { setOperator("+", display) }
        findViewById<Button>(R.id.buttonMinus).setOnClickListener { setOperator("-", display) }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { setOperator("*", display) }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { setOperator("/", display) }

        findViewById<Button>(R.id.buttonEqual).setOnClickListener { calculateResult(display) }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clear(display) }
    }

    private fun appendToInput(value: String, display: TextView) {
        if (operator.isEmpty()) {
            firstOperand += value
        } else {
            secondOperand += value
        }
        currentInput += value
        display.text = currentInput
    }

    private fun setOperator(op: String, display: TextView) {
        if (firstOperand.isNotEmpty() && secondOperand.isEmpty()) {
            operator = op
            currentInput += " $op "
            display.text = currentInput
        }
    }

    private fun calculateResult(display: TextView) {
        if (firstOperand.isNotEmpty() && secondOperand.isNotEmpty() && operator.isNotEmpty()) {
            val num1 = firstOperand.toDoubleOrNull()
            val num2 = secondOperand.toDoubleOrNull()

            if (num1 != null && num2 != null) {
                result = when (operator) {
                    "+" -> (num1 + num2).toString()
                    "-" -> (num1 - num2).toString()
                    "*" -> (num1 * num2).toString()
                    "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Hata"
                    else -> "Hata"
                }
                display.text = result
                reset()
                firstOperand = result // Sonucu bir sonraki işlem için kullan
            } else {
                display.text = "Geçersiz Giriş"
            }
        }
    }

    private fun clear(display: TextView) {
        reset()
        display.text = "0"
    }

    private fun reset() {
        currentInput = ""
        firstOperand = ""
        secondOperand = ""
        operator = ""
        result = ""
    }
}
