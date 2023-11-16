package hu.bme.aut.android.cardwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.databinding.CardListBinding

class CardAdapter(private val listener: CardClickListener) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>(){

    private val items = mutableListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(
        CardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = items[position]

        holder.binding.cardQuestion.text = card.question
        holder.binding.cardAnswer.text = card.answer
        holder.binding.cardEdit.setOnClickListener {
            listener.onCardEditClicked(card)
        }
        holder.binding.cardDelete.setOnClickListener {
            listener.onCardDeleteClicked(card)
        }
    }

    override fun getItemCount(): Int = items.size

    interface CardClickListener {
        fun onCardDeleteClicked(item: Card)
        fun onCardEditClicked(item: Card)
    }

    fun addItem(item: Card) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun updateItem(item: Card) {
        var existingItem = items.find { it.id == item.id }
        if (existingItem != null)
        {
            var position = items.indexOf(existingItem)
            items[position] = item
            notifyItemChanged(position)
        }
    }

    fun setAllItem(cards: List<Card>) {
        items.clear()
        items.addAll(cards)
        notifyDataSetChanged()
    }

    fun deleteItem(item: Card) {
        var existingItem = items.find { it.id == item.id }
        if (existingItem != null)
        {
            var position = items.indexOf(existingItem)
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class CardViewHolder(val binding: CardListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}