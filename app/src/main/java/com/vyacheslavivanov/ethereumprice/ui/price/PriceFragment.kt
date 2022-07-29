package com.vyacheslavivanov.ethereumprice.ui.price

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vyacheslavivanov.ethereumprice.databinding.FragmentPriceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceFragment : Fragment() {
    private lateinit var binding: FragmentPriceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPriceBinding.inflate(inflater, container, false)

        return binding.root
    }
}
