package com.example.itoll.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoll.databinding.FragmentUsersBinding
import com.example.itoll.domain.model.UserModel
import com.example.itoll.presentation.ConsumableValue
import com.example.itoll.presentation.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    lateinit var binding: FragmentUsersBinding
    private val viewModel: ViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentUsersBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.userRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getData()
        observer()
    }

    private fun observer() {
        viewModel.users.observe(viewLifecycleOwner, ::onGetUser)
        viewModel.loading.observe(viewLifecycleOwner, ::onLoading)
        viewModel.error.observe(viewLifecycleOwner, ::onError)

    }


    private fun onGetUser(event: ConsumableValue<List<UserModel>>) {
        event.consume { users ->
            adapter = UserAdapter(users) {
                val data = it.login
                Toast.makeText(requireContext(), data, Toast.LENGTH_SHORT).show()
            }
            recyclerView.adapter = adapter
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