package com.example.itoll.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoll.databinding.FragmentUsersBinding
import com.example.itoll.domain.model.UserModel
import com.example.itoll.presentation.ConsumableValue
import com.example.itoll.presentation.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.SearchView
import com.example.itoll.domain.model.FunctionName
import com.example.itoll.presentation.Helper

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private val viewModel: ViewModel by viewModels()

    lateinit var binding: FragmentUsersBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: UserAdapter
    var searchTxt = String()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUsers()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentUsersBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        search()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.userRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        observer()
    }

    private fun observer() {
        viewModel.users.observe(viewLifecycleOwner, ::onGetUser)
        viewModel.loading.observe(viewLifecycleOwner, ::onLoading)
        viewModel.failure.observe(viewLifecycleOwner, ::onFailure)
        viewModel.error.observe(viewLifecycleOwner, ::onError)

    }


    private fun onGetUser(users: List<UserModel>?) {
        users?.let {
            adapter = UserAdapter(users) { user ->
                val data = user.login

                Toast.makeText(requireContext(), data, Toast.LENGTH_SHORT).show()

                if (user.login != null) {
                    val action =
                        UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(user.login)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "This UserHas no UserName", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            recyclerView.adapter = adapter
        }

    }

    private fun onLoading(event: ConsumableValue<Boolean>) {
        event.consume { isLoading ->

            when (isLoading) {
                true -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.searchView.visibility = View.GONE
                    binding.userRecyclerView.visibility = View.GONE
                }

                false -> {
                    binding.progressBar.visibility = View.GONE
                    binding.searchView.visibility = View.VISIBLE
                    binding.userRecyclerView.visibility = View.VISIBLE
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
            binding.showErrorTextView.text = Helper.asNetworkException(ex)
        }
        binding.tryagainButton.visibility = View.VISIBLE
        binding.showErrorTextView.visibility = View.VISIBLE
        binding.tryagainButton.setOnClickListener {

            binding.tryagainButton.visibility = View.GONE
            binding.showErrorTextView.visibility = View.GONE

            when (viewModel.lastFunctionCall) {
                FunctionName.USERS -> viewModel.getUsers()
                FunctionName.SEARCH -> viewModel.searchUser(searchTxt)
            }
        }
    }

    private fun search() {


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                searchText?.let {
                    searchTxt = it
                    viewModel.searchUser(it)
                }
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text?.isEmpty() == true) {
                    viewModel.getUsers()
                }
                return true
            }
        })


    }
}