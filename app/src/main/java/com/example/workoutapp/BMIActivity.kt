package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object{
        private const val Metric_Units_View = "METRIC_UNIT_VIEW"
        private const val Us_Units_View = "US_UNITS_VIEW"
    }
    private var currentVisibleView : String = Metric_Units_View
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        makeVisibleMetricUnitsView()
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkerId:Int ->
            if(checkerId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }
            else{
                makeVisibleUsUnitsView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = Metric_Units_View
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitUsHeightInch?.visibility = View.GONE
        binding?.tilMetricUsUnitUsHeightFeet?.visibility = View.GONE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun makeVisibleUsUnitsView(){
        currentVisibleView = Us_Units_View
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUsUnitUsHeightInch?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitUsHeightFeet?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE

        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitWeight?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIResult (bmi : Float){
        val bmiLabel : String
        val bmiDescription : String
        if(bmi<=15f){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        }
        else if(bmi>15f && bmi<=16f){
            bmiLabel = "severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        }
        else if(bmi>16f && bmi<=18.5f){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        }
        else if(bmi>18.5f && bmi<=25f){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }
        else if(bmi>25f && bmi<=35f){
            bmiLabel = "Obese Class"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more"
        }
        else if(bmi>35f && bmi<=40f){
            bmiLabel = "Obese Class"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now"
        }
        else{
            bmiLabel = "Obese Class"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }
    private fun validateMetricUnits() : Boolean{
        var isValid = true
        if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }
    private fun validateUsUnits() : Boolean{
        var isValid = true
        if(binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etUsMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }
    private fun calculateUnits(){
        if(currentVisibleView == Metric_Units_View){
            if(validateMetricUnits()){
                val height : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weight : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi = weight/(height * height)
                displayBMIResult(bmi)
            }
            else{
                Toast.makeText(this,"Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if(validateUsUnits()) {
                val usUnitHeightValueFeet: String = binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch : String = binding?.etUsMetricUnitHeightInch?.text.toString()
                val usUnitWeightValue : Float = binding?.etUsMetricUnitWeight?.text.toString().toFloat()
                val heightValue = (usUnitHeightValueFeet.toFloat()*12) + usUnitHeightValueInch.toFloat()
                val bmi = 703 *(usUnitWeightValue/(heightValue*heightValue))
                displayBMIResult(bmi)
            }
            else{
                Toast.makeText(this,"Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }
}