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

    private fun backWebViewButton(){
        binding.backWebViewButton.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack();
            }
        }
    }

    private fun observer() {
        viewModel.user.observe(viewLifecycleOwner, ::onGetUser)
        viewModel.loading.observe(viewLifecycleOwner, ::onLoading)
        viewModel.error.observe(viewLifecycleOwner, ::onError)

    }

    private fun onGetUser(user: UserModel) {
        binding.userName.text = user.login

        user.avatar_url?.let { imageUrl ->
            Helper.glideCreator(binding.avatarImageView, imageUrl, requireContext())
        }
        user.html_url?.let {  githubUrl ->
            binding.githubTextView.text = githubUrl
            binding.webView.webViewClient = object :WebViewClient(){
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

    private fun onError(event: ConsumableValue<Throwable>) {
        event.consume { ex ->
            Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}