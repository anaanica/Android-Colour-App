package hr.algebra.colourapp.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.colourapp.COLOUR_PROVIDER_CONTENT_URI
import hr.algebra.colourapp.ItemPagerActivity
import hr.algebra.colourapp.POSITION
import hr.algebra.colourapp.R
import hr.algebra.colourapp.framework.startActivity
import hr.algebra.colourapp.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvHexadecimal = itemView.findViewById<TextView>(R.id.tvHexadecimal)
        private val tvUsername = itemView.findViewById<TextView>(R.id.tvUsername)

        fun bind(item: Item) {
            tvTitle.text = item.title
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.colour)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)

            tvHexadecimal.text = item.hexadecimal
            tvUsername.text = item.username
            ivRead.setImageResource(if(item.read) R.drawable.bookmark_fill else R.drawable.bookmark)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ivRead.setOnClickListener {
            updateItem(position)
        }

        holder.bind(items[position])
    }

    private fun updateItem(position: Int) {
        val item = items[position]
        item.read = !item.read
        context.contentResolver.update(
            ContentUris.withAppendedId(COLOUR_PROVIDER_CONTENT_URI, item._id!!),
            ContentValues().apply {
                put(Item::read.name, item.read)
            },
            null,
            null
        )
        notifyItemChanged(position)
    }
}