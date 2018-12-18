package com.pjl.kyubee.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pjl.kyubee.model.Category
import io.reactivex.Flowable

@Dao
interface CategoryDao {

    @Insert
    fun insert(category: Category)

    @Query("SELECT * FROM Category")
    fun getAllCategories(): Flowable<List<Category>>

    @Query("SELECT * FROM Category WHERE name = :name")
    fun getCategory(name: String): Flowable<Category>
}