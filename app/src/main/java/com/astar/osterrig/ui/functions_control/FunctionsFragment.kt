package com.astar.osterrig.ui.functions_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.astar.osterrig.R
import com.astar.osterrig.databinding.FragmentFunctionsBinding


class FunctionsFragment : Fragment() {

    private lateinit var binding: FragmentFunctionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_functions, container, false)

        return binding.root
    }

    companion object {

    }
}