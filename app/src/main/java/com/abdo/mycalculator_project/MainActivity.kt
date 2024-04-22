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

        // Initialize TextViews using View Binding (assuming you have a layout file named activity_main.xml)
        calculationTextView = findViewById(R.id.calculation)
        resultTextView = findViewById(R.id.result)
    }

    // These functions should not be @Composable
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
        calculationTextView.text = " "
    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators(calculationTextView.text.toString())
        if (digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in 1 until passedList.size step 2) {
            val operator = passedList[i] as Char
            val nextDigit = passedList[i + 1] as Float
            when (operator) {
                '+' -> result += nextDigit
                '-' -> result -= nextDigit
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        val list = mutableListOf<Any>()
        var tempResult = 0.0f
        var operator = '+'

        for (item in passedList) {
            if (item is Char && (item == '×' || item == '÷')) {
                operator = item
            } else if (item is Float) {
                when (operator) {
                    '+' -> tempResult += item
                    '-' -> tempResult -= item
                    '×' -> tempResult *= item
                    '÷' -> tempResult /= item
                }
            }
        }

        list.add(tempResult)
        return list
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
