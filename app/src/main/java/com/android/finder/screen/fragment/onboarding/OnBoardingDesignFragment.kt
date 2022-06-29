package com.android.finder.screen.fragment.onboarding

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.finder.screen.CommonFragment
import com.android.finder.R
import com.android.finder.databinding.FragmentOnBoardingDesignBinding

class OnBoardingDesignFragment(val position: Int) :
    CommonFragment<FragmentOnBoardingDesignBinding>(R.layout.fragment_on_boarding_design),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onBoardingImageView.setImageResource(when(position) {
            0 -> R.drawable.ic_on_boarding_one
            1 -> R.drawable.ic_on_boarding_two
            else -> R.drawable.ic_on_boarding_three
        })
        when(position) {
            2 -> {
                binding.skipButton.visibility = View.GONE
                binding.startButton.visibility = View.VISIBLE
            }
            else -> {
                binding.skipButton.visibility = View.VISIBLE
                binding.startButton.visibility = View.GONE
            }
        }
    }
    override fun eventListenerSetting() {
        binding.skipButton.setOnClickListener(this)
        binding.startButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
    }
}