package com.mp.sharpsight.ui.register.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.isalepro.requestHandler.ApiResponse
import com.mp.sharpsight.requestHandler.ApiClient
import com.mp.sharpsight.requestHandler.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RegisterRepository {

    private var apiClient: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
    private val TAG = "RegisterRepositoryTAG"


    fun registerNewUser(
        email: String,
        userName: String,
        password: String
    ): MutableLiveData<ApiResponse> {
        val responseData: MutableLiveData<ApiResponse> = MutableLiveData<ApiResponse>()

        val fieldMap=HashMap<String,String>()
        fieldMap["email"]=email
        fieldMap["username"]=userName
        fieldMap["password"]=password

        apiClient.registerNewUser(fieldMap).enqueue(object : Callback<JsonElement> {
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                if (response.isSuccessful) {
                    responseData.value = ApiResponse(response.body())
                } else {
                    responseData.value = ApiResponse(response.body(), false)
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                responseData.value = ApiResponse(t)
            }
        })
        return responseData
    }

}