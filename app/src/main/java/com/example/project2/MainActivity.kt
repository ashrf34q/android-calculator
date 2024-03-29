package com.example.project2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), KeyboardFragment.DataListener {

    private lateinit var decimalFormat: DecimalFormat
//    private val screenFragmentInstance : ScreenFragment = ScreenFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        decimalFormat = DecimalFormat("#.######")

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.screenLayout, ScreenFragment())
        ft.replace(R.id.keyboard, KeyboardFragment())
        ft.commit()
    }


    override fun onOperandChange(operand: String) {
        val screenFragment = supportFragmentManager.findFragmentById(R.id.screenLayout) as ScreenFragment
        screenFragment.setOperand(operand)
    }

    override fun onResult(result: Double) {
        val screenFragment = supportFragmentManager.findFragmentById(R.id.screenLayout) as ScreenFragment
        screenFragment.setResult(decimalFormat.format(result))
    }

    override fun onClear() {
        val screenFragmentInstance = supportFragmentManager.findFragmentById(R.id.screenLayout) as ScreenFragment
        screenFragmentInstance.clearScreen()
    }
}