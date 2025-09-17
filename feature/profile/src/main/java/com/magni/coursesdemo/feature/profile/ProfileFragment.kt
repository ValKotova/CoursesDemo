package com.magni.coursesdemo.feature.profile

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
import com.magni.coursesdemo.feature.profile.databinding.FragmentProfileBinding
import com.magni.coursesdemo.presentation.CourseAdapter
import com.magni.coursesdemo.presentation.NavigationContract
import com.magni.coursesdemo.presentation.UiState
import com.magni.coursesdemo.presentation.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: CourseAdapter

    @Inject
    lateinit var navigator: NavigationContract

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        binding.btnSupport.setOnClickListener {
            // Handle support action
        }

        binding.btnSettings.setOnClickListener {
            // Handle settings action
        }

        binding.btnLogout.setOnClickListener {
            requireActivity().finish()
            navigator.navigateToAuth(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}