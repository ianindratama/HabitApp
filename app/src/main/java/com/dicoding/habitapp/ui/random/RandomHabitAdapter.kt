package com.dicoding.habitapp.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view

        private val pagerTvTitle = itemView.findViewById<TextView>(R.id.pager_tv_title)!!
        private val pagerTvStartTime = itemView.findViewById<TextView>(R.id.pager_tv_start_time)!!
        private val itemPriorityLevel = itemView.findViewById<ImageView>(R.id.item_priority_level)!!
        private val pagerTvMinutes = itemView.findViewById<TextView>(R.id.pager_tv_minutes)!!
        private val btnOpenCountDown = itemView.findViewById<Button>(R.id.btn_open_count_down)!!

        fun bind(pageType: PageType, pageData: Habit) {

            pagerTvTitle.text = pageData.title
            pagerTvStartTime.text = pageData.startTime

            itemPriorityLevel.apply {
                when (pageType) {
                    PageType.HIGH -> setImageResource(R.drawable.ic_priority_high)
                    PageType.MEDIUM -> setImageResource(R.drawable.ic_priority_medium)
                    PageType.LOW -> setImageResource(R.drawable.ic_priority_low)
                }
            }

            pagerTvMinutes.text = pageData.minutesFocus.toString()

            btnOpenCountDown.setOnClickListener {
                onClick(pageData)
            }

        }
    }
}
