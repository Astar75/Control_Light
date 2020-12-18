package com.astar.osterrig.ui.color_control

import android.app.Application
import android.graphics.Color
import androidx.annotation.IntRange
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ColorViewModel(app: Application) : AndroidViewModel(app) {

    private val _previewColor = MutableLiveData<Int>()
    val previewColor: LiveData<Int>
        get() = _previewColor

    private val _previewHexColor = MutableLiveData<String>()
    val previewHexColor: LiveData<String>
        get() = _previewHexColor

    private val _currentColorColorSelector = MutableLiveData<Int>()
    val currentColorColorSelector: LiveData<Int>
        get() = _currentColorColorSelector

    private val _colorPresetButton0 = MutableLiveData<Int>()
    val colorPresetButton0: LiveData<Int>
        get() = _colorPresetButton0

    private val _colorPresetButton1 = MutableLiveData<Int>()
    val colorPresetButton1: LiveData<Int>
        get() = _colorPresetButton1

    private val _colorPresetButton2 = MutableLiveData<Int>()
    val colorPresetButton2: LiveData<Int>
        get() = _colorPresetButton2

    private val _colorPresetButton3 = MutableLiveData<Int>()
    val colorPresetButton3: LiveData<Int>
        get() = _colorPresetButton3

    private val _colorPresetButton4 = MutableLiveData<Int>()
    val colorPresetButton4: LiveData<Int>
        get() = _colorPresetButton4

    private val _colorPresetButton5 = MutableLiveData<Int>()
    val colorPresetButton5: LiveData<Int>
        get() = _colorPresetButton5

    private val _lightness = MutableLiveData<Int>()
    val lightness: LiveData<Int>
        get() = _lightness

    private val _percentLightness = MutableLiveData<Int>()
    val percentLightness: LiveData<Int>
        get() = _percentLightness

    init {
        _colorPresetButton0.value = Color.WHITE
        _colorPresetButton1.value = Color.WHITE
        _colorPresetButton2.value = Color.WHITE
        _colorPresetButton3.value = Color.WHITE
        _colorPresetButton4.value = Color.WHITE
        _colorPresetButton5.value = Color.WHITE
    }

    fun setPreviewColor(color: Int) {
        _previewColor.value = color
        _previewHexColor.value =
            String.format("#%02X%02X%02X", Color.red(color), Color.green(color), Color.blue(color))
    }

    fun setLightness(@IntRange(from = 0, to = 255) lightness: Int) {
        _lightness.value = lightness
        _percentLightness.value = (lightness * 100) / 255
    }

    fun setColorPresetButton(row: Int, color: Int) {
        when (row) {
            0 -> _colorPresetButton0.value = color
            1 -> _colorPresetButton1.value = color
            2 -> _colorPresetButton2.value = color
            3 -> _colorPresetButton3.value = color
            4 -> _colorPresetButton4.value = color
            5 -> _colorPresetButton5.value = color
        }
    }

    fun setColorSelectorValue(color: Int) {
        _currentColorColorSelector.value = color
    }

}