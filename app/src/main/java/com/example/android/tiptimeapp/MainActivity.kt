package com.example.android.tiptimeapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.android.tiptimeapp.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateBtn.setOnClickListener{ calculateTip() }
        binding.costOfServiceET.setOnKeyListener { view, i, _ ->
            handleKeyEvent(view,i)
        }

    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfService.editText?.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        val tipPercentage = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.amazingRadioBtn -> 0.20
            R.id.goodRadioBtn -> 0.18
            else -> 0.15
        }
        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        var tip = tipPercentage * cost
        if (binding.roundSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        // Display the formatted tip value on screen
        displayTip(tip)



    }

    @SuppressLint("StringFormatInvalid")
    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }


}