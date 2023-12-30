package com.mahmoud.android.tobuy.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahmoud.android.tobuy.arch.ToBuyViewModel
import com.mahmoud.android.tobuy.databinding.BottomSheetSortOrderBinding

class SortOrderBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetSortOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToBuyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetSortOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = BottomSheetEpoxyController(ToBuyViewModel.HomeViewState.Sort.values()) {
            viewModel.currentSort = it
            dismiss()
        }

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}