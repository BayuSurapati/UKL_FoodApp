package com.bayusurapati.ukl_food.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bayusurapati.ukl_food.R
import com.bayusurapati.ukl_food.models.Hours
import com.bayusurapati.ukl_food.models.RestaurentModel
import com.bumptech.glide.Glide
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class RestaurentListAdapter(val restaurentList: List<RestaurentModel?>?, val clickListener: RestaurentListClickListener): RecyclerView.Adapter<RestaurentListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurentListAdapter.MyViewHolder{
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_restaurent_list_row, parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurentListAdapter.MyViewHolder, position: Int){
        holder.bind(restaurentList?.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(restaurentList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return restaurentList?.size!!
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        val thumbnailImage: ImageView = view.findViewById(R.id.thumbnailImage)

        val tvRestaurentName: TextView = view.findViewById(R.id.tvRestaurentName)
        val tvRestaurentAddress: TextView = view.findViewById(R.id.tvRestaurentAddress)
        val tvRestaurentHours: TextView = view.findViewById(R.id.tvRestaurentHours)

        fun bind(restaurentModel: RestaurentModel?){
            tvRestaurentName.text = restaurentModel?.name
            tvRestaurentAddress.text = "Address:"+restaurentModel?.address
            tvRestaurentHours.text = "Today's Hours:" + getTodaysHours(restaurentModel?.hours!!)

            Glide.with(thumbnailImage)
                .load(restaurentModel?.image)
                .into(thumbnailImage)
        }
    }

    private fun getTodaysHours(hours: Hours): String?{
        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.time
        val day: String = SimpleDateFormat("EEEE",Locale.ENGLISH).format(date.time)
        return when(day){
            "Sunday" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Thursday" -> hours.Thursday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday

        }
    }

    interface RestaurentListClickListener{
        fun onItemClick(restaurentModel: RestaurentModel)
    }
}