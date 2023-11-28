package com.mahmoud.android.tobuy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mahmoud.android.tobuy.arch.ToBuyViewModel
import com.mahmoud.android.tobuy.database.AppDatabase

abstract class BaseFragment : Fragment() {
    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: ToBuyViewModel by activityViewModels()
}