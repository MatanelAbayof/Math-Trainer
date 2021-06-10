package heb.apps.mathtrainer.utils.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

object LayoutInflateHelper {

    // inflate view
    fun inflate(ctx: Context, layoutId: Int, root: ViewGroup? = null, attachToRoot: Boolean = false) : View {
        return when(root) {
            null -> LayoutInflater.from(ctx).inflate(layoutId, null)
            else -> LayoutInflater.from(ctx).inflate(layoutId, root, attachToRoot)
        }
    }
}