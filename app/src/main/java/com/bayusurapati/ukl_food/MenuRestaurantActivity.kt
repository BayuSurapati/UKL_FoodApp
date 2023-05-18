package com.bayusurapati.ukl_food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.bayusurapati.ukl_food.adapter.MenuListAdapter
import com.bayusurapati.ukl_food.models.Menus
import com.bayusurapati.ukl_food.models.RestaurentModel
import kotlinx.android.synthetic.main.activity_menu_restaurant.*

class MenuRestaurantActivity : AppCompatActivity(), MenuListAdapter.MenulistClickListener {

    private var itemsInTheCartList: MutableList<Menus?>? = null
    private var totalItemInCartCount = 0
    private var menuList: List<Menus?>? = null
    private var menuListAdapter: MenuListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_restaurant)

        val restaurentModel = intent?.getParcelableExtra<RestaurentModel>("RestaurentModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurentModel?.name)
        actionBar?.setSubtitle(restaurentModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuList = restaurentModel?.menus

        initRecyclerView(menuList)
        checkoutButton.setOnClickListener {
            if(itemsInTheCartList != null && itemsInTheCartList!!.size <= 0){
                Toast.makeText(this@MenuRestaurantActivity, "Tambahkan item di Keranjang", Toast.LENGTH_LONG).show()
            }else{
                restaurentModel?.menus = itemsInTheCartList
                val intent = Intent(this@MenuRestaurantActivity, PlaceYourOrderActivity::class.java)
                intent.putExtra("RestaurentModel",restaurentModel)
                startActivityForResult(intent, 1000)
            }
        }
    }

    private fun initRecyclerView(menus: List<Menus?>?){
        menuRecycler.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuListAdapter(menus, this)
        menuRecycler.adapter = menuListAdapter
    }

    override fun addToCartClickListener(menu: Menus) {
        if(itemsInTheCartList == null){
            itemsInTheCartList = ArrayList()
        }
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for(menu in itemsInTheCartList!!){
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        checkoutButton.text = "Checkout ("+totalItemInCartCount+") Items"
    }

    override fun updateCartClickListener(menu: Menus) {
        val index = itemsInTheCartList!!.indexOf(menu)
        itemsInTheCartList?.removeAt(index)
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for(menu in itemsInTheCartList!!){
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        checkoutButton.text = "Checkout ("+totalItemInCartCount+") Items"
    }

    override fun removeFromCartClickListener(menu: Menus) {
        if(itemsInTheCartList!!.contains(menu)){
            itemsInTheCartList?.remove(menu)
            totalItemInCartCount = 0

            for(menu in itemsInTheCartList!!){
                totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
            }
            checkoutButton.text = "Checkout ("+totalItemInCartCount+") Items"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == RESULT_OK){
            finish()
        }
    }
}