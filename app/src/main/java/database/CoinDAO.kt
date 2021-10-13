package database

import Pojo.CoinPriceInfo
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinDAO {
    @Query("SELECT * FROM full_price_list ORDER BY lastupdate DESC")
    fun getPriceList():LiveData<List<CoinPriceInfo>>
    @Query("SELECT * FROM full_price_list WHERE fromsymbol== :fsym LIMIT 1")
    fun getPriceInfoAboutCoin(fsym:String):LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(pricelist:List<CoinPriceInfo>)
}