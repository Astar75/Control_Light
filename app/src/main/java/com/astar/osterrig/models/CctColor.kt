package com.astar.osterrig.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CctColor(
    val red: Int,             // для cct
    val green: Int,
    val blue: Int,
    val white: Int,
    val backgroundCell: Int,  // для цвета ячейки
    val text: String
): Parcelable