package com.example.handybook.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.handybook.R
import com.example.handybook.databinding.FragmentMainBinding
import com.google.android.material.navigation.NavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var drawerLayout: DrawerLayout? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        parentFragmentManager.beginTransaction()
            .add(R.id.container, BoshSahifaFragment())
            .commit()



        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bosh_sahifa -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, BoshSahifaFragment()).commit()
                    binding.fragmentname.text = "Bosh Sahifa"
                }

                R.id.qidiruv -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment()).commit()
                    binding.fragmentname.text = "Qidiruv"
                }

                R.id.saqlanganlar -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SavedBooksFragment()).commit()
                    binding.fragmentname.text = "Saqlanganlar"
                }
                R.id.tilniozgartir -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        LanguageFragment()
                    ).commit()
                    binding.fragmentname.text = "Tilni o'zgartir"
                }
            }
            true
        }

        drawerLayout = binding.drawerLayout
        val navigationView = binding.navView
        val toolbar = binding.toolbar

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)


        val toggle = ActionBarDrawerToggle(
            requireActivity(), binding.drawerLayout, toolbar, R.string.open_nav,
            R.string.close_nav
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
            when (it.itemId) {
                R.id.main -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        BoshSahifaFragment()
                    ).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = "Bosh Sahifa"
                }

                R.id.search -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment()).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = "Qidiruv"
                }

                R.id.saved -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        SavedBooksFragment()
                    ).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = "Saqlanganlar"
                }
                R.id.lang -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        LanguageFragment()
                    ).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = "Tilni o'zgartiring"
                }
            }
            true
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}