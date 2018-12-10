package com.pjl.kyubee.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pjl.kyubee.model.Category

@Dao
interface CategoryDao {

    @Insert
    fun insert(category: Category)

    @Query("SELECT * FROM Category")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM Category WHERE name = :name")
    fun getCategory(name: String): Category
}