package com.changeyourself.cryptocurrency

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_deatail.*

class CoinDeatailActivity : AppCompatActivity() {
    private lateinit var viewModule:CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_deatail)
        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)){
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModule = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]
        if (fromSymbol != null) {
            viewModule.getDeatailInfo(fromSymbol).observe(this, Observer {
                tvPrice.text = it.price.toString()
                tvMinPrice.text = it.lowday.toString()
                tvMaxPrice.text = it.highday.toString()
                tvLastMarket.text = it.lastmarket.toString()
                tvLastUpdate.text = it.getFormatedTime()
                tvFromSymbol.text = it.fromsymbol
                tvToSymbol.text = it.tosymbol
                Picasso.get().load(it.getFullImageURL()).into(ivLogoCoin)
            })
        }
    }
    companion object{
        const val EXTRA_FROM_SYMBOL="fSym"
        fun newIntent(context: Context,fromsymbol:String):Intent{
         val intent=Intent(context,CoinDeatailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL,fromsymbol)
            return intent
        }
    }
}