package com.changeyourself.cryptocurrency

import Pojo.CoinPriceInfo
import Pojo.CoinPriceInfoRawData
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import api.ApiFactory
import com.google.gson.Gson
import database.AppDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(apllication:Application):AndroidViewModel(apllication) {
    private val db=AppDatabase.getInstance(apllication)
    val pricelist=db.coinpriceInfoDao().getPriceList()
     private val compositdisposible=CompositeDisposable()
    fun getDeatailInfo(fSym:String):LiveData<CoinPriceInfo>{
        return db.coinpriceInfoDao().getPriceInfoAboutCoin(fSym)
    }
    init {
        loadData()
    }
    fun loadData(){
        val disposable= ApiFactory.apiservice.getTopCoinsInfo(limit = 50)
            .map { it.data?.map {it.coinInfo?.name}?.joinToString(",") }
            .flatMap { ApiFactory.apiservice.getFullPriceList(fromsymbol = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinpriceInfoDao().insertPriceList(it)
                Log.d("Test_of_loading_Data", it.toString())
            },{
                Log.d("Test_of_loading_Data", it.message.toString())
            }
            )
          compositdisposible.add(disposable)
    }
    private fun getPriceListFromRawData(coinpriceinfoRawdata:CoinPriceInfoRawData):List<CoinPriceInfo>{
        val result=ArrayList<CoinPriceInfo>()
        val jsonObject=coinpriceinfoRawdata.coinpriceInfoJsonObject?: return result
        val coinkeyset=jsonObject.keySet()
        for(coinkey in coinkeyset){
            val currencyjson=jsonObject.getAsJsonObject(coinkey)
            val currencyKeySet=currencyjson.keySet()
            for(curencekey in currencyKeySet){
                val priceinfo=Gson().fromJson(currencyjson.getAsJsonObject(curencekey),CoinPriceInfo::class.java)
                result.add(priceinfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositdisposible.dispose()
    }
}