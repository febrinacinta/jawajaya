package com.jawajaya.Database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jawajaya.Database.WayangData
import com.jawajaya.R

class WayangAdapter(private val context: Context, private var dataList: ArrayList<WayangData>) :
    RecyclerView.Adapter<WayangAdapter.ViewHolder>(), Filterable {

    private var filteredDataList: ArrayList<WayangData> = ArrayList(dataList)
    private var listener: OnClickListener? = null

    interface OnClickListener {
        fun onItemClick(item: WayangData)
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewWayangName)

        init {
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wayang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = filteredDataList[position]
        holder.textViewName.text = currentItem.tokohWayang
    }

    override fun getItemCount(): Int {
        return filteredDataList.size
    }

    fun setData(data: ArrayList<WayangData>) {
        dataList = data
        filteredDataList.clear()
        filteredDataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) {
                    filteredDataList = dataList
                } else {
                    val filteredList = ArrayList<WayangData>()
                    dataList.filter { it.tokohWayang.contains(charString, true) }
                        .forEach { filteredList.add(it) }
                    filteredDataList = filteredList
                }
                return FilterResults().apply { values = filteredDataList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<WayangData>
                notifyDataSetChanged()
            }
        }
    }
}
