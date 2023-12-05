package com.mahmoud.android.tobuy.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.database.entity.ItemEntity
import com.mahmoud.android.tobuy.databinding.FragmentAddItemEntityBinding
import com.mahmoud.android.tobuy.ui.BaseFragment
import java.util.UUID

class AddItemEntityFragment : BaseFragment() {
    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveItemEntityToDatabase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){ complete ->
            if(complete){
                Toast.makeText(requireActivity(), "Item saved!", Toast.LENGTH_SHORT).show()
                binding.titleEditText.text = null
                mainActivity.showKeyboard()
                binding.titleEditText.requestFocus()

                binding.descriptionEditText.text = null
                binding.radioGroup.check(R.id.radioButtonLow)
            }
        }

        mainActivity.showKeyboard()
        binding.titleEditText.requestFocus()
    }

    override fun onPause() {
        super.onPause()

        sharedViewModel.transactionCompleteLiveData.postValue(false)
    }

    override fun onStop() {
        super.onStop()
        mainActivity.hideKeyboard(requireView())
    }

    private fun saveItemEntityToDatabase(){
        val itemTitle = binding.titleEditText.text.toString().trim()
        if(itemTitle.isEmpty()){
            binding.titleEditText.error = "* Required field"
            return
        }

        binding.titleEditText.error = null

        val itemDescription = binding.descriptionEditText.text.toString().trim()
        val itemPriority = when(binding.radioGroup.checkedRadioButtonId){
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            categoryId = ""
        )

        sharedViewModel.insertItem(itemEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}