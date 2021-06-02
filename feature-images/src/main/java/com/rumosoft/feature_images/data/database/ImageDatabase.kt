package com.rumosoft.feature_images.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rumosoft.feature_images.data.database.dbmodels.ImageEntity

const val ImagesDbName = "Image.db"

@Database(
        entities = [ImageEntity::class],
        version = 1,
        exportSchema = false
)
abstract class ImagesDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    // The database is created by Dependency Injection
}
