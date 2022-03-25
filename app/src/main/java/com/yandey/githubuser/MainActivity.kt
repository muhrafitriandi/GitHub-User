package com.yandey.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yandey.githubuser.data.util.Resource
import com.yandey.githubuser.databinding.ActivityMainBinding
import com.yandey.githubuser.presentation.adapter.UserAdapter
import com.yandey.githubuser.presentation.viewmodel.UserViewModel
import com.yandey.githubuser.presentation.viewmodel.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: UserViewModelFactory

    @Inject
    lateinit var userAdapter: UserAdapter
    lateinit var viewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProvider(this@MainActivity, factory)[UserViewModel::class.java]

        getSearchUsers(viewModel)

        showDefaultHome(true)

        initRecyclerView(binding.rvUser)
    }

    override fun onBackPressed() {
        startActivity(
            Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = getString(R.string.search_username)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchUsers(query.toString())
                    getSearchUsers(viewModel)
                    showDefaultHome(false)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    showDefaultHome(true)
                    initRecyclerView(binding.rvUser)
                    return false
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_favorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_setting -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            userAdapter = UserAdapter()
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    fun getSearchUsers(viewModel: UserViewModel) {
        viewModel.searchUsers.observe(this@MainActivity) { response ->
            when (response) {
                is Resource.Success -> {
                    showLoading(false)
                    response.data?.let {
                        if (it.items.isEmpty()) {
                            showEmptyData(true)
                        } else {
                            showEmptyData(false)
                        }
                        binding.tvResultCount.text =
                            getString(R.string.showing_results, it.items.size)
                        userAdapter.differ.submitList(it.items.toList())
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    response.data?.let {
                        Toast.makeText(
                            this@MainActivity,
                            "An error occurred : $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun showDefaultHome(isRemove: Boolean) {
        if (isRemove) {
            binding.apply {
                ivSearchIllustration.visibility = View.VISIBLE
                tvSearch.visibility = View.VISIBLE
                ivNoDataIllustration.visibility = View.INVISIBLE
                tvNoData.visibility = View.INVISIBLE
                tvResultCount.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                ivSearchIllustration.visibility = View.INVISIBLE
                tvSearch.visibility = View.INVISIBLE
                tvResultCount.visibility = View.VISIBLE
            }
        }
    }

    private fun showEmptyData(isEmpty: Boolean) {
        if (isEmpty) {
            binding.apply {
                ivNoDataIllustration.visibility = View.VISIBLE
                tvNoData.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                ivNoDataIllustration.visibility = View.INVISIBLE
                tvNoData.visibility = View.INVISIBLE
            }
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