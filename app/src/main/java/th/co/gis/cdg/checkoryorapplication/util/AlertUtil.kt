package th.co.gis.cdg.checkoryorapplication.util

import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding

object AlertUtil {
    
    fun alert(context: Context, msg: String) {
        val contentTitle = TextView(context)
        contentTitle.setSingleLine(false)
        contentTitle.setPadding(32)
        contentTitle.textSize = 22f
        val content = TextView(context)
        content.setSingleLine(false)
        content.setPadding(32)
        content.textSize = 16f
        content.text = msg
        AlertDialog.Builder(context)
            .setCustomTitle(contentTitle)
            .setView(content)
            .setPositiveButton("ตกลง") {_,_ ->}
            .show()
    }
    
    fun alert(context: Context, @StringRes msgResId: Int) {
        val contentTitle = TextView(context)
        contentTitle.setSingleLine(false)
        contentTitle.setPadding(32)
        contentTitle.textSize = 22f
        contentTitle.text = ""
//        contentTitle.typeface = ResourcesCompat.getFont(context, R.font.kanit_light)
        val content = TextView(context)
        content.setSingleLine(false)
        content.setPadding(32)
        content.textSize = 16f
        content.text = context.resources.getText(msgResId)
//        content.typeface = ResourcesCompat.getFont(context, R.font.kanit_light)
        AlertDialog.Builder(context)
            .setCustomTitle(contentTitle)
            .setView(content)
            .setPositiveButton("ตกลง") { _, _ ->}
            .show()
    }
    


    fun alertWithSelect(context: Context, arrayOption : Array<String>, title: String, listener:((dialog: DialogInterface, which: Int) -> Unit)){
        AlertDialog.Builder(context)
            .setTitle(title)
            .setItems(arrayOption, listener)
            .show()
    }
}
