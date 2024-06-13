package hr.algebra.colourapp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.colourapp.dao.Repository
import hr.algebra.colourapp.factory.getColourRepository
import hr.algebra.colourapp.model.Item

private const val AUTHORITY = "hr.algebra.colourapp.api.provider"
private const val PATH = "items"
val COLOUR_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

private const val ITEMS = 10
private const val ITEM_ID = 20

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, ITEMS) // hr.algebra.colourapp.api.provider/items
    addURI(AUTHORITY, "$PATH/#", ITEM_ID) // hr.algebra.colourapp.api.provider/items/5
    this
}

//1. content:hr.algebra.colourapp.api.provider/items
//2. content:hr.algebra.colourapp.api.provider/items/5

class ColourContentProvider : ContentProvider() {

    private lateinit var repository: Repository

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)) {
            ITEMS -> return repository.delete(selection, selectionArgs)
            ITEM_ID -> {
                val id = uri.lastPathSegment // 5
                if (id != null) {
                    return repository.delete("${Item::_id.name}=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("WRONG URI")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = repository.insert(values) // id!!!
        return ContentUris.withAppendedId(COLOUR_PROVIDER_CONTENT_URI, id)
    }

    override fun onCreate(): Boolean {
        repository = getColourRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = repository.query(
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)) {
            ITEMS -> return repository.update(values, selection, selectionArgs)
            ITEM_ID -> {
                val id = uri.lastPathSegment // 5
                if (id != null) {
                    return repository.update(values, "${Item::_id.name}=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("WRONG URI")
    }
}