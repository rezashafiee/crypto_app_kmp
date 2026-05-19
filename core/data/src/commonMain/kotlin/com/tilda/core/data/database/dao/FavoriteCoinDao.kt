package com.tilda.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tilda.core.data.database.model.FavoriteCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCoinDao {

    @Query("SELECT coinId FROM favorite_coins")
    fun getFavoriteCoinIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteCoin(favoriteCoin: FavoriteCoinEntity)

    @Query("DELETE FROM favorite_coins WHERE coinId = :coinId")
    suspend fun deleteFavoriteCoin(coinId: Int)
}
