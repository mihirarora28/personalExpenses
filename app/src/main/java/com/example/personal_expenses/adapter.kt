package com.example.personal_expenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class adapter1(var mutableList: MutableList<expense>) : BaseAdapter(){
    override fun getCount(): Int {
        return mutableList.size
    }

    override fun getItem(position: Int): Any {
        return mutableList[position]
    }

    override fun getItemId(position: Int): Long {
       return mutableList[position].Product.hashCode().toLong();
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val xx = LayoutInflater.from(parent!!.context).inflate(R.layout.items, parent, false);
        val c1  = xx.findViewById<TextView>(R.id.txt1)
       val c2 =  xx.findViewById<TextView>(R.id.txt2)
        c1.text = mutableList[position].Product
        c2.text = mutableList[position].Value.toString()
        return xx;
    }
}