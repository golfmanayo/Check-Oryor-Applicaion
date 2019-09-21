package th.co.gis.cdg.checkoryorapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_history.*
import th.co.gis.cdg.checkoryorapplication.database.DatabaseManager
import th.co.gis.cdg.checkoryorapplication.model.Constants
import th.co.gis.cdg.checkoryorapplication.model.Oryor

class HistoryActivity : AppCompatActivity(), HistoryAdapter.HistoryAdapterListener {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val adapter = HistoryAdapter(this, mutableListOf())
        adapter.callback = this
        DatabaseManager.getInstance(this).getUpload()
            .subscribe({ list ->
                historyListView.adapter = adapter
                adapter.setData(list)
            }, {
                Log.i("Error", it.message)
            })
    }

    override fun onItemClick(oryor: Oryor) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("ID_ORYOR", oryor.UPLOAD_ID)
        startActivity(intent)
    }


}
