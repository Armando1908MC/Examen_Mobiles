package com.example.kotlin.examenmovilesmendez.frameworks.views.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.examenmovilesmendez.R
import com.example.kotlin.examenmovilesmendez.frameworks.viewmodel.ViewModel
import com.example.kotlin.examenmovilesmendez.databinding.ItemBinding
import com.example.kotlin.examenmovilesmendez.databinding.ActivityMainBinding
import com.example.kotlin.examenmovilesmendez.frameworks.adapters.Adapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: Adapter
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponents()
    }

    private fun initializeComponents() {
        setupViewModel()
        configureRecyclerView()
        handlePagination()
        loadInitialPage()
    }

    private fun configureRecyclerView() {
        adapter = Adapter().also {
            binding.recyclerViewEventos.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = it
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.eventos.observe(this) { eventos ->
            adapter.submitList(eventos)
            binding.textPageNumber.text = "Página $currentPage"
        }

        viewModel.error.observe(this) { error ->
            error?.let { showError(it) }
        }
    }

    private fun handlePagination() {
        binding.buttonPrevious.setOnClickListener { navigateToPreviousPage() }
        binding.buttonNext.setOnClickListener { navigateToNextPage() }
    }

    private fun navigateToPreviousPage() {
        if (currentPage > 1) {
            currentPage--
            loadPage(currentPage)
        }
    }

    private fun navigateToNextPage() {
        currentPage++
        loadPage(currentPage)
    }

    private fun loadInitialPage() = loadPage(currentPage)

    private fun loadPage(page: Int) {
        viewModel.consultarEventos(page)
        binding.buttonPrevious.isEnabled = page > 1
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}