package com.yandey.githubuser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.yandey.githubuser.data.util.AppConstant
import com.yandey.githubuser.data.util.AppUtils
import com.yandey.githubuser.data.util.AppUtils.loadCircleImage
import com.yandey.githubuser.data.util.Resource
import com.yandey.githubuser.databinding.ActivityDetailBinding
import com.yandey.githubuser.presentation.adapter.SectionPagerAdapter
import com.yandey.githubuser.presentation.viewmodel.UserViewModel
import com.yandey.githubuser.presentation.viewmodel.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), View.OnClickListener {
    @Inject
    lateinit var factory: UserViewModelFactory
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: UserViewModel
    private var isFavorite: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(AppConstant.EXTRA_USER)

        val bundle = Bundle().apply {
            putString(AppConstant.EXTRA_USER, username)
        }

        viewModel = ViewModelProvider(this@DetailActivity, factory)[UserViewModel::class.java]
        Log.i("TAG", viewModel.detailUsers(username.toString()).toString())
        getDetailUsers(viewModel)

        binding.btnOpenOnGitHub.setOnClickListener(this)
        binding.fabFavorite.setOnClickListener(this)
        viewModel.checkFavoriteUsers(username ?: "").observe(this) {
            isFavoriteUser(it)
            isFavorite = it
        }

        binding.apply {
            backToActivity(ivBack)
            shareGithub(ivShare, getString(R.string.hello_there, username))
            openGithub(btnOpenOnGitHub, getString(R.string.open_github, username))
        }
        sectionPager(bundle)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fabFavorite -> {
                if (isFavorite == true) {
                    viewModel.detailUsers.observe(this) { response ->
                        response.data?.let {
                            viewModel.deleteUser(it)
                            isFavoriteUser(false)
                        }
                    }
                    AppUtils.showSnackBar(view, getString(R.string.deleted_success))
                } else {
                    viewModel.detailUsers.observe(this) { response ->
                        response.data?.let {
                            viewModel.insertUser(it)
                            isFavoriteUser(true)
                        }
                    }
                    AppUtils.showSnackBar(view, getString(R.string.success_added))
                }
            }
        }
    }

    private fun sectionPager(bundle: Bundle) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getDetailUsers(viewModel: UserViewModel) {
        viewModel.detailUsers.observe(this@DetailActivity) { response ->
            when (response) {
                is Resource.Success -> {
                    showLoading(false)
                    response.data?.let {
                        binding.apply {
                            ivUser.loadCircleImage(ivUser.context, it.avatar_url)
                            tvName.text = it.name
                            tvUsername.text = it.login
                            tvBio.text = it.bio
                            tvCompany.text = it.company
                            tvLocation.text = it.location
                            tvBlog.text = it.blog
                            tvTotalRepository.text = it.public_repos.toString()
                            tvTotalFollowers.text = it.followers.toString()
                            tvTotalFollowing.text = it.following.toString()
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    response.data?.let {
                        Toast.makeText(
                            this@DetailActivity,
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

    private fun shareGithub(view: View, username: String) {
        view.setOnClickListener {
            val sub = getString(R.string.github_connection)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, sub)
            intent.putExtra(Intent.EXTRA_TEXT, username)
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
        }
    }

    private fun openGithub(view: View, username: String) {
        view.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(username)
            }.also {
                startActivity(it)
            }
        }
    }

    private fun isFavoriteUser(favorite: Boolean) {
        isFavorite = if (favorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            true
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
            false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun backToActivity(view: View) {
        view.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}