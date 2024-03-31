package com.example.project2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project2.databinding.FragmentKeyboardBinding
import java.text.DecimalFormat
import kotlin.ClassCastException
import kotlin.math.sqrt

class KeyboardFragment : Fragment() {

    enum class Symbols {ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, MODULUS, INITIAL, SQUAREROOT}
    private var currentOperator : Symbols = Symbols.INITIAL
    private lateinit var binding: FragmentKeyboardBinding
    private var firstOperand : Double = Double.NaN
    private var secondOperand : Double = Double.NaN
    private var inputState : StringBuilder = StringBuilder()
    private var result : Double = Double.NaN
    private var sqrRoot: Boolean = false
    private lateinit var activityCallback: DataListener

    interface DataListener {
        fun onOperandChange(operand: String)

        fun onResult(result: Double)

        fun onClear()

        fun onClearLast()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            activityCallback = context as DataListener
        }
        catch (e : ClassCastException) {
            throw ClassCastException("$context must implement DataListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKeyboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.zero.setOnClickListener { activityCallback.onOperandChange("0")
            inputState.append("0")
        }
        binding.one.setOnClickListener {
            activityCallback.onOperandChange("1")
            inputState.append("1")
        }
        binding.two.setOnClickListener {
            activityCallback.onOperandChange("2")
            inputState.append("2")
        }
        binding.three.setOnClickListener {
            activityCallback.onOperandChange("3")
            inputState.append("3")
        }
        binding.four.setOnClickListener {
            activityCallback.onOperandChange("4")
            inputState.append("4")
        }
        binding.five.setOnClickListener {
            activityCallback.onOperandChange("5")
            inputState.append("5")
        }
        binding.six.setOnClickListener {
            activityCallback.onOperandChange("6")
            inputState.append("6")
        }
        binding.seven.setOnClickListener {
            activityCallback.onOperandChange("7")
            inputState.append("7")
        }
        binding.eight.setOnClickListener {
            activityCallback.onOperandChange("8")
            inputState.append("8")
        }
        binding.nine.setOnClickListener {
            activityCallback.onOperandChange("9")
            inputState.append("9")
        }
        binding.dot.setOnClickListener {
            if(inputState.isEmpty()) {
                activityCallback.onOperandChange("0.")
                inputState.append("0.")
            }
            else {
                activityCallback.onOperandChange(".")
                inputState.append('.')
            }
        }

        binding.plusBtn.setOnClickListener {
            if(checkInput()) {
                updateOperands()
                activityCallback.onOperandChange("+")
                currentOperator = Symbols.ADDITION
            }
        }

        binding.minusBtn.setOnClickListener {
            if(checkInput()) {
                updateOperands()
                activityCallback.onOperandChange("-")
                currentOperator = Symbols.SUBTRACTION
            }
        }

        binding.multiplicationBtn.setOnClickListener {
            if(checkInput()) {
                updateOperands()
                activityCallback.onOperandChange("x")
                currentOperator = Symbols.MULTIPLICATION
            }
        }
        binding.divisionBtn.setOnClickListener {
            if(checkInput()) {
                updateOperands()
                activityCallback.onOperandChange("/")
                currentOperator = Symbols.DIVISION
            }
        }
        binding.modulusBtn.setOnClickListener {
            if(checkInput()) {
                updateOperands()
                activityCallback.onOperandChange("%")
                currentOperator = Symbols.MODULUS
            }
        }

        binding.squareRoot.setOnClickListener {
                sqrRoot = true
                updateOperands()
                activityCallback.onOperandChange("√")

        }
        binding.equalsBtn.setOnClickListener {
            if(checkInput()) { // first operand set
                updateOperands()
            }

            if(calculateResult()) { // both operands set and successfully computed result
                    inputState.clear()
                    firstOperand = Double.NaN
                    secondOperand = Double.NaN
                    activityCallback.onResult(result)
                    sqrRoot = false
                    currentOperator = Symbols.INITIAL
            }
        }

        binding.clearBtn.setOnClickListener {
            firstOperand = Double.NaN
            secondOperand = Double.NaN
            sqrRoot = false
            result = Double.NaN
            inputState.clear()
            activityCallback.onClear()
            currentOperator = Symbols.INITIAL
        }

        binding.ceBtn.setOnClickListener {
            if(checkInput()) {
                if (currentOperator == Symbols.INITIAL || inputState.isNotEmpty()) {
                    inputState.deleteAt(inputState.length - 1)
                }
                activityCallback.onClearLast()
            }

        }

    }

    private fun checkInput(): Boolean {
        return if(firstOperand.isNaN() && inputState.isEmpty() && result.isNaN()) {
            Toast.makeText(context, "Invalid Format!", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }


    private fun calculateResult(): Boolean {

        // ******** Trial code *************
        if(!firstOperand.isNaN() && sqrRoot && secondOperand.isNaN()) {
            // first operand is set and operator is square root and secondOperand is NaN
            result = firstOperand // given that first operand was already updated to sqrt(firsOperand) in updateOperands()
            return true
        }

        else if(!firstOperand.isNaN() && !secondOperand.isNaN()) {
            // Both operands are set
            Log.w("program2", "$firstOperand, $secondOperand")
            when (currentOperator) {
                Symbols.ADDITION -> result = firstOperand + secondOperand
                Symbols.SUBTRACTION -> result = firstOperand - secondOperand
                Symbols.MULTIPLICATION -> result = firstOperand * secondOperand
                Symbols.DIVISION -> result = firstOperand / secondOperand
                Symbols.MODULUS -> result = firstOperand % secondOperand
                Symbols.SQUAREROOT -> Log.w("project2", "SQUARE ROOT operation") // Might not work! Old code:  result = firstOperand * sqrt(secondOperand)
                Symbols.INITIAL -> Log.w("project2", "Initial state!")
            }
            return true
        }
        else {
            Toast.makeText(context, "Provide two operands", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun updateOperands() {
        if(!firstOperand.isNaN()) {
//            if first operand is set
            if(inputState.isNotEmpty()) {
                secondOperand = if(sqrRoot) {
                    sqrRoot = false
                    sqrt(inputState.toString().toDouble())
                }
                else {
                    inputState.toString().toDouble()
                }
                inputState.clear()
            }
            else {
                Toast.makeText(context, "Enter second operand!", Toast.LENGTH_SHORT).show()
            }
        }
        else {

                    if (!result.isNaN()) { // if result is set, so user to perform calculation with result as firstOperand
                        firstOperand = result
                    } else { // result is not set
                        Log.w("project2", "$sqrRoot")

                        if(inputState.isEmpty()) Log.w("project2", "Enter first operand")
                        else {
                        firstOperand = if (sqrRoot) {
//                            sqrRoot = false
                            sqrt(inputState.toString().toDouble())
                        } else {
                            inputState.toString().toDouble()
                        }

                        inputState.clear()
                    }
            }
        }

    }

}