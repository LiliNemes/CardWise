package hu.bme.aut.android.cardwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cardwise.data.Card
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.databinding.CardListBinding
import hu.bme.aut.android.cardwise.databinding.StudyListBinding

class StudyAdapter() :
    RecyclerView.Adapter<StudyAdapter.CardViewHolder>(){

    private val items = mutableListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(
        StudyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = items[position]

        holder.binding.tvQuestion.text = card.question
    }

    override fun getItemCount(): Int = items.size

    fun setAllItem(cards: List<Card>) {
        items.clear()
        items.addAll(cards)
        notifyDataSetChanged()
    }

    inner class CardViewHolder(val binding: StudyListBinding) : RecyclerView.ViewHolder(binding.root) {
    }

}