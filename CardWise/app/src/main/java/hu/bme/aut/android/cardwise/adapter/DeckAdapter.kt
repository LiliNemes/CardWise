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
    }

    override fun getItemCount(): Int = items.size

    interface DeckClickListener {
        fun onItemChanged(item: Deck)
    }

    inner class DeckViewHolder(val binding: DeckListBinding) : RecyclerView.ViewHolder(binding.root)

    fun addItem(item: Deck) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<Deck>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }
}