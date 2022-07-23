package com.finder.android.mbti.screen.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.finder.android.mbti.CommonFragment
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.FragmentImageListBinding
import com.finder.android.mbti.list.image.ImageDetailViewPagerAdapter
import java.lang.Exception

class ImagePageFragment: CommonFragment<FragmentImageListBinding>(R.layout.fragment_image_list), View.OnClickListener {

    private val args : ImagePageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            context?.let { context ->
                binding.imageCountView.text = "${args.position+1}/${args.images.size}"
                binding.imageBodyPager.adapter = ImageDetailViewPagerAdapter(context, args.images)
                binding.imageBodyPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        binding.imageCountView.text = "${position+1}/${args.images.size}"
                    }
                })
                binding.imageBodyPager.post {
                    binding.imageBodyPager.currentItem = args.position
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            navPopStack()
        }
    }
    override fun eventListenerSetting() {
        binding.closeButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.closeButton -> navPopStack()
        }
    }

}