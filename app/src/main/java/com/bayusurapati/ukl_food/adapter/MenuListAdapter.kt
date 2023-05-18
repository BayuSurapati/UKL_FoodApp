package com.bayusurapati.ukl_food.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bayusurapati.ukl_food.R
import com.bayusurapati.ukl_food.models.Menus
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.menu_list_row.view.*
import kotlinx.android.synthetic.main.recycler_restaurent_list_row.view.*
import org.w3c.dom.Text

class MenuListAdapter(val menuList: List<Menus?>?, val clickListener: MenulistClickListener): RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {
    override fun getItemCount(): Int {
        return if(menuList == null)return 0 else menuList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.menu_list_row,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)

    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val thumbnailImage: ImageView = view.thumbImage
        val menuName: TextView = view.menuName
        val menuPrice: TextView = view.menuPrice
        val addtoCartBtn: TextView = view.addToCartButton
        val addMoreLayout: LinearLayout = view.addMoreLayout
        val imageMinus: ImageView = view.imageMinus
        val imageAddOne: ImageView = view.imageAddOne
        val tvCount:TextView = view.tvCount

        fun bind(menus: Menus){
            menuName.text = menus?.name
            menuPrice.text = "Price: Rp. ${menus?.price}"
            addtoCartBtn.setOnClickListener {
                menus?.totalInCart = 1
                clickListener.addToCartClickListener(menus)
                addMoreLayout?.visibility = View.VISIBLE
                addtoCartBtn.visibility = View.GONE
                tvCount.text = menus?.totalInCart.toString()
            }
            imageMinus.setOnClickListener {
                var total: Int = menus.totalInCart
                total--
                if(total>0){
                    menus?.totalInCart = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = menus?.totalInCart.toString()
                }else{
                    menus.totalInCart = total
                    clickListener.removeFromCartClickListener(menus)
                    addMoreLayout.visibility = View.GONE
                    addtoCartBtn.visibility = View.VISIBLE
                }
            }
            imageAddOne.setOnClickListener {
                var total: Int = menus.totalInCart
                total++
                if(total<=10){
                    menus.totalInCart = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = total.toString()
                }

            }
            Glide.with(thumbnailImage)
                .load(menus?.url)
                .into(thumbnailImage)

        }

    }
    interface MenulistClickListener{
        fun addToCartClickListener(menu: Menus)
        fun updateCartClickListener(menu: Menus)
        fun removeFromCartClickListener(menu: Menus)
    }
}