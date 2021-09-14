package com.mp.sharpsight.ui.register.dto


import com.google.gson.annotations.SerializedName

data class RegisterDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String
)