package heb.apps.mathtrainer.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

object ImageHelper {

    /** get drawable from assets path */
    fun getImgDrawableFromAssets(ctx: Context, imgPath: String) : Drawable {
        try {
            val imgStream = ctx.assets.open(imgPath)
            return Drawable.createFromStream(imgStream, null)
        } catch (e: Exception) {
            throw IllegalArgumentException("Cannot load the image at '$imgPath' from assets")
        }
    }

    /** get image resource from drawable folder by name */
    fun getImgResByName(ctx: Context, name: String) : Int {
        @DrawableRes val resId = ctx.resources.getIdentifier(name, DRAWABLE_FOLDER_NAME, ctx.packageName)
        require(resId != NOT_FOUND_IMG) {
            "Cannot load the image with name = '$name' in drawable folder"
        }
        return resId
    }

    /** drawable folder name */
    private const val DRAWABLE_FOLDER_NAME = "drawable"
    /** not found image flag */
    private const val NOT_FOUND_IMG = 0
}