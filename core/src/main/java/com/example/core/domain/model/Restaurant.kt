package com.example.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Restaurant(
    val pictureId: String,
    val city: String,
    val name: String,
    val rating: Double,
    val description: String,
    val id: String
): Parcelable