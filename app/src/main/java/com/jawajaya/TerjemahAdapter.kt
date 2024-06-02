package com.jawajaya.Database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jawajaya.R

class TerjemahAdapter(private val context: Context, private var dataList: ArrayList<TerjemahData>) :
    RecyclerView.Adapter<TerjemahAdapter.ViewHolder>() {

    private var filteredDataList: ArrayList<TerjemahData> = ArrayList(dataList)
    private var listener: OnClickListener? = null

    interface OnClickListener {
        fun onItemClick(item: TerjemahData)
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)

        init {
            // Menambahkan OnClickListener pada setiap item
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = filteredDataList[position]
                    listener?.onItemClick(currentItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = filteredDataList[position]
        holder.textView.text = currentItem.jawa ?: "N/A"
    }

    override fun getItemCount(): Int {
        return filteredDataList.size
    }

    fun setData(data: ArrayList<TerjemahData>) {
        dataList = data
        filteredDataList.clear()
        filteredDataList.addAll(data)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredDataList = if (query.isEmpty()) {
            ArrayList(dataList)
        } else {
            val filteredList = ArrayList<TerjemahData>()
            for (item in dataList) {
                if (item.jawa?.contains(query, true) == true) {
                    filteredList.add(item)
                }
            }
            filteredList
        }
        notifyDataSetChanged()
    }
}
