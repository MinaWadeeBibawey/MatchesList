package com.innovitics.android.architecture.assessmentapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.innovitics.android.architecture.assessmentapplication.adapters.MatchesListAdapter
import com.innovitics.android.architecture.assessmentapplication.data.network.Resource
import com.innovitics.android.architecture.assessmentapplication.databinding.ActivityMainBinding
import com.innovitics.android.architecture.assessmentapplication.utils.*
import com.innovitics.android.architecture.assessmentapplication.viewModels.MatchesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MatchesViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    lateinit var bindingAdapter: MatchesListAdapter
    lateinit var radioButton: RadioButton
    lateinit var finalList: MutableList<Any>
    private var fromCal = Calendar.getInstance()
    private var toCal = Calendar.getInstance()
    private lateinit var dateFromSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var dateToSetListener: DatePickerDialog.OnDateSetListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initDatePicker()
        initClickListeners()
        initLiveData()

        bindingAdapter = MatchesListAdapter {
            if (it.addedToWishlist) {
                viewModel.removeItemFromWishList(it.id)
            } else {
                it.addedToWishlist = true
                viewModel.saveMatchObjectLocally(it)
            }

        }

        viewModel.getMatchListFromDB().observe(this) {
            bindingAdapter.submitList(it)
            showAndHideFilter(false)
        }

        binding.matchesRecyclerView.apply {
            adapter = bindingAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        if (!viewModel.matchesListSaved) {
            callApi()
        }
    }

    private fun callApi() {
        viewModel.getMatchesList(
            dateFrom = viewModel.dateFromToServer,
            dateTo = viewModel.dateToForServer,
            status = viewModel.filterStatus.value
        )
    }

    private fun initDatePicker() {
        dateFromSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                fromCal.set(Calendar.YEAR, year)
                fromCal.set(Calendar.MONTH, monthOfYear)
                fromCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                viewModel.dateFromToServer = parseDateTOServerFormat(fromCal.time) ?: ""
                viewModel.setFilterFromDate(updateDateInView(fromCal.time) ?: "")
            }

        dateToSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                toCal.set(Calendar.YEAR, year)
                toCal.set(Calendar.MONTH, monthOfYear)
                toCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                viewModel.dateToForServer = parseDateTOServerFormat(fromCal.time) ?: ""
                viewModel.setFilterToDate(updateDateInView(toCal.time) ?: "")
            }
    }

    private fun initClickListeners() {
        binding.fromDateFilterTv.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                dateFromSetListener,
                fromCal.get(Calendar.YEAR),
                fromCal.get(Calendar.MONTH),
                fromCal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.toDateFilterTv.setOnClickListener {
            if (viewModel.filterFromDate.value != "") {
                DatePickerDialog(
                    this@MainActivity,
                    dateToSetListener,
                    toCal.get(Calendar.YEAR),
                    toCal.get(Calendar.MONTH),
                    toCal.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else {
                Toast.makeText(this, "Please Select From Date First", Toast.LENGTH_LONG).show()
            }
        }

        binding.confirmFilterStatusButtonPopup.setOnClickListener {
            binding.motionLayout.transitionToStart()

            val selectedOption: Int = binding.matchStatusRg.checkedRadioButtonId
            if (selectedOption != -1) {
                radioButton = findViewById(selectedOption)
                viewModel.setFilterStatus(radioButton.text.toString())
                callApi()
            }
        }

        binding.clearFilterStatusButton.setOnClickListener {
            viewModel.setFilterStatus("")
            callApi()
        }

        binding.clearDateFilterButton.setOnClickListener {
            viewModel.resetDateFilterData()
            callApi()
        }
    }

    private fun initLiveData() {
        viewModel.filterStatus.observe(this) {
            if (it == "")
                binding.statusDateFilterTv.text = "Status"
            else
                binding.statusDateFilterTv.text = it
        }

        viewModel.filterFromDate.observe(this) {
            if (it == "")
                binding.fromDateFilterTv.text = "From"
            else
                binding.fromDateFilterTv.text = it
        }

        viewModel.filterToDate.observe(this) {
            if (it == "")
                binding.toDateFilterTv.text = "To"
            else if (!viewModel.filteredWithDate.value!!) {
                binding.toDateFilterTv.text = it
                callApi()
                viewModel.resetFilteredWithDateFlag(true)
            } else {
                binding.toDateFilterTv.text = it
            }
        }

        viewModel.matchesResponse.observe(this) {
            showLoading(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    showAndHideFilter(true)
                    finalList = listConverter(it.value.matches)
                    bindingAdapter.submitList(finalList)
                    viewModel.updateMatchesSavedListStatus(true)
                }
                is Resource.Failure -> {
                    handleApiError(it, { callApi() }, view = binding.root)
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.AppLoadingRL.visible(boolean)
        Log.d("Loading", boolean.toString())
    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        startNewActivity(MainActivity::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_wishlist -> {
                viewModel.getMatchListFromDB().observe(this) {
                    bindingAdapter.submitList(it)
                    showAndHideFilter(false)
                }
            }
            else -> {
                showAndHideFilter(true)
                bindingAdapter.submitList(finalList)
            }
        }

        return true
    }

    private fun showAndHideFilter(show: Boolean) {
        binding.filterHeader.visible(show)
        binding.fromDateFilterTv.visible(show)
        binding.toDateFilterTv.visible(show)
        binding.statusDateFilterTv.visible(show)
        binding.clearDateFilterButton.visible(show)
        binding.clearFilterStatusButton.visible(show)
    }
}