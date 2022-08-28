package com.example.slim_fit.Adapters

import android.Manifest
import android.content.UriPermission
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.slim_fit.R
import com.example.slim_fit.database.weightMod
import java.net.URI

import android.net.Uri as Uri1

class homeAdapter(private val mList: List<weightMod>) : RecyclerView.Adapter<homeAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the weight_model view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weight_model, parent, false)

        return ViewHolder(view)
    }

    // binds the views held by ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currItem = mList[position]
        holder.photoView.setImageURI(android.net.Uri.parse(currItem.photoUri))
        holder.dateView.text = currItem.date
        holder.weightView.text = currItem.weight.toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val photoView: ImageView = itemView.findViewById(R.id.user_photo)
        val weightView: TextView = itemView.findViewById(R.id.weight_view)
        val dateView: TextView = itemView.findViewById(R.id.date_view)
    }
}