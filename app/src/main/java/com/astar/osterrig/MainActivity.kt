package com.astar.osterrig

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var mBtnOne: Button
    private lateinit var mBtnTwo: Button
    private lateinit var mBtnThree: Button
    private lateinit var mBtnFour: Button

    private lateinit var navController: NavController

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.btn_one -> {
                if (navController.currentDestination?.label != "fragment_color") {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_colorFragment)
                }
            }
            R.id.btn_two -> {
                if (navController.currentDestination?.label != "fragment_cct_color") {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_cctColorFragment)
                }
            }
            R.id.btn_three -> {

            }
            R.id.btn_four -> {
                if (navController.currentDestination?.label != "fragment_functions") {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_functionsFragment)
                }
            }
        }
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnOne = findViewById(R.id.btn_one)
        mBtnTwo = findViewById(R.id.btn_two)
        mBtnThree = findViewById(R.id.btn_three)
        mBtnFour = findViewById(R.id.btn_four)

        mBtnOne.setOnClickListener(onClickListener)
        mBtnTwo.setOnClickListener(onClickListener)
        mBtnThree.setOnClickListener(onClickListener)
        mBtnFour.setOnClickListener(onClickListener)

        navController = findNavController(R.id.nav_host_fragment)

        mBtnOne.text = "RGB"
        mBtnTwo.text = "FTP"
        mBtnFour.text = "FNC"
        mBtnThree.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {

        val activeButtonColor =
            ResourcesCompat.getColor(resources, R.color.button_default_active, theme)
        val buttonColor = ResourcesCompat.getColor(resources, R.color.button_default, theme)

        mBtnOne.setBackgroundResource(R.color.button_default)
        mBtnOne.backgroundTintList = ColorStateList.valueOf(buttonColor)
        mBtnTwo.setBackgroundResource(R.color.button_default)
        mBtnTwo.backgroundTintList = ColorStateList.valueOf(buttonColor)
        mBtnThree.setBackgroundResource(R.color.button_default)
        mBtnThree.backgroundTintList = ColorStateList.valueOf(buttonColor)
        mBtnFour.setBackgroundResource(R.color.button_default)
        mBtnFour.backgroundTintList = ColorStateList.valueOf(buttonColor)

        val currentDestination = findNavController(R.id.nav_host_fragment).currentDestination

        when (currentDestination?.label) {
            "fragment_color" -> {
                mBtnOne.backgroundTintList = ColorStateList.valueOf(activeButtonColor)
            }
            "fragment_cct_color" -> {
                mBtnTwo.backgroundTintList = ColorStateList.valueOf(activeButtonColor)
            }
            "fragment_functions" -> {
                mBtnFour.backgroundTintList = ColorStateList.valueOf(activeButtonColor)
            }
        }
    }

}