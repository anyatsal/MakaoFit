package com.makao.makaofit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.makao.makaofit.R
import kotlinx.android.synthetic.main.tutorial_adapter_layout.view.*
import java.util.*

/**
 * OnBoarding screen.
 */
class TutorialAdapter() : PagerAdapter() {

    private var mImageUrlList = ArrayList<Int>()
    private var mCustomTextList = ArrayList<String>()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    /**
     * @return number of onBoarding screens.
     */
    override fun getCount(): Int {
        return mImageUrlList.size
    }

    /**
     * Instantiates onBoarding screen.
     *
     * @param container current view container.
     * @param position current position on onBoarding screen.
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.tutorial_adapter_layout, container, false)

        view.tutorial_tv_main.text = mCustomTextList[position]
        view.tutorial_image.setImageResource(mImageUrlList[position])

        container.addView(view)

        return view
    }

    /**
     * Removes view from view container.
     *
     * @param container current view container.
     * @param position current position on onBoarding screen.
     * @param `object` current screen.
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    /**
     * Adds screen to onBoarding screen.
     *
     * @param imagesList thw images for onBoarding screen.
     * @param customTextList the texts for onBoarding screen.
     */
    fun addItems(
        imagesList: ArrayList<Int>,
        customTextList: ArrayList<String>
    ) {
        mImageUrlList = imagesList
        mCustomTextList = customTextList
        notifyDataSetChanged()
    }
}