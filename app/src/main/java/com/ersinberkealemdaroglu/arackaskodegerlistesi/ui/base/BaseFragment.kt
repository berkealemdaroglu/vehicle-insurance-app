package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.MainActivity
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.creditcalculator.CreditCalculatorFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.detail.VehicleDetailFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.detailList.VehicleSearchListFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.HomeFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomsheet.HomeVehicleFilterBottomSheet
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomsheet.SelectedVehicleFilterItem
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.splash.SplashFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.customviews.InsureProgressDialog

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

    private val activityBinding by lazy {
        (activity as? MainActivity)
    }

    protected abstract fun initUI(view: View)
    protected open fun createInitUI() {}
    protected open fun createPresenter() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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

    override fun onResume() {
        super.onResume()
        setToolbarStateForFragments(this)
    }

    private fun setToolbarStateForFragments(fragment: Fragment) {
        when (fragment) {
            is SplashFragment -> {
                activityBinding?.hideToolbar()
            }

            is HomeFragment -> {
                activityBinding?.showToolbar()
                activityBinding?.setToolbar(
                    leftIconDrawable = R.drawable.ic_insure_app_logo,
                    title = getString(R.string.arac_kasko_deger_listesi),
                )
            }

            is VehicleDetailFragment -> {
                activityBinding?.showToolbar()
                activityBinding?.setToolbar(
                    leftIconDrawable = R.drawable.btn_back,
                    leftButtonClickListener = { findNavController().navigateUp() },
                    title = getString(R.string.arac_detay),
                )
            }

            is VehicleSearchListFragment -> {
                activityBinding?.showToolbar()
                activityBinding?.setToolbar(leftIconDrawable = R.drawable.btn_back,
                    title = getString(R.string._400_bin_tl_alti_araclar),
                    rightIconDrawable = R.drawable.ic_filter,
                    leftButtonClickListener = { findNavController().navigateUp() },
                    rightButtonClickListener = { fragment.openFilterBottomSheet() })
            }

            is CreditCalculatorFragment -> {
                activityBinding?.showToolbar()
                activityBinding?.setToolbar(leftIconDrawable = R.drawable.btn_back,
                    title = getString(R.string.kredi_hesaplama),
                    leftButtonClickListener = { findNavController().navigateUp() })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun hideKeyboard() {
        if (requireActivity().currentFocus != null) {
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
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