package com.astar.osterrig.ui.color_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.astar.osterrig.R
import com.astar.osterrig.databinding.FragmentColorBinding
import com.astar.osterrig.ui.base.BaseFragment
import com.astar.osterrig.ui.ftp_dialog.FtpDialog

class ColorFragment : BaseFragment() {

    private val mViewModel: ColorViewModel by viewModels()

    private lateinit var mBinding: FragmentColorBinding

    private val mFtpDialogListener = object : FtpDialog.OnFtpDialogListener {
        override fun onSelectColor(color: Int) {
            mViewModel.setColorSelectorValue(color)
        }

        override fun onOkColor(color: Int) {
            mViewModel.setColorSelectorValue(color)
        }

        override fun onCancelColor() {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_color, container, false)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        mBinding.previewColor.setOnClickListener {
            val ftpDialog = FtpDialog.newInstance(mBinding.colorSelector.color)
                ftpDialog.addListener(mFtpDialogListener)
                ftpDialog.show(parentFragmentManager, "ftp_dialog")
        }

        mBinding.colorSelector.subscribe { color, _, _ ->
            mViewModel.setPreviewColor(color)
        }

        mBinding.colorPreset0.setOnClickListener(onClickListener)
        mBinding.colorPreset1.setOnClickListener(onClickListener)
        mBinding.colorPreset2.setOnClickListener(onClickListener)
        mBinding.colorPreset3.setOnClickListener(onClickListener)
        mBinding.colorPreset4.setOnClickListener(onClickListener)
        mBinding.colorPreset5.setOnClickListener(onClickListener)

        mBinding.colorPreset0.setOnLongClickListener(onLongClickListener)
        mBinding.colorPreset1.setOnLongClickListener(onLongClickListener)
        mBinding.colorPreset2.setOnLongClickListener(onLongClickListener)
        mBinding.colorPreset3.setOnLongClickListener(onLongClickListener)
        mBinding.colorPreset4.setOnLongClickListener(onLongClickListener)
        mBinding.colorPreset5.setOnLongClickListener(onLongClickListener)

        mViewModel.setLightness(mBinding.sbLightness.progress)

        mBinding.sbLightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mViewModel.setLightness(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mViewModel.setLightness(seekBar.progress)
            }
        })

        addObservers()

        return mBinding.root
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.color_preset_0 -> {
                mViewModel.colorPresetButton0.value?.let { mViewModel.setColorSelectorValue(it) }
            }
            R.id.color_preset_1 -> {
                mViewModel.colorPresetButton1.value?.let { mViewModel.setColorSelectorValue(it) }
            }
            R.id.color_preset_2 -> {
                mViewModel.colorPresetButton2.value?.let { mViewModel.setColorSelectorValue(it) }
            }
            R.id.color_preset_3 -> {
                mViewModel.colorPresetButton3.value?.let { mViewModel.setColorSelectorValue(it) }
            }
            R.id.color_preset_4 -> {
                mViewModel.colorPresetButton4.value?.let { mViewModel.setColorSelectorValue(it) }
            }
            R.id.color_preset_5 -> {
                mViewModel.colorPresetButton5.value?.let { mViewModel.setColorSelectorValue(it) }
            }
        }
    }

    private val onLongClickListener: View.OnLongClickListener = View.OnLongClickListener { view ->
        when (view.id) {
            R.id.color_preset_0 -> {
                mViewModel.setColorPresetButton(0, mBinding.colorSelector.color)
            }
            R.id.color_preset_1 -> {
                mViewModel.setColorPresetButton(1, mBinding.colorSelector.color)
            }
            R.id.color_preset_2 -> {
                mViewModel.setColorPresetButton(2, mBinding.colorSelector.color)
            }
            R.id.color_preset_3 -> {
                mViewModel.setColorPresetButton(3, mBinding.colorSelector.color)
            }
            R.id.color_preset_4 -> {
                mViewModel.setColorPresetButton(4, mBinding.colorSelector.color)
            }
            R.id.color_preset_5 -> {
                mViewModel.setColorPresetButton(5, mBinding.colorSelector.color)
            }
        }
        true
    }

    private fun addObservers() {
        mViewModel.currentColorColorSelector.observe(viewLifecycleOwner, {
            mBinding.colorSelector.setColor(it, false)
        })
    }
}