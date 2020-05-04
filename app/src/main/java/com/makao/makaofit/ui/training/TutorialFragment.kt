package com.makao.makaofit.ui.training

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.makao.makaofit.R
import com.makao.makaofit.ui.activity.AppActivity
import com.makao.makaofit.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_tutorial.*
import kotlinx.android.synthetic.main.fragment_tutorial.view.*

class TutorialFragment : Fragment(R.layout.fragment_tutorial) {

    private lateinit var currentView: View
    private var adapter =
        TutorialAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentView = view

        checkAndStartTutor(view)

        skip_button.setOnClickListener {
            try {
                val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                intent.data = requireActivity().intent?.data
                startActivity(intent)
                requireActivity().finish()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    private fun checkAndStartTutor(view: View) {
        if (!requireContext().getSharedPreferences("com.makao.makaofit", Context.MODE_PRIVATE)
                .getBoolean("AUTHENTICATED", false)
        ) {
            view.view_pager.adapter = adapter
            adapter.notifyDataSetChanged()
            view.dots_indicator.setupWithViewPager(view.view_pager, true)

            setTutorial()
        } else {
            val intent = Intent(requireActivity(), AppActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }
            intent.data = requireActivity().intent?.data
            startActivity(intent)
            requireActivity().finish()
        }
    }

    /**
     * Creates onBoarding screens.
     */
    private fun setTutorial() {

        val imagesList = ArrayList<Int>()
        val overlayList = ArrayList<String>()

        imagesList.add(R.drawable.img1)
        imagesList.add(R.drawable.img2)
        imagesList.add(R.drawable.img3)
        imagesList.add(R.drawable.img4)

        overlayList.add(
            "1"
        )
        overlayList.add(
            "2"
        )
        overlayList.add(
            "3"
        )
        overlayList.add(
            "4"
        )

        adapter.addItems(imagesList, overlayList)
    }
}