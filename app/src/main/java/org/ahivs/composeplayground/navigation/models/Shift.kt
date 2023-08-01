package org.ahivs.composeplayground.navigation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shift(val id: Int, val title: String, val description: String) : Parcelable