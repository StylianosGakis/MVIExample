package se.stylianosgakis.mviexample.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogPost(
    @Expose
    @SerializedName("pk")
    val pk: Int? = null,

    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("body")
    val body: String? = null,

    @Expose
    @SerializedName("image")
    val image: String? = null
) {

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as BlogPost
        if (pk != other.pk) return false
        return true
    }

    override fun toString(): String {
        return "BlogPost(pk=$pk, title=$title, body=$body, image=$image)"
    }

}