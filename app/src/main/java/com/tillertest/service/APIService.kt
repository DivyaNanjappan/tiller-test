package com.tillertest.service

import com.tillertest.response.CountryItem
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET(EndPoints.COUNTRIES)
    fun fetchCountries(): Call<List<CountryItem>>
}