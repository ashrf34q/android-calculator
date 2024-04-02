package com.example.project2

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project2.databinding.FragmentScreenBinding
import java.text.DecimalFormat

class ScreenFragment : Fragment() {


    private lateinit var binding: FragmentScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun clearScreen() {
        binding.screenView.text = ""
    }

    fun setOperand(operand: String) {
        binding.screenView.append(operand)
    }

    fun setResult(result: String) {
        binding.screenView.text = result
    }

    fun clearLast() {
        var text = binding.screenView.text
        text = text.substring(0, text.length - 1)
        binding.screenView.text = text
    }
}