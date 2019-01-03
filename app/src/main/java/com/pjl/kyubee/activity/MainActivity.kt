package com.pjl.kyubee.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pjl.kyubee.R
import com.pjl.kyubee.model.Category
import com.pjl.kyubee.model.Session
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        bottom_navigation.setupWithNavController(navController)

        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ActivityViewModel::class.java)

        subscribeCategorySpinner(puzzle_spinner)
        subscribeSessionSpinner(session_spinner)
    }

    private fun subscribeCategorySpinner(spinner: Spinner) {
        val adapter = CategoryAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf())
        spinner.adapter = adapter
        viewModel.categories.observe(this, Observer {
            adapter.setCategories(it)
        })
        viewModel.currentCategory.observe(this, Observer {
            spinner.setSelection(adapter.getPosition(it))
        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectCategory(parent?.getItemAtPosition(position) as Category)
            }
        }
    }

    private fun subscribeSessionSpinner(spinner: Spinner) {
        val adapter = SessionAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf())
        spinner.adapter = adapter
        viewModel.sessions.observe(this, Observer {
            adapter.setSessions(it)
        })
        viewModel.currentSession.observe(this, Observer {
            spinner.setSelection(adapter.getPosition(it))
        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectSession(parent?.getItemAtPosition(position) as Session)
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onSupportNavigateUp(): Boolean
            = findNavController(R.id.nav_host_fragment).navigateUp()
}
