package heb.apps.mathtrainer.utils

import android.content.res.Resources
import androidx.annotation.RawRes


fun Resources.getRawTextFile(@RawRes id: Int) =
    openRawResource(id).bufferedReader().use { it.readText() }