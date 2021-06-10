package heb.apps.mathtrainer.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper


class ContactUsDialog(context: Context) : BaseDialog(context) {

    private class DialogLayout(val dialogView: View) {
        val etMessage: EditText = dialogView.findViewById(R.id.editText_message)
    }

    // dialog layoutHolder
    private val dialogLayout: DialogLayout

    init {
        setTitle(R.string.contact_us)
        setIcon(R.drawable.ic_mail_black_24dp)

        // set content view
        dialogLayout = buildDialogLayout()
        setView(dialogLayout.dialogView)

        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.send_msg)) { _, _ ->
            // send message
            val message = buildMessage()
            openEmailActivity(message)
        }
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(android.R.string.cancel)) { _,_ -> }


        // control when show
        dialogLayout.etMessage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                updateSendBt()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        setOnShowListener{
            updateSendBt()
        }
    }

    // check if message OK
    private fun isMessageOK() : Boolean {
        val message = dialogLayout.etMessage.text.toString()
        val messageAfterTrim = message.trim()
        return messageAfterTrim.isNotEmpty()
    }

    // update send button
    private fun updateSendBt() {
        getButton(BUTTON_POSITIVE).isEnabled = isMessageOK()
    }

    // build message
    private fun buildMessage() : String {
        val sb = StringBuilder()
        // append message
        sb.appendln(dialogLayout.etMessage.text.toString())


        // append metadata
        sb.append("\n".repeat(4))
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val currentVersionName = pInfo.versionName
            sb.append(context.getString(R.string.application_version))
            sb.append(": ")
            sb.append(currentVersionName)
            sb.appendln()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        sb.append(context.getString(R.string.android_version))
        sb.append(": ")
        sb.append(android.os.Build.VERSION.RELEASE)
        sb.appendln()
        sb.append(context.getString(R.string.model))
        sb.append(": ")
        sb.append(android.os.Build.MODEL)
        sb.appendln()
        sb.append(context.getString(R.string.resolution))
        sb.append(": ")
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        sb.append(metrics.widthPixels.toString())
        sb.append("*")
        sb.append(metrics.heightPixels.toString())
        sb.append(" ")
        sb.append(context.getString(R.string.pixels))
        sb.appendln()

        return sb.toString()
    }


    // open email activity
    private fun openEmailActivity(message: String) {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", context.getString(R.string.contact_email_address), null)
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(
            Intent.createChooser(
                emailIntent,
                context.getString(R.string.send_msg) + " " + context.getString(R.string.via)
            )
        )
    }

    // build dialog layoutHolder
    private fun buildDialogLayout() : DialogLayout {
        val dialogView = LayoutInflateHelper.inflate(context, R.layout.contact_us)
        return DialogLayout(dialogView)
    }
}