package heb.apps.mathtrainer.ui.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.ui.widget.BaseButton
import heb.apps.mathtrainer.ui.widget.EquationTV
import heb.apps.mathtrainer.utils.ImageHelper


class TestsActivity : MathBaseActivity() {

    private lateinit var lh: LayoutHolder

    var a = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityView(R.layout.activity_tests)
        lh = LayoutHolder()


        val resId = ImageHelper.getImgResByName(this, "ic_gift")
        title = "resId=" + resId
        lh.iv.setImageResource(resId)

        // TODO test equationTv with big text (do multi lines)



        //Snackbar.make(lh.clMain, "test", Snackbar.LENGTH_SHORT).show()
    }

    inner class LayoutHolder {
        val clMain: CoordinatorLayout = findViewById(R.id.coordinatorLayout_main)
        val e: EquationTV = findViewById(R.id.equationTV)
        val bt: BaseButton = findViewById(R.id.button)
        val iv: ImageView = findViewById(R.id.imageView)
    }

    companion object {
        private const val LOG_TAG = "TestsActivity"
    }
}
