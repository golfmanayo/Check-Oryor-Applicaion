package th.co.gis.cdg.checkoryorapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.view.*
import kotlinx.android.synthetic.main.adapter_history.view.*
import th.co.gis.cdg.checkoryorapplication.model.Oryor

class HistoryAdapter(val context: Context, var data: List<Oryor>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.tvlcnno.text = data[position].lcnno
        holder.tvAddr.text = data[position].Addr
        holder.tvIDA.text = data[position].IDA
        holder.tvNewCode.text = data[position].NewCode
        holder.tvcncnm.text = data[position].cncnm
        holder.tvLicen.text = data[position].licen
        holder.tvProducEng.text = data[position].produceng
        holder.tvProducTh.text = data[position].productha
        holder.tvthanm.text = data[position].thanm
        holder.tvType.text = data[position].type
        holder.tvTypeAllow.text = data[position].typeallow
        holder.tvTypePro.text = data[position].typepro
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_history, parent, false)
        return HistoryViewHolder(view, this)
    }

    inner class HistoryViewHolder(itemView: View, private val adapter: HistoryAdapter) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvlcnno: TextView = itemView.tvlcnno
        val tvAddr: TextView = itemView.tvAddr
        val tvIDA: TextView = itemView.tvIDA
        val tvNewCode: TextView = itemView.tvNewCode
        val tvcncnm: TextView = itemView.tvcncnm
        val tvLicen: TextView = itemView.tvLicen
        val tvProducEng: TextView = itemView.tvProducEng
        val tvProducTh: TextView = itemView.tvProducTh
        val tvthanm: TextView = itemView.tvthanm
        val tvType: TextView = itemView.tvType
        val tvTypeAllow: TextView = itemView.tvTypeAllow
        val tvTypePro: TextView = itemView.tvTypePro

        init {
            //.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            when (v) {
//            }
        }
    }

}