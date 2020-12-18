package com.astar.osterrig.ui.ftp_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FtpViewModel: ViewModel() {

    private val _redColor = MutableLiveData<Int>()
    val redColor: LiveData<Int>
        get() = _redColor

    private val _greenColor = MutableLiveData<Int>()
    val greenColor: LiveData<Int>
        get() = _greenColor

    private val _blueColor = MutableLiveData<Int>()
    val blueColor: LiveData<Int>
        get() = _blueColor



}