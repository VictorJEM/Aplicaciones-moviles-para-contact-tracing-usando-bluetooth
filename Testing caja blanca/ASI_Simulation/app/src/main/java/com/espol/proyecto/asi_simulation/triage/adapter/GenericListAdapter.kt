package com.espol.proyecto.asi_simulation.triage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericListAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var data : List<T>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var clickListener : GenericClickListener<T>
    var selectedPosition = -1

    constructor(listItems: List<T>, clickListener: GenericClickListener<T>) {
        this.data = listItems
        this.clickListener = clickListener
    }

    constructor() {
        data = emptyList()
    }

    fun onItemClick(pos: Int) {
        if (pos != RecyclerView.NO_POSITION) {
            clickListener.onClick(data[pos])
            notifyItemRangeChanged(0, data.size)
            selectedPosition = pos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false), viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, data[position])
    }

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(view: View, viewType: Int):RecyclerView.ViewHolder

    internal interface Binder<T> {
        fun bind(data: T, position: Int)
    }
}

interface GenericClickListener<Any> {
    fun onClick(item: Any)
}