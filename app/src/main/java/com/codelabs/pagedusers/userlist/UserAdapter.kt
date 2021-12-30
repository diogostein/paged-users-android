package com.codelabs.pagedusers.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.pagedusers.R
import com.codelabs.pagedusers.common.models.User

class UserAdapter(
    diffCallback: DiffUtil.ItemCallback<User>
) : PagingDataAdapter<User, UserAdapter.UserViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_user, parent, false)
            .let { UserViewHolder(it) }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        private val textView = itemView.findViewById<TextView>(R.id.textView)
        private val textView2 = itemView.findViewById<TextView>(R.id.textView2)

        fun bind(user: User?) {
            user?.run {
                textView.text = name
                textView2.text = email

                Glide.with(itemView)
                    .load(user.pictureUrl)
                    .placeholder(R.drawable.user_placeholder)
                    .into(imageView)
            }
        }
    }

    object UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.email == newItem.email
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }

}