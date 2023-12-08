package com.mahmoud.android.tobuy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyTouchHelper
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.database.entity.ItemEntity
import com.mahmoud.android.tobuy.databinding.FragmentHomeBinding
import com.mahmoud.android.tobuy.ui.BaseFragment

class HomeFragment : BaseFragment(), ItemEntityInterface {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        val controller = HomeEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner){ itemEntityList ->
            controller.itemEntityList = itemEntityList as ArrayList<ItemEntity>
        }

        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(HomeEpoxyController.ItemEntityEpoxyModel::class.java)
            .andCallbacks(object:
            EpoxyTouchHelper.SwipeCallbacks<HomeEpoxyController.ItemEntityEpoxyModel>(){
                override fun onSwipeCompleted(
                    model: HomeEpoxyController.ItemEntityEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val itemThatWasRemoved = model?.itemEntity ?: return
                    sharedViewModel.deleteItem(itemThatWasRemoved)
                }

            })
    }

    override fun onResume() {
        super.onResume()

        mainActivity.hideKeyboard(requireView())
    }

    override fun onBumpPriority(itemEntity: ItemEntity) {
        val currentPriority = itemEntity.priority
        var newPriority = currentPriority + 1
        if(newPriority > 3){
            newPriority = 1
        }

        val updatedItemEntity = itemEntity.copy(priority = newPriority)
        sharedViewModel.updateItem(updatedItemEntity)
    }

    override fun onItemSelected(itemEntity: ItemEntity) {
        val navDirections = HomeFragmentDirections.actionHomeFragmentToAddItemEntityFragment(itemEntity.id)
        navigateViaNavGraph(navDirections)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}