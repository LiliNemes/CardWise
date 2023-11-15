package hu.bme.aut.android.cardwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.databinding.DeckListBinding

class DeckAdapter(private val listener: DeckClickListener) :
    RecyclerView.Adapter<DeckAdapter.DeckViewHolder>(){

    private val items = mutableListOf<Deck>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeckViewHolder(
        DeckListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val deck = items[position]

        holder.binding.tvName.text = deck.name
        holder.binding.tvDescription.text = deck.description
        holder.binding.ibRemove.setOnClickListener {
            listener.onDeckDeleted(deck)
        }
        holder.binding.ibEdit.setOnClickListener {
            listener.onDeckEdited(deck)
        }
        holder.binding.ibStart.setOnClickListener {
            listener.onDeckStarted(deck)
        }
    }

    override fun getItemCount(): Int = items.size

    interface DeckClickListener {
        fun onDeckStarted(item: Deck)
        fun onDeckDeleted(item: Deck)
        fun onDeckEdited(item: Deck)
    }

    fun addItem(item: Deck) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun updateItem(item: Deck) {
        var existingItem = items.find { it.id == item.id }
        if (existingItem != null)
        {
            var position = items.indexOf(existingItem)
            items[position] = item
            notifyItemChanged(position)
        }
    }

    fun setAllItem(deckItems: List<Deck>) {
        items.clear()
        items.addAll(deckItems)
        notifyDataSetChanged()
    }

    fun deleteItem(item: Deck) {
        var existingItem = items.find { it.id == item.id }
        if (existingItem != null)
        {
            var position = items.indexOf(existingItem)
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class DeckViewHolder(val binding: DeckListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}