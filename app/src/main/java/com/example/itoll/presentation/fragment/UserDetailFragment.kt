package com.example.itoll.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.itoll.R
import com.example.itoll.databinding.FragmentUserDetailBinding
import com.example.itoll.databinding.FragmentUsersBinding
import com.example.itoll.domain.model.FunctionName
import com.example.itoll.domain.model.UserModel
import com.example.itoll.presentation.ConsumableValue
import com.example.itoll.presentation.Helper
import com.example.itoll.presentation.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private val viewModel: ViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentUserDetailBinding
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUser(args.UserLogin)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        backWebViewButton()
        observer()
        return binding.root
    }

    private fun backWebViewButton() {
        binding.backWebViewButton.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack();
            }
        }
    }

    private fun observer() {
        viewModel.user.observe(viewLifecycleOwner, ::onGetUser)
        viewModel.loading.observe(viewLifecycleOwner, ::onLoading)
        viewModel.failure.observe(viewLifecycleOwner, ::onFailure)
        viewModel.error.observe(viewLifecycleOwner, ::onError)

    }

    fun visibility(isVisibleMainViews: Boolean) {
        if (isVisibleMainViews) {
            binding.titleUserName.visibility = View.VISIBLE
            binding.userName.visibility = View.VISIBLE
            binding.githubTextView.visibility = View.VISIBLE
            binding.githubTitleTextView.visibility = View.VISIBLE
            binding.cardView.visibility = View.VISIBLE
            binding.cardView2.visibility = View.VISIBLE
            binding.tryagainButton.visibility = View.GONE
            binding.showErrorTextView.visibility = View.GONE
        } else {
            binding.titleUserName.visibility = View.GONE
            binding.userName.visibility = View.GONE
            binding.githubTextView.visibility = View.GONE
            binding.githubTitleTextView.visibility = View.GONE
            binding.cardView.visibility = View.GONE
            binding.cardView2.visibility = View.GONE
            binding.tryagainButton.visibility = View.VISIBLE
            binding.showErrorTextView.visibility = View.VISIBLE
        }
    }

    private fun onGetUser(user: UserModel) {
        binding.userName.text = user.login
        visibility(true)
        user.avatar_url?.let { imageUrl ->
            Helper.glideCreator(binding.avatarImageView, imageUrl, requireContext())
        }
        user.html_url?.let { githubUrl ->
            binding.githubTextView.text = githubUrl
            binding.webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                    return true
                }

            }
            binding.webView.loadUrl(githubUrl)
        }

        binding.webView.setOnClickListener {
            webView.reload()
        }
    }

    private fun onLoading(event: ConsumableValue<Boolean>) {
        event.consume { isLoading ->

            when (isLoading) {
                true -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                false -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun onFailure(event: ConsumableValue<String>) {
        event.consume { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun onError(event: ConsumableValue<Throwable>) {
        event.consume { ex ->
            visibility(false)
            binding.showErrorTextView.text = Helper.asNetworkException(ex)
        }

        binding.tryagainButton.setOnClickListener {
            binding.tryagainButton.visibility = View.GONE
            binding.showErrorTextView.visibility = View.GONE

            viewModel.getUser(args.UserLogin)
        }
    }
}