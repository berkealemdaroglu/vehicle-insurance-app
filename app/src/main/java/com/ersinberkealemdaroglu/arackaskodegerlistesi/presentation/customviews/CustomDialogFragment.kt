package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.customviews

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.DialogCustomBinding

class CustomDialogFragment : DialogFragment() {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    var onPositiveButtonClick: (() -> Unit)? = null
    var onNegativeButtonClick: (() -> Unit)? = null
    var setTitleText: ((String) -> String)? = null
    var setMessageText: ((String?) -> String?)? = null
    var setPositiveButtonText: ((String) -> String)? = null
    var setNegativeButtonText: ((String) -> String)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.CustomDialogTheme)

        isCancelable = false

        binding.apply {
            setTitleText?.let {
                dialogTitle.text = it("")
            }

            setMessageText?.let {
                dialogMessage.text = it("")
            }
            setPositiveButtonText?.let {
                positiveButton.text = it("")
            }
            setNegativeButtonText?.let {
                negativeButton.text = it("")
            }
            positiveButton.setOnClickListener {
                onPositiveButtonClick?.invoke()
                dismiss()
            }

            negativeButton.setOnClickListener {
                onNegativeButtonClick?.invoke()
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val params = window.attributes
            val metrics = resources.displayMetrics
            val screenWidth = metrics.widthPixels
            val dialogWidth = screenWidth - (2 * resources.getDimensionPixelSize(R.dimen.margin_32dp))
            params.width = dialogWidth
            window.attributes = params

            window.setWindowAnimations(R.style.DialogAnimation)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}