package com.pjl.kyubee.activity

import androidx.lifecycle.ViewModel
import com.pjl.kyubee.repository.CategoryRepository
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
        private val repository: CategoryRepository
) : ViewModel() {

    val categories = repository.getAllCategories()
}