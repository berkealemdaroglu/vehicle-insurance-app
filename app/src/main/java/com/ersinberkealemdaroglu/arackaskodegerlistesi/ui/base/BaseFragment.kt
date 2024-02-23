package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomSheet.HomeVehicleFilterBottomSheet
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomSheet.SelectedVehicleFilterItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.customViews.InsureProgressDialog

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel?>(
    private val inflate: (
        inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean
    ) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding
    abstract val viewModel: VM
    private var activeProgressCount = 0
    private var insureProgressDialog: InsureProgressDialog? = null

    protected abstract fun initUI(view: View)
    protected open fun createInitUI() {}
    protected open fun createPresenter() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        createInitUI()
        observeVMLoading()

        _binding = this@BaseFragment.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insureProgressDialogSetup()
        initUI(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    open fun hideKeyboard() {
        if (requireActivity().currentFocus != null) {
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun insureProgressDialogSetup() {
        insureProgressDialog = InsureProgressDialog(requireContext())
    }

    private fun observeVMLoading() {
        viewModel?.loading?.observe(viewLifecycleOwner) {
            if (it) showLoading() else hideLoading()
        }
    }

    open fun showLoading() {
        if (activeProgressCount == 0) {
            insureProgressDialog?.show()
        }
        activeProgressCount++
    }

    open fun hideLoading() {
        activeProgressCount--

        if (activeProgressCount <= 0) {
            activeProgressCount = 0
            insureProgressDialog?.dismiss()
        }
    }

    protected fun openVehicleFilterBottomSheet(selectedVehicleFilterItem: SelectedVehicleFilterItem) {
        val vehicleFilterBottomSheet = HomeVehicleFilterBottomSheet(selectedVehicleFilterItem)
        vehicleFilterBottomSheet.show(childFragmentManager, vehicleFilterBottomSheet.tag)
    }
}