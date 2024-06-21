package com.example.cognitivetests.recyclerView

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class InstructionAdapter(private val context: Context, private val instructions: List<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return instructions.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val textView = TextView(context)
        textView.text = instructions[position]
        textView.textSize = 20f
        textView.gravity = Gravity.CENTER
        textView.setTextColor(
            context.resources.getColor(android.R.color.black))
        container.addView(textView)
        return textView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}