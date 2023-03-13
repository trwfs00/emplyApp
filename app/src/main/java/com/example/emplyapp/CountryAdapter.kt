package com.example.emplyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.emplyapp.R
import java.util.*
import kotlin.collections.ArrayList

class CountryAdapter(
    var CountryList : List<CountryClass>,
    var mListener : onItemClickListener
    ) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item_layout, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.titleCountry.text = CountryList[position].nicename

        holder.itemView.setOnClickListener {
            mListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return CountryList.size
    }

    class CountryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleCountry : TextView = itemView.findViewById(R.id.txtCountry)
    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }

}