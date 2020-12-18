package com.astar.osterrig.ui.cct_color

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.astar.osterrig.R
import com.astar.osterrig.databinding.FragmentCctColorBinding
import com.astar.osterrig.models.CctColor

class CctColorFragment : Fragment() {

    private lateinit var mBinding: FragmentCctColorBinding
    private val mViewModel : CctColorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cct_color, container, false)
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        val adapter = CctColorAdapter()
        mBinding.adapter = adapter

        adapter.setItems(listOf(
            CctColor(255, 100, 0, 255, Color.rgb(221, 230, 255), "8000K"),
            CctColor(255, 100, 0, 255, Color.rgb(255, 246, 237), "6000K"),
            CctColor(255, 100, 0, 255, Color.rgb(255, 239, 225), "5600K"),
            CctColor(255, 100, 0, 255, Color.rgb(255, 228, 206), "5000K"),
            CctColor(255, 100, 0, 255, Color.rgb(255, 222, 195), "4700K"),
            CctColor(255, 100, 0, 255, Color.rgb(255, 198, 151), "3700K"),
            CctColor(255, 100, 0, 255, Color.rgb(255, 170, 95), "2800K")
        ))

        initViews()

        return mBinding.root
    }

    private fun initViews() {
        mViewModel.setLightness(0)
        mBinding.sbLightness.setOnSeekBarChangeListener(mChangeListener)
        mBinding.sbTintColor.setOnSeekBarChangeListener(mChangeListener)
        mBinding.tvTintColor.setOnClickListener { mViewModel.setTintColor(50) }
    }

    private val  mChangeListener: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            when (seekBar) {
                mBinding.sbLightness -> { mViewModel.setLightness(progress) }
                mBinding.sbTintColor -> { mViewModel.setTintColor(progress) }
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            when (seekBar) {
                mBinding.sbLightness -> { mViewModel.setLightness(seekBar.progress) }
                mBinding.sbTintColor -> { mViewModel.setTintColor(seekBar.progress) }
            }
        }
    }

    companion object {
        const val TAG = "CctColorFragment"
    }
}