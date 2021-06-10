package heb.apps.mathtrainer.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.user.UserInfo
import heb.apps.mathtrainer.ui.widget.BaseButton
import java.util.regex.Pattern


class SignUpActivity : MathBaseActivity() {

    private lateinit var layoutHolder: LayoutHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_sign_up)
        layoutHolder = LayoutHolder()

        updateOKBt()

        handleViewsEvents()
    }

    /** update OK button when fields OK or not OK */
    private fun updateOKBt() {
        layoutHolder.btSignUp.isEnabled = isFieldsOK()
    }

    /** handle views events */
    private fun handleViewsEvents() {
        layoutHolder.etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s == null)
                    return
                val text = s.toString()
                val length = text.length

                if (length > 0 && !Pattern.matches(USER_NAME_PATTERN, text)) {
                    s.delete(length - 1, length)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateOKBt()
            }
        })

        layoutHolder.btSignUp.setOnClickListener{
            val userInfo = buildUserInfoFromViews()
            returnActivityResult(userInfo)
        }
    }

    /** check if fields OK */
    private fun isFieldsOK(): Boolean
            = layoutHolder.etUserName.text.toString().trim().length >= MIN_USER_NAME_LENGTH

    /** build user info from views */
    private fun buildUserInfoFromViews() : UserInfo {
        val userInfo = UserInfo()
        userInfo.name = layoutHolder.etUserName.text.toString()
        userInfo.gender = (if (layoutHolder.rbMaleGender.isChecked) UserInfo.Gender.Male
                           else UserInfo.Gender.Female)
        return userInfo
    }

    /** return activity result */
    private fun returnActivityResult(userInfo: UserInfo) {
        val resultIntent = Intent()
        resultIntent.putExtra(USER_INFO_EXTRA_OUT, userInfo.toJSON().toString())
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        /* savedInstanceState.run {
            // restore here
        } */
    }

    override fun onSaveInstanceState(outState: Bundle) {
        /* outState.run {
            // save here
        } */
        super.onSaveInstanceState(outState)
    }

    private inner class LayoutHolder {
        val etUserName: EditText = findViewById(R.id.editText_userName)
        val rbMaleGender: RadioButton = findViewById(R.id.radio_male)
        val btSignUp: BaseButton = findViewById(R.id.button_signUp)
    }

    companion object {
        // extras
        const val USER_INFO_EXTRA_OUT = "userInfo"

        private const val MIN_USER_NAME_LENGTH = 2
        private const val USER_NAME_PATTERN = "[\\p{L} ]+" // \p{L} = unicode letter
    }
}
