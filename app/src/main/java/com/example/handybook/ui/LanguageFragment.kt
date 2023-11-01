package com.example.handybook.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.handybook.R
import com.example.handybook.adapter.LanguageAdapter
import com.example.handybook.adapter.RomanAdapter
import com.example.handybook.adapter.SearchAdapter
import com.example.handybook.databinding.FragmentLanguageBinding
import com.example.handybook.model.Book
import com.example.handybook.model.Language
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LanguageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LanguageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var currentLanguage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLanguageBinding.inflate(inflater, container, false)
        val list = mutableListOf<Language>()
        val gson = Gson()
        val type = object : TypeToken<String>() {}.type
        val activity: AppCompatActivity = activity as AppCompatActivity
        val cache = activity.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        val edit = cache.edit()
        val str = cache.getString("lang", "")
        if (!str.isNullOrEmpty()) {
            currentLanguage = gson.fromJson(str, type)
        }
        list.add(Language(R.drawable.uk, "English"))
        list.add(Language(R.drawable.uk, "O'zbek"))
        list.add(Language(R.drawable.uk, "Русский"))
        list.add(Language(R.drawable.uk, "Spanish"))
        list.add(Language(R.drawable.uk, "Portugal"))
        val adapter = LanguageAdapter(list, object : LanguageAdapter.ChangeLan {
            override fun ChangeLanguage(language: Language) {
                when (language.name) {
                    "Русский" -> {
                        setLocale("ru")
                        currentLanguage = "ru"
                    }

                    "O'zbek" -> {
                        setLocale("uz")
                        currentLanguage = "uz"
                    }

                    "English" -> {
                        setLocale("en")
                        currentLanguage = "en"
                    }

                    "Spanish" -> {
                        Toast.makeText(
                            requireContext(),
                            "No language found !",
                            Toast.LENGTH_SHORT
                        ).show();
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, LanguageFragment())
                            .commit()
                    }

                    "Portugal" -> {
                        Toast.makeText(
                            requireContext(),
                            "No language found !",
                            Toast.LENGTH_SHORT
                        ).show();
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, LanguageFragment())
                            .commit()
                    }
                }
                edit.putString("lang", gson.toJson(currentLanguage)).apply()
            }
        })
        binding.languagesRv.adapter = adapter


        binding.search.addTextChangedListener {
            val list1 = mutableListOf<Language>()

            for (i in list) {
                if (i.name.toLowerCase().contains(it.toString().toLowerCase())) {
                    list1.add(i)
                }
            }
            binding.languagesRv.adapter =
                LanguageAdapter(list1, object : LanguageAdapter.ChangeLan {
                    override fun ChangeLanguage(language: Language) {
                        when (language.name) {
                            "Русский" -> {
                                setLocale("ru")
                                currentLanguage = "ru"
                            }

                            "O'zbek" -> {
                                setLocale("uz")
                                currentLanguage = "uz"
                            }

                            "English" -> {
                                setLocale("en")
                                currentLanguage = "en"
                            }

                            "Spanish" -> {
                                Toast.makeText(
                                    requireContext(),
                                    "No language found !",
                                    Toast.LENGTH_SHORT
                                ).show();
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.container, LanguageFragment())
                                    .commit()
                            }

                            "Portugal" -> {
                                Toast.makeText(
                                    requireContext(),
                                    "No language found !",
                                    Toast.LENGTH_SHORT
                                ).show();
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.container, LanguageFragment())
                                    .commit()
                            }
                        }
                        edit.putString("lang", gson.toJson(currentLanguage)).apply()
                    }
                })
        }

        return binding.root
    }

    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            val locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            parentFragmentManager.beginTransaction().replace(R.id.container, LanguageFragment())
                .commit()
        } else {
            Toast.makeText(
                requireContext(),
                "Language, , already, , selected)!",
                Toast.LENGTH_SHORT
            ).show();
            parentFragmentManager.beginTransaction().replace(R.id.container, LanguageFragment())
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LanguageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}