package adapter

import Pojo.CoinPriceInfo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.changeyourself.cryptocurrency.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*
class CoinOnfoAdapter(private val context:Context):RecyclerView.Adapter<CoinOnfoAdapter.CoinViewHolder>() {
   interface OnCoinCklickListener{
       fun onCoinCklick(coinPriceInfo: CoinPriceInfo)
   }
    var onCoinCklickListener:OnCoinCklickListener?=null
    var coininfoList:List<CoinPriceInfo> = listOf()
    set(value){
        field=value
        notifyDataSetChanged()
    }
    inner class CoinViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
    val imageviewlogo=itemview.ivLogoCoin
    val tvSymbols=itemview.tvSymbols
    val tvPrice=itemview.tvPrice
    val tvLastUpdate=itemview.tvLastUpdate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info,parent,false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
      val coin =coininfoList.get(position)
        with(holder) {
            with(coin) {
                val symblstemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdatetemplate = context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symblstemplate, fromsymbol,tosymbol)
                tvPrice.text = price.toString()
                tvLastUpdate.text = String.format(lastUpdatetemplate,getFormatedTime())
                Picasso.get().load(getFullImageURL()).into(imageviewlogo)
               itemView.setOnClickListener {
                    onCoinCklickListener?.onCoinCklick(this)
                }
            }
        }

        }
    override fun getItemCount(): Int {
        return coininfoList.size
    }
}