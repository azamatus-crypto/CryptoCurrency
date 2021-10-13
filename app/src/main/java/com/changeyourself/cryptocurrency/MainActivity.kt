package com.changeyourself.cryptocurrency

import Pojo.CoinPriceInfo
import adapter.CoinOnfoAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModule:CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter=CoinOnfoAdapter(this)
        recycklerviewmain.adapter=adapter
        adapter.onCoinCklickListener=object :CoinOnfoAdapter.OnCoinCklickListener{
            override fun onCoinCklick(coinPriceInfo: CoinPriceInfo) {
//             val intent=Intent(this@MainActivity,CoinDeatailActivity::class.java)
//                intent.putExtra(CoinDeatailActivity.EXTRA_FROM_SYMBOL,coinPriceInfo.fromsymbol)
//                startActivity(intent)
                val intent=CoinDeatailActivity.newIntent(this@MainActivity,coinPriceInfo.fromsymbol)
                startActivity(intent)
            }

        }
       viewModule = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]


        viewModule.pricelist.observe(this, Observer {
            adapter.coininfoList=it
       })

    }


}