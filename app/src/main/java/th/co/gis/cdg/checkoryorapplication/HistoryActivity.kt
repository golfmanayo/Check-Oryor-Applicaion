package th.co.gis.cdg.checkoryorapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_history.*
import th.co.gis.cdg.checkoryorapplication.database.DatabaseManager
import th.co.gis.cdg.checkoryorapplication.model.Constants

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyListView.adapter = HistoryAdapter(this, Constants.list)
//        DatabaseManager.getInstance(this).getUpload()
//            .subscribe({list ->
//                historyListView.adapter = HistoryAdapter(this, Constants.list)
//            },{
//                Log.i("Error",it.message)
//            })
      }
}
