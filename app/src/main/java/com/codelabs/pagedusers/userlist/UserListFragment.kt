package com.codelabs.pagedusers.userlist

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pagedusers.R
import com.codelabs.pagedusers.common.adapters.CustomLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {
    companion object {
        fun newInstance() = UserListFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewGroupError: ViewGroup
    private lateinit var tvErrorMessage: TextView
    private lateinit var btnRetry: Button

    private val viewModel by viewModels<UserListViewModel>()
    private val pagingAdapter = UserAdapter(UserAdapter.UserComparator)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        viewGroupError = view.findViewById(R.id.viewGroupError)
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage)
        btnRetry = view.findViewById(R.id.btnRetry)

        recyclerView.adapter = pagingAdapter
            .withLoadStateFooter(CustomLoadStateAdapter(pagingAdapter::retry))

        pagingAdapter.addLoadStateListener {
            it.refresh.let { loadState ->
                recyclerView.isVisible = loadState is LoadState.NotLoading
                progressBar.isVisible = loadState is LoadState.Loading
                viewGroupError.isVisible = loadState is LoadState.Error

                if (loadState is LoadState.Error) {
                    tvErrorMessage.text = loadState.error.localizedMessage
                    btnRetry.setOnClickListener { pagingAdapter.refresh() }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUsersFlow().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

}