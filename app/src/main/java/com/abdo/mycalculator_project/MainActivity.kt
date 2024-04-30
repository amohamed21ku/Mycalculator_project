package com.abdo.mycalculator_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var calculationTextView: TextView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculationTextView = findViewById(R.id.calculation)
        resultTextView = findViewById(R.id.result)
    }

    fun allClearAction(view: View) {
        calculationTextView.text = ""
        resultTextView.text = ""
    }

    fun backSpaceAction(view: View) {
        val currentText = calculationTextView.text.toString()
        if (currentText.isNotEmpty()) {
            calculationTextView.text = currentText.substring(0, currentText.length - 1)
        }
    }

    fun numberClicked(view: View) {
        val clickedNumber = (view as TextView).text
        calculationTextView.append(clickedNumber)
    }

    fun addAction(view: View) {
        calculationTextView.append("+")
    }

    fun subtractAction(view: View) {
        calculationTextView.append("-")
    }

    fun multiplyAction(view: View) {
        calculationTextView.append("×")
    }

    fun divideAction(view: View) {
        calculationTextView.append("÷")
    }

    fun equalsAction(view: View) {
        resultTextView.text = calculateResults()
        calculationTextView.text = ""
    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators(calculationTextView.text.toString())
        if (digitsOperators.isEmpty()) return ""

        val result = performOperations(digitsOperators)
        return result.toString()
    }

    private fun performOperations(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in 1 until passedList.size step 2) {
            val operator = passedList[i] as Char
            val nextDigit = passedList[i + 1] as Float

            when (operator) {
                '+' -> result += nextDigit
                '-' -> result -= nextDigit
                '×' -> result *= nextDigit
                '÷' -> result /= nextDigit
            }
        }

        return result
    }

    private fun digitsOperators(expression: String): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in expression) {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else {
                if (currentDigit.isNotEmpty()) {
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                }
                if (character != ' ') {
                    list.add(character)
                }
            }
        }

        if (currentDigit.isNotEmpty()) {
            list.add(currentDigit.toFloat())
        }

        return list
    }
}
