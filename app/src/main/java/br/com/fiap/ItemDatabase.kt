package br.com.fiap

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemModel::class], version = 2)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}