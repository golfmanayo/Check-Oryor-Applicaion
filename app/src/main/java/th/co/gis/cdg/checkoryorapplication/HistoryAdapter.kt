package th.co.gis.cdg.checkoryorapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.view.*
import kotlinx.android.synthetic.main.activity_history.view.historyListView
import kotlinx.android.synthetic.main.adapter_history.view.*
import th.co.gis.cdg.checkoryorapplication.database.DatabaseManager
import th.co.gis.cdg.checkoryorapplication.model.Oryor

class HistoryAdapter(val context: Context, private var data: MutableList<Oryor>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun getItemCount(): Int = data.size
    var callback : HistoryAdapterListener? = null

    fun setData(mdata: MutableList<Oryor>){
        this.data = mdata
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.tvlcnno.text = data[position].lcnno
//        holder.tvAddr.text = data[position].Addr
//        holder.tvIDA.text = data[position].IDA
//        holder.tvNewCode.text = data[position].NewCode
        holder.tvcncnm.text = data[position].cncnm
        holder.tvLicen.text = data[position].licen
//        holder.tvProducEng.text = data[position].produceng
        holder.tvProducTh.text = data[position].productha
        holder.tvthanm.text = data[position].thanm
//        holder.tvType.text = data[position].type
//        holder.tvTypeAllow.text = data[position].typeallow
//        holder.tvTypePro.text = data[position].typepro
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_history, parent, false)
        return HistoryViewHolder(view, this)
    }

    inner class HistoryViewHolder(itemView: View, private val adapter: HistoryAdapter) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvlcnno: TextView = itemView.findViewById(R.id.tvlcnno)
//        val tvAddr: TextView = itemView.findViewById(R.id.tvAddr)
//        val tvIDA: TextView = itemView.findViewById(R.id.tvIDA)
//        val tvNewCode: TextView = itemView.findViewById(R.id.tvNewCode)
        val tvcncnm: TextView = itemView.findViewById(R.id.tvcncnm)
        val tvLicen: TextView = itemView.findViewById(R.id.tvLicen)
//        val tvProducEng: TextView = itemView.findViewById(R.id.tvProducEng)
        val tvProducTh: TextView = itemView.findViewById(R.id.tvProducTh)
        val tvthanm: TextView = itemView.findViewById(R.id.tvthanm)
//        val tvType: TextView = itemView.findViewById(R.id.tvType)
//        val tvTypeAllow: TextView = itemView.findViewById(R.id.tvTypeAllow)
//        val tvTypePro: TextView = itemView.findViewById(R.id.tvTypePro)
        val delete_button: ImageButton = itemView.findViewById(R.id.delete_button)

        init {
            delete_button.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {
                delete_button -> {
                    DatabaseManager.getInstance(context).deleteUpload(data[adapterPosition].UPLOAD_ID.toString())
                        .subscribe({
                            Log.i("Success","delete")
                            data.removeAt(adapterPosition)
                            adapter.notifyDataSetChanged()
                        },{
                            Log.i("Error","Error")
                        })
                }
                itemView->{
                    adapter.callback?.onItemClick(data[adapterPosition])
                }
            }
        }
    }
    interface HistoryAdapterListener{
        fun onItemClick(oryor: Oryor)
    }
}