package com.bayusurapati.ukl_food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayusurapati.ukl_food.adapter.PlaceYourOrderAdapter
import com.bayusurapati.ukl_food.models.RestaurentModel
import kotlinx.android.synthetic.main.activity_place_your_order.*

class PlaceYourOrderActivity : AppCompatActivity() {

    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    var isDeliveryOn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val restaurentModel: RestaurentModel? = intent.getParcelableExtra("RestaurentModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurentModel?.name)
        actionBar?.setSubtitle(restaurentModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val spinnerId = findViewById<Spinner>(R.id.inputCardPin)
        val nomorMeja = arrayOf("Meja 1", "Meja 2", "Meja 3", "Meja 4")
        val arrayAdap = ArrayAdapter(this@PlaceYourOrderActivity, android.R.layout.simple_spinner_dropdown_item,nomorMeja)

        spinnerId.adapter = arrayAdap

        spinnerId?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@PlaceYourOrderActivity, "Anda Memilih Meja ${nomorMeja[p2]}", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@PlaceYourOrderActivity, "Anda tidak memilih meja", Toast.LENGTH_LONG).show()
            }
        }

        buttonTambahPesanan.setOnClickListener {
            onPlaceOrderButtonClick(restaurentModel)
        }

        switchDelivery?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                inputAddress.visibility = View.VISIBLE
                inputCity.visibility = View.VISIBLE
                inputState.visibility = View.VISIBLE
                inputZip.visibility = View.VISIBLE
                tvDeliveryCharge.visibility = View.VISIBLE
                tvDeliveryChargeAmount.visibility = View.VISIBLE
                isDeliveryOn = true
                calculateTotalAmount(restaurentModel)
            } else {
                inputAddress.visibility = View.GONE
                inputCity.visibility = View.GONE
                inputState.visibility = View.GONE
                inputZip.visibility = View.GONE
                tvDeliveryCharge.visibility = View.GONE
                tvDeliveryChargeAmount.visibility = View.GONE
                isDeliveryOn = false
                calculateTotalAmount(restaurentModel)
            }
        }

        initRecyclerView(restaurentModel)
        calculateTotalAmount(restaurentModel)
    }

    private fun initRecyclerView(restaurentModel: RestaurentModel?) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurentModel?.menus)
        cartItemsRecyclerView.adapter = placeYourOrderAdapter
    }

    private fun calculateTotalAmount(restaurentModel: RestaurentModel?) {
        var subTotalAmount = 0.0
        for (menu in restaurentModel?.menus!!) {
            subTotalAmount += menu?.price!! * menu?.totalInCart!!
        }
        tvSubtotalAmount.text = "Rp." + String.format("%.2f", subTotalAmount)
        if (isDeliveryOn) {
            tvDeliveryChargeAmount.text = "Rp." + String.format(
                "%.2f",
                subTotalAmount,
                restaurentModel.delivery_charge?.toDouble()
            )
            subTotalAmount += restaurentModel?.delivery_charge?.toDouble()!!
        }
        tvTotalAmount.text = "Rp." + String.format("%.2f", subTotalAmount)
    }

    private fun onPlaceOrderButtonClick(restaurentModel: RestaurentModel?) {
        if (TextUtils.isEmpty(inputName.text.toString())) {
            inputName.error = "Masukkan Nama Anda"
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())) {
            inputAddress.error = "Masukkan Alamat Anda"
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString())) {
            inputCity.error = "Masukkan Kota Anda"
            return
        } else if (isDeliveryOn && TextUtils.isEmpty(inputZip.text.toString())) {
            inputZip.error = "Masukkan Kode Daerah Anda"
            return
        } else if (TextUtils.isEmpty(inputCardNumber.text.toString())) {
            inputCardNumber.error = "Masukkan Nomer Kartu Kredit Anda"
            return
        } else if (TextUtils.isEmpty(inputCardExpiry.text.toString())) {
            inputCardExpiry.error = "Masukkan Batas Pakai Kartu Anda"
            return
        }
        val intent = Intent(this@PlaceYourOrderActivity, SuksesOrderActivity::class.java)
        intent.putExtra("RestaurentModel",restaurentModel)
        startActivityForResult(intent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000){
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else->{}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}