package com.yandey.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yandey.githubuser.databinding.ActivityFavoriteBinding
import com.yandey.githubuser.presentation.adapter.UserAdapter
import com.yandey.githubuser.presentation.viewmodel.UserViewModel
import com.yandey.githubuser.presentation.viewmodel.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: UserViewModelFactory

    @Inject
    lateinit var userAdapter: UserAdapter
    lateinit var viewModel: UserViewModel
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@FavoriteActivity, factory)[UserViewModel::class.java]

        binding.apply {
            initRecyclerView(rvUser)
            backToActivity(ivBack)
        }

        getFavoriteUsers(viewModel)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val users = userAdapter.differ.currentList[position]
                viewModel.deleteUser(users)
                Snackbar.make(
                    binding.coordinatorLayout,
                    getString(R.string.deleted_success),
                    Snackbar.LENGTH_LONG
                ).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.insertUser(users)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvUser)
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            userAdapter = UserAdapter()
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
    }

    private fun getFavoriteUsers(viewModel: UserViewModel) {
        viewModel.getAllUsers().observe(this@FavoriteActivity) {
            if (it.isEmpty()) {
                showEmptyData(true)
            } else {
                showEmptyData(false)
            }
            userAdapter.differ.submitList(it)
        }
    }

    private fun showEmptyData(isEmpty: Boolean) {
        if (isEmpty) {
            binding.apply {
                ivNoFavorite.visibility = View.VISIBLE
                tvNoFavorite.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                ivNoFavorite.visibility = View.INVISIBLE
                tvNoFavorite.visibility = View.INVISIBLE
            }
        }
    }

    private fun backToActivity(view: View) {
        view.setOnClickListener {
            onBackPressed()
        }
    }
}