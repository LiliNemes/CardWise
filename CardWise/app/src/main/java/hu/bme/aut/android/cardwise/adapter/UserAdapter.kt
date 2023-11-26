package hu.bme.aut.android.cardwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cardwise.data.Deck
import hu.bme.aut.android.cardwise.data.UserStatInfo
import hu.bme.aut.android.cardwise.databinding.DeckListBinding
import hu.bme.aut.android.cardwise.databinding.UserListBinding

class UserAdapter() :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val items = mutableListOf<UserStatInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvRank.text =item.rank.toString() + "."
        holder.binding.tvName.text = item.name
        holder.binding.tvScore.text = item.score
    }

    override fun getItemCount(): Int = items.size

    fun setAllItem(userStatItems: List<UserStatInfo>) {
        items.clear()
        items.addAll(userStatItems)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}