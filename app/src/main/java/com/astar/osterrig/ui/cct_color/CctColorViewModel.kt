package com.astar.osterrig.ui.cct_color

import androidx.annotation.IntRange
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CctColorViewModel : ViewModel() {

    private val _percentLightness = MutableLiveData<Int>()
    val percentLightness: LiveData<Int>
        get() = _percentLightness

    private val _lightness = MutableLiveData<Int>()
    val lightness: LiveData<Int>
        get() = _lightness

    private val _tintValue = MutableLiveData<Int>()
    val tintValue: LiveData<Int>
        get() = _tintValue

    private val _tintPreview = MutableLiveData<Int>()
    val tintPreview: LiveData<Int>
        get() = _tintPreview


    fun setLightness(@IntRange(from = 0, to = 255) lightness: Int) {
        _lightness.value = lightness
        _percentLightness.value = (lightness * 100) / 255
    }

    fun setTintColor(@IntRange(from = 0, to = 100) tint: Int) {
        _tintValue.value = tint
        _tintPreview.value = tint - 50
    }

}