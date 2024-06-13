package hr.algebra.colourapp.model

data class Item(
    var _id: Long?,
    val title: String,
    val username: String,
    val picturePath: String,
    val hexadecimal: String,
    var read: Boolean
)
