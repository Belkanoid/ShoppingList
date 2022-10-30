package com.belkanoid.shoppinglist.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.belkanoid.shoppinglist.data.database.dao.ShoppingListDao
import com.belkanoid.shoppinglist.data.database.dbEntity.ShoppingItemDbModel
import javax.inject.Inject

@Database(entities = [ShoppingItemDbModel::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun shoppingListDao() : ShoppingListDao

    companion object {
        private var INSTANCE: ShoppingListDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "shopping_list.db"

        fun getInstance(application: Application): ShoppingListDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
            }

            val db = Room.databaseBuilder(
                application,
                ShoppingListDatabase::class.java,
                DB_NAME
            )
                .build()

            INSTANCE = db
            return db
        }
    }
}