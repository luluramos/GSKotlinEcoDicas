package br.com.fiap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val onItemRemoved: (ItemModel) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items = listOf<ItemModel>() // Lista original
    private var filteredItems = listOf<ItemModel>() // Lista filtrada

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
        val button: ImageButton = view.findViewById(R.id.imageButton)

        fun bind(item: ItemModel) {
            textViewTitle.text = item.title
            textViewDescription.text = item.description
            button.setOnClickListener {
                onItemRemoved(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<ItemModel>) {
        items = newItems
        filteredItems = newItems
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            items
        } else {
            items.filter {
                it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}