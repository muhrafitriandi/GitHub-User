package com.yandey.githubuser.presentation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yandey.githubuser.DetailActivity
import com.yandey.githubuser.data.model.UserResponse
import com.yandey.githubuser.data.util.AppUtils.loadCircleImage
import com.yandey.githubuser.data.util.AppConstant
import com.yandey.githubuser.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            parent.context,
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class UserViewHolder(private val context: Context, private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserResponse) {
            binding.apply {
                tvUsername.text = item.login
                ivUser.loadCircleImage(context, item.avatar_url)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(AppConstant.EXTRA_USER, item.login)
                itemView.context.startActivity(intent)
            }
        }
    }
}