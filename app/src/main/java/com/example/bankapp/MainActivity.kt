package com.example.bankapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        pan_edt.addTextChangedListener(validateTextWatcher)
        year_edt.addTextChangedListener(validateDateWatcher)
        initialiseObservers()

        noPan_tV.setOnClickListener{
            finish();
        }
    }

    private fun initialiseObservers() {
        mainViewModel.panValidationLiveData.observe(this, Observer {
            if (!it) {
                next_btn.isEnabled = false
                pan_til.error = "Invalid Pan"
                pan_til.isErrorEnabled = true
            }
            else {

                pan_til.isErrorEnabled = false
                mainViewModel.dateOfBirthValidationLiveData.observe(this, Observer {

                    if(!it)
                    {
                        next_btn.isEnabled = false
                        textInputLayout3.error = "Invalid Day"
                        textInputLayout4.error = "Invalid Month"
                        textInputLayout5.error = "Invalid Year"
                        textInputLayout3.isErrorEnabled = true
                        textInputLayout4.isErrorEnabled = true
                        textInputLayout5.isErrorEnabled = true
                    }
                    else
                    {
                        textInputLayout3.isErrorEnabled = false
                        textInputLayout4.isErrorEnabled = false
                        textInputLayout5.isErrorEnabled = false
                        next_btn.isEnabled = true
                        next_btn.setOnClickListener{
                            Toast.makeText(applicationContext,"Details submitted Successfully" ,Toast.LENGTH_LONG).show()
                            finish();
                         }
                    }

                /*next_btn.isEnabled = true
                next_btn.setOnClickListener{
                    Toast.makeText(applicationContext,"Details submitted Successfully" ,Toast.LENGTH_LONG).show()*/
                    //finish();
                })
            }
        })
    }

    private val validateTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            // Start the validate
            mainViewModel.onPanValidateQuery(editable.toString())

        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
    private val validateDateWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            var dateData:String = dateOfBirthObservers()
            mainViewModel.onDobValidateQuery(dateData)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }


    }

    private fun dateOfBirthObservers():String
    {
        var dateOfBirth: String? = null
        var day: String = day_edt?.getText().toString()
        var month: String = month_edt?.getText().toString()
        var year : String = year_edt?.getText().toString()
        dateOfBirth = day + month + year

        Toast.makeText(applicationContext,"Called date of birth validation" ,Toast.LENGTH_LONG).show();
        return dateOfBirth
    }

}