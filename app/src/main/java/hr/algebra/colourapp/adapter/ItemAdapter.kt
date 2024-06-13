package hr.algebra.colourapp.adapter

import android.content.ContentUris
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

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(item: Item) {
            tvTitle.text = item.title
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.colour)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnLongClickListener {
            //delete
            val item = items[position]
            context.contentResolver.delete(
                ContentUris.withAppendedId(COLOUR_PROVIDER_CONTENT_URI, item._id!!),
                null, null
            )
            File(item.picturePath).delete()
            items.removeAt(position)
            notifyDataSetChanged()

            true
        }

        holder.itemView.setOnClickListener {
            // edit
            context.startActivity<ItemPagerActivity>(POSITION, position)
        }

        holder.bind(items[position])
    }
}