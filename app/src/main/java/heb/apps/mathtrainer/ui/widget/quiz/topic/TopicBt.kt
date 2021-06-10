package heb.apps.mathtrainer.ui.widget.quiz.topic

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.data.quiz.topic.TopicWithExtras
import heb.apps.mathtrainer.ui.widget.PercentageBar
import heb.apps.mathtrainer.utils.ImageHelper
import heb.apps.mathtrainer.utils.view.LayoutInflateHelper


class TopicBt(ctx: Context, val topicWithEx: TopicWithExtras)
    : RelativeLayout(ctx) {

    private lateinit var layoutHolder: LayoutHolder
    var onHelpClickListener: (TopicBt) -> Unit  = {}
    var onClickListener: (TopicBt) -> Unit  = {}

    init {
        isFocusable = false
        buildTopicView()
        initEvents()
    }

    // build topic view
    private fun buildTopicView() {
        val topicView = LayoutInflateHelper.inflate(context, R.layout.topic_bt)
        layoutHolder = LayoutHolder(topicView)
        updateTopicInfo()
        // add view as child
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        addView(topicView, lp)
    }

    // update topic info
    private fun updateTopicInfo() {
        layoutHolder.tvTopicName.text = topicWithEx.topic.displayName

        // set topic image
        topicWithEx.topic.imagePath?.let { imgPath ->
            val imgRes = ImageHelper.getImgResByName(context, imgPath)
            layoutHolder.ivTopicImage.setImageResource(imgRes)
        }

        if(topicWithEx.topic.description.isEmpty()) {
            layoutHolder.ivHelp.visibility = View.GONE
        } else {
            layoutHolder.ivHelp.visibility = View.VISIBLE
        }

        // set percentage value
        layoutHolder.pbTopic.percentage = topicWithEx.completePercents.toInt()
    }

    // init events
    private fun initEvents() {
        layoutHolder.ivHelp.setOnClickListener {
            onHelpClickListener(this)
        }
        layoutHolder.rlTopic.setOnClickListener {
            onClickListener(this)
        }
    }

    private class LayoutHolder(view: View) {
        val rlTopic: RelativeLayout = view.findViewById(R.id.relativeLayout_topic)
        val tvTopicName: TextView = view.findViewById(R.id.textView_topicName)
        val ivTopicImage: ImageView = view.findViewById(R.id.imageView_topicImage)
        val pbTopic: PercentageBar = view.findViewById(R.id.percentageBar_topic)
        val ivHelp: ImageView = view.findViewById(R.id.imageView_help)
    }
}