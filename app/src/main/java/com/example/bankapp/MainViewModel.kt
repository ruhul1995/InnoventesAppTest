package com.example.bankapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private var debouncePeriod: Long = 500
    private var searchJob: Job? = null
    private var searchJob2:Job? = null
    val panValidationLiveData = MutableLiveData<Boolean>()
    val dateOfBirthValidationLiveData = MutableLiveData<Boolean>()

    fun onPanValidateQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)

            var status = PanRepository.panValidation(query)
            panValidationLiveData.postValue(status)
        }
    }

    fun onDobValidateQuery(dob: String)
    {
        searchJob2?.cancel()
        searchJob2 = viewModelScope.launch {
            delay(debouncePeriod)
            if (dob.isNotEmpty()) {
                var status = PanRepository.dobValidation(dob)
                dateOfBirthValidationLiveData.postValue(status)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
        searchJob2?.cancel()
    }


}