package com.magni.coursesdemo.feature.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.magni.coursesdemo.feature.home.databinding.FragmentHomeBinding
import com.magni.coursesdemo.presentation.CourseAdapter
import com.magni.coursesdemo.presentation.UiState
import com.magni.coursesdemo.presentation.getErrorMessage

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupListeners()

    }

    private fun setupRecyclerView() {
        adapter = CourseAdapter()
        binding.stateContainer.setAdapter(adapter)
        binding.stateContainer.setLayoutManager(LinearLayoutManager(requireContext()))
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.courses.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.stateContainer.showLoading()
                        }
                        is UiState.Success -> {
                            adapter.submitList(uiState.data.toList())

                            if (uiState.data.isNotEmpty()) {
                                binding.stateContainer.showContent()
                            } else {
                                binding.stateContainer.showEmpty()
                            }
                        }
                        is UiState.Error -> {
                            binding.stateContainer.showError(getErrorMessage(uiState.error, requireContext()))
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.stateContainer.onRetryClickListener = {
            viewModel.loadCourses()
        }

        adapter.onLikeClicked = {
            viewModel.updateCourseLikeStatus(it.id)
        }

        adapter.onMoreClicked = {

        }

        binding.btnFilter.setOnClickListener {
            // Handle filter action
        }

        binding.btnSort.setOnClickListener {
            viewModel.sortByDate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}