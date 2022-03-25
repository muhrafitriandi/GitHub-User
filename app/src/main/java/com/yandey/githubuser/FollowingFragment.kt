package com.yandey.githubuser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yandey.githubuser.data.util.AppConstant
import com.yandey.githubuser.data.util.Resource
import com.yandey.githubuser.databinding.FragmentFollowBinding
import com.yandey.githubuser.presentation.adapter.UserAdapter
import com.yandey.githubuser.presentation.viewmodel.UserViewModel
import com.yandey.githubuser.presentation.viewmodel.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FollowingFragment : Fragment() {
    @Inject
    lateinit var factory: UserViewModelFactory

    @Inject
    lateinit var userAdapter: UserAdapter
    lateinit var viewModel: UserViewModel
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(AppConstant.EXTRA_USER).toString()

        initRecyclerView(binding.rvUser)

        viewModel = ViewModelProvider(this@FollowingFragment, factory)[UserViewModel::class.java]
        Log.i("TAG", viewModel.followingUsers(username).toString())

        getFollowingUsers(viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            userAdapter = UserAdapter()
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getFollowingUsers(viewModel: UserViewModel) {
        viewModel.followingUsers(username)
        viewModel.followUsers.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    showLoading(false)
                    response.data?.let {
                        if (it.isEmpty()) {
                            showEmptyData(true)
                        } else {
                            showEmptyData(false)
                        }
                        userAdapter.differ.submitList(it.toList())
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    response.data?.let {
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun showEmptyData(isEmpty: Boolean) {
        if (isEmpty) {
            binding.tvMessage.text = resources.getString(R.string.doesnt_follow_anyone, username)
        } else {
            binding.tvMessage.visibility = View.INVISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}