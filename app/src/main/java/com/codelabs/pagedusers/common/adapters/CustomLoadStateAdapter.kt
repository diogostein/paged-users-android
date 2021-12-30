package com.codelabs.pagedusers.common.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pagedusers.R

class CustomLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CustomLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.load_state_item, parent, false)
            .let { LoadStateViewHolder(it, retry) }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadStateViewHolder(
        itemView: View,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
        private val viewGroupError = itemView.findViewById<ViewGroup>(R.id.viewGroupError)
        private val tvRetry = itemView.findViewById<TextView>(R.id.tvRetry)

        init {
            itemView.findViewById<Button>(R.id.btnRetry).setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            viewGroupError.isVisible = loadState is LoadState.Error
            tvRetry.text = if (loadState is LoadState.Error) loadState.error.localizedMessage else null
        }
    }
}