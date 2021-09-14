package com.mp.sharpsight.requestHandler

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @FormUrlEncoded
    @POST("apiuser/register")
    fun registerNewUser(@FieldMap fields:HashMap<String,String>): Call<JsonElement>

}
