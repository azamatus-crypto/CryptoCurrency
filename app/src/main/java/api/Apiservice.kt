package api

import Pojo.CoinInfoListOfData
import Pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Apiservice {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(@Query(Query_PARAM_APIKEY)apkey:String="2517d46194affc28011f7466c3ea2c37671209810fb0e0ed0b382ffaec2d5aef"
    ,@Query(Query_PARAM_LIMIT)limit:Int=10,@Query(Query_PARAM_TY_SYMBOL)tysymbol:String= CURRENCY): Single<CoinInfoListOfData>
    @GET("pricemultifull")
    fun getFullPriceList(@Query(Query_PARAM_APIKEY)apkey:String="2517d46194affc28011f7466c3ea2c37671209810fb0e0ed0b382ffaec2d5aef"
    ,@Query(Query_PARAM_TY_SYMBOLs)tsymbol:String= CURRENCY,
                         @Query(Query_PARAM_From_SYMBOLs)fromsymbol:String):Single<CoinPriceInfoRawData>
    companion object{
        private const val Query_PARAM_LIMIT="limit"
        private const val Query_PARAM_TY_SYMBOL="tsym"
        private const val Query_PARAM_TY_SYMBOLs="tsyms"
        private const val Query_PARAM_From_SYMBOLs="fsyms"
        private const val Query_PARAM_APIKEY="api_key"
        private const val CURRENCY="USD"
    }
}