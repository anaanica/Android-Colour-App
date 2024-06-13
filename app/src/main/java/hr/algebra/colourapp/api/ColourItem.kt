package hr.algebra.colourapp.api

import com.google.gson.annotations.SerializedName

data class ColourItem(
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("numViews") val numViews : Int,
    @SerializedName("numVotes") val numVotes : Int,
    @SerializedName("numComments") val numComments : Int,
    @SerializedName("numHearts") val numHearts : Int,
    @SerializedName("rank") val rank : Int,
    @SerializedName("dateCreated") val dateCreated : String,
    @SerializedName("hex") val hex : String,
    //@SerializedName("rgb") val rgb : Rgb,
    //@SerializedName("hsv") val hsv : Hsv,
    @SerializedName("description") val description : String,
    @SerializedName("url") val url : String,
    @SerializedName("imageUrl") val imageUrl : String,
    @SerializedName("badgeUrl") val badgeUrl : String,
    @SerializedName("apiUrl") val apiUrl : String
)
