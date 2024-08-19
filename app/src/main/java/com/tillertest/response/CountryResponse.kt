package com.tillertest.response

import com.google.gson.annotations.SerializedName

data class CountryResponse(

    val countryResponse: List<CountryItem>? = null
)

data class CountryItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("isoAlpha2")
    val isoAlpha2: String? = null,

    @field:SerializedName("dialCode")
    val dialCode: String? = null,

    @field:SerializedName("isoAlpha3")
    val isoAlpha3: String? = null,

    @field:SerializedName("displayOrder")
    val displayOrder: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("long")
    val jsonMemberLong: Any? = null,

    @field:SerializedName("isoNumeric")
    val isoNumeric: Int? = null,

    @field:SerializedName("nationality")
    val nationality: String? = null,

    @field:SerializedName("countryCode")
    var countryCode: String? = null,

    @field:SerializedName("links")
    val links: String? = null,

    @field:SerializedName("countryName")
    var countryName: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null
)
