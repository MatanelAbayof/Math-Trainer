package heb.apps.mathtrainer.memory.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DBManager {

    // map of DB instances
    private var mapDBs = mutableMapOf<String, RoomDatabase>()

    // get database instance
    fun <T : AppDB> getDB(applicationContext: Context, dbClass: Class<T>,
                          dbName: String, migrations: Array<Migration>) : T {

        // check if DB instance built already
        if(mapDBs.containsKey(dbName))
            return mapDBs[dbName] as T

        // create B
        var database: T? = null

        // listen events
        val rdc = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                database!!.onCreate(applicationContext)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                database!!.onOpen(applicationContext)
            }
        }

        // build DB
        database = Room.databaseBuilder(
            applicationContext,
            dbClass, dbName
        ).addCallback(rdc).addMigrations(*migrations).build()

        // add to map
        mapDBs[dbName] = database

        return database
    }
}