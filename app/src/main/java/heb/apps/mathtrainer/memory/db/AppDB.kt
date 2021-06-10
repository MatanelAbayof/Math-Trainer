package heb.apps.mathtrainer.memory.db

import android.content.Context
import androidx.room.RoomDatabase

abstract class AppDB : RoomDatabase() {

    // event on create DB at first time
    abstract fun onCreate(ctx: Context)

    // event on open DB - create instance
    abstract fun onOpen(ctx: Context)

    // TODO add: onUpgrade(ctx)

}

