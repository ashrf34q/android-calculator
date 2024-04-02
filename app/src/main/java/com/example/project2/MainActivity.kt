package com.example.project2

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), KeyboardFragment.DataListener {

    private lateinit var decimalFormat: DecimalFormat
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

    override fun onConfigurationChanged(newConfig: Configuration) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()

        super.onConfigurationChanged(newConfig)

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            Log.w("Project2", "Landscape mode")
            setContentView(R.layout.activity_main_landscape)
        }
        else{
            setContentView(R.layout.activity_main)
        }
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

    override fun onClearLast() {
        val screenFragmentInstance = supportFragmentManager.findFragmentById(R.id.screenLayout) as ScreenFragment
        screenFragmentInstance.clearLast()
    }
}