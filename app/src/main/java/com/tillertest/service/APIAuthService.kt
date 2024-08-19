package com.tillertest.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIAuthService {

    @FormUrlEncoded
    @POST(EndPoints.ACCESS_TOKEN)
    fun fetchAccessToken(@FieldMap params: Map<String, String>): Call<Any>
}