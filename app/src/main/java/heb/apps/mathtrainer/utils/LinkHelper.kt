package heb.apps.mathtrainer.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import heb.apps.mathtrainer.R

object LinkHelper {

    // open link
    fun openLink(context: Context, link: String, errorMessage: String = context.getString(R.string.open_link_err_msg)) {
        val uri = Uri.parse(link)
        val linkIntent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(linkIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}