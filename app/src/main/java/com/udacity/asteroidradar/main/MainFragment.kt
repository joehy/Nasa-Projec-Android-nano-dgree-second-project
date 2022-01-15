package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var asteroidsAdapter: AsteroidsAdapter
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding :FragmentMainBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setAsteroidsAdapter()
        binding.asteroidRecycler.adapter=asteroidsAdapter
        updateAdapterList()
        setHasOptionsMenu(true)
        return binding.root
    }


    private fun setAsteroidsAdapter() {
        asteroidsAdapter= AsteroidsAdapter(AsteroidsAdapter.AsteroidListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })
    }
    private fun updateAdapterList() {
        viewModel.asteroids.observe(viewLifecycleOwner, {
            if (it != null) {
                Log.i("el7ala","Everything is Good")
                asteroidsAdapter.submitList(it)
            }else{
                Log.i("el7ala","Everything is Opos")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(context,"MMMMMMMM",Toast.LENGTH_SHORT).show()
        Log.i("el7ala","onOptionsItemSelected")
        when (item.itemId) {
            R.id.show_all_menu -> viewModel.onWeekFilterCheck()
            R.id.show_rent_menu -> {
                Toast.makeText(context,"show_rent_menu",Toast.LENGTH_SHORT).show()
                updateAdapterList()
                Log.i("el7ala","show_rent_menu")
                viewModel.onTodayFilterCheck()
            }
            R.id.show_buy_menu -> viewModel.onSavedFilterCheck()
        }
        return true
    }
}
