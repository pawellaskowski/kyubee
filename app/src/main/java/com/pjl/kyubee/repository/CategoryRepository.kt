package com.pjl.kyubee.repository

import com.pjl.kyubee.database.CategoryDao
import com.pjl.kyubee.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    fun getAllCategories() = categoryDao.getAllCategories()

    fun insert(category: Category) = categoryDao.insert(category)

    fun getCategory(name: String) = categoryDao.getCategory(name)
}