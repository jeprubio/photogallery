package com.rumosoft.photogallery.data.database

import androidx.room.*
import com.rumosoft.photogallery.data.database.dbmodels.ImageEntity

@Dao
interface ImageDao {

    @Query("SELECT * from image ORDER BY id ASC")
    suspend fun getImages(): List<ImageEntity>

    @Query("SELECT * from image where id = :imageId LIMIT 1")
    suspend fun getImage(imageId: Long): ImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAll(list: List<ImageEntity>)

    @Query("DELETE FROM image")
    suspend fun deleteAll()
}