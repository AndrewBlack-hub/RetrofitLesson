package com.androidgang.lessonapikudago

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MoviesResponse {

    @SerializedName("count")
    @Expose
    val count: Int = 0

    @SerializedName("next")
    @Expose
    val next: String = ""

    @SerializedName("previous")
    @Expose
    val previous: Any? = null

    @SerializedName("results")
    @Expose
    val result: List<Result>? = null

    class Poster {

        @SerializedName("image")
        @Expose
        val image: String = ""

        @SerializedName("source")
        @Expose
        val source: Source? = null

    }

    class Result {

        @SerializedName("id")
        @Expose
        val id: Int = 0

        @SerializedName("title")
        @Expose
        val title: String = ""

        @SerializedName("poster")
        @Expose
        val poster: Poster? = null

    }

    class Source {
        @SerializedName("name")
        @Expose
        val name: String = ""

        @SerializedName("link")
        @Expose
        val link: String = ""


    }
}
