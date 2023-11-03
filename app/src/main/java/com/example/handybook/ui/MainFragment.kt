package com.example.handybook.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.handybook.R
import com.example.handybook.databinding.FragmentMainBinding
import com.example.handybook.databinding.NavHeaderBinding
import com.example.handybook.model.AddUser
import com.example.handybook.model.User
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText

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

        val user = arguments?.getSerializable("user") as AddUser

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bosh_sahifa -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, BoshSahifaFragment()).commit()
                    binding.fragmentname.text = requireActivity().getString(R.string.bosh_sahifa)
                }

                R.id.qidiruv -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment()).commit()
                    binding.fragmentname.text = requireActivity().getString(R.string.qidiruv)
                }

                R.id.saqlanganlar -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SavedBooksFragment()).commit()
                    binding.fragmentname.text = requireActivity().getString(R.string.saqlanganlar)
                }

                R.id.tilniozgartir -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        LanguageFragment()
                    ).commit()
                    binding.fragmentname.text = requireActivity().getString(R.string.tilni_ozgartir)
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
            when (it.itemId) {
                R.id.main -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        BoshSahifaFragment()
                    ).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = R.string.bosh_sahifa.toString()
                }

                R.id.search -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment()).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = R.string.qidiruv.toString()
                }

                R.id.saved -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        SavedBooksFragment()
                    ).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = R.string.saqlanganlar.toString()
                }

                R.id.lang -> {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.container,
                        LanguageFragment()
                    ).commit()
                    binding.drawerLayout.close()
                    binding.fragmentname.text = R.string.tilni_ozgartir.toString()
                }
            }
            true
        }
        val binding2 = NavHeaderBinding.inflate(inflater, container, false)
        val header = navigationView.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.name)
        val email = header.findViewById<TextView>(R.id.email)
        name.text = user.fullname
        email.text = "${user.username}@gmail.com"

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