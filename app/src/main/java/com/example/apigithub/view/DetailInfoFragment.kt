package com.example.apigithub.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.apigithub.R
import com.example.apigithub.databinding.DetailInfoFragmentBinding

class DetailInfoFragment: Fragment(R.layout.detail_info_fragment) {

    private lateinit var binding: DetailInfoFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailInfoFragmentBinding.bind(view)
        //переход из детаилс в лист

    }
}