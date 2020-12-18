package com.astar.osterrig.ui.cct_color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.R
import com.astar.osterrig.models.CctColor

class CctColorAdapter : RecyclerView.Adapter<CctColorAdapter.ViewHolder>() {

    private val mData = mutableListOf<CctColor>()

    fun setItems(data : List<CctColor>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val container : FrameLayout
        val tvContent : TextView

        init {
            container = itemView.findViewById(R.id.item_container)
            tvContent = itemView.findViewById(R.id.tv_content)
        }

        fun bind(item: CctColor) {
            tvContent.text = item.text
            container.setBackgroundColor(item.backgroundCell)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_recycler_cct_color, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

}