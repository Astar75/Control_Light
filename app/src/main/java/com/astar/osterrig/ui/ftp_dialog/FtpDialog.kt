package com.astar.osterrig.ui.ftp_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.astar.osterrig.R

class FtpDialog : DialogFragment() {

    private lateinit var viewDialog: View

    private lateinit var previewColor: View
    private lateinit var tvColorPreview: TextView
    private lateinit var sbRedChannel: SeekBar
    private lateinit var sbGreenChannel: SeekBar
    private lateinit var sbBlueChannel: SeekBar

    private lateinit var tvRedProgress: TextView
    private lateinit var tvGreenProgress: TextView
    private lateinit var tvBlueProgress: TextView

    private lateinit var btnCancel: Button
    private lateinit var btnOk: Button

    private var mListener: OnFtpDialogListener? = null

    private var red: Int = 0
    private var green: Int = 0
    private var blue: Int = 0

    private val onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener =
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (seekBar.id) {
                    R.id.sb_red_channel -> red = progress
                    R.id.sb_green_channel -> green = progress
                    R.id.sb_blue_channel -> blue = progress
                }
                mListener?.onSelectColor(Color.rgb(red, green, blue))
                updateUI()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                when (seekBar.id) {
                    R.id.sb_red_channel -> red = seekBar.progress
                    R.id.sb_green_channel -> green = seekBar.progress
                    R.id.sb_blue_channel -> blue = seekBar.progress
                }
                mListener?.onSelectColor(Color.rgb(red, green, blue))
                updateUI()
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewDialog = layoutInflater.inflate(R.layout.ftp_dialog, null)

        previewColor = viewDialog.findViewById(R.id.preview_color)
        tvColorPreview = viewDialog.findViewById(R.id.tv_color_preview)

        sbRedChannel = viewDialog.findViewById(R.id.sb_red_channel)
        sbGreenChannel = viewDialog.findViewById(R.id.sb_green_channel)
        sbBlueChannel = viewDialog.findViewById(R.id.sb_blue_channel)

        /*val redProgressDrawable = sbRedChannel.progressDrawable as LayerDrawable
        val redGradientDrawable = redProgressDrawable.findDrawableByLayerId(R.id.progress_seek_bar_drawable) as GradientDrawable
        redGradientDrawable.mutate()
        redGradientDrawable.colors = intArrayOf(Color.RED, Color.BLACK)
        //redProgressDrawable.setDrawableByLayerId(R.id.progress_seek_bar_drawable, redGradientDrawable)
        sbRedChannel.progressDrawable = redProgressDrawable*/

        /*Log.e("DIALOG", "onCreateDialog: ${redGradientDrawable.current.alpha}")

        when (redGradientDrawable) {
            is GradientDrawable -> {
                println("Gradient drawable")
            }
            is ColorDrawable -> {
                println("Color drawable")
            }
            is LayerDrawable -> {
                println("Layer drawable")
            }
            is DrawableContainer -> {
                println("Drawable Container")
            }
            is ScaleDrawable -> {
                println("Scale Drawable")
            }
            is ShapeDrawable -> {
                println("Shape Drawable")
            }
            is VectorDrawable -> {
                println("Vector Drawable")
            }
            else -> {
                println("ШО ТО ХУЙНЯ, ШО ЭТО ХУЙНЯ...")
            }
        }*/

        tvRedProgress = viewDialog.findViewById(R.id.tv_red_progress)
        tvGreenProgress = viewDialog.findViewById(R.id.tv_green_progress)
        tvBlueProgress = viewDialog.findViewById(R.id.tv_blue_progress)

        btnCancel = viewDialog.findViewById(R.id.btn_cancel)
        btnOk = viewDialog.findViewById(R.id.btn_ok)

        btnCancel.setOnClickListener { mListener?.onCancelColor(); dismiss() }
        btnOk.setOnClickListener { mListener?.onOkColor(Color.rgb(red, green, blue)); dismiss() }

        sbRedChannel.setOnSeekBarChangeListener(onSeekBarChangeListener)
        sbGreenChannel.setOnSeekBarChangeListener(onSeekBarChangeListener)
        sbBlueChannel.setOnSeekBarChangeListener(onSeekBarChangeListener)

        return AlertDialog.Builder(requireContext()).apply {
            setTitle(null)
            setView(viewDialog)
        }.create().apply {
            setOnShowListener {
                val color = arguments?.getInt(EXTRA_COLOR, Color.BLACK)
                if (color != null) {
                    red = Color.red(color)
                    green = Color.green(color)
                    blue = Color.blue(color)
                }
                updateUI()
            }
        }
    }

    private fun updateUI() {
        sbRedChannel.progress = red
        sbGreenChannel.progress = green
        sbBlueChannel.progress = blue

        tvRedProgress.text = red.toString()
        tvGreenProgress.text = green.toString()
        tvBlueProgress.text = blue.toString()

        tvColorPreview.text = String.format("#%02X%02X%02X", red, green, blue)
        tvColorPreview.setTextColor(Color.rgb(255 - red, 255 - green, 255 - blue))
        previewColor.setBackgroundColor(Color.rgb(red, green, blue))
    }

    fun addListener(listener: OnFtpDialogListener) {
        mListener = listener
    }

    interface OnFtpDialogListener {
        fun onSelectColor(color: Int)
        fun onOkColor(color: Int)
        fun onCancelColor()
    }

    companion object {
        const val EXTRA_COLOR = "extra_color"
        fun newInstance(color: Int): FtpDialog {
            val args = Bundle()
            args.putInt(EXTRA_COLOR, color)

            val fragment = FtpDialog()
            fragment.arguments = args
            return fragment
        }
    }
}