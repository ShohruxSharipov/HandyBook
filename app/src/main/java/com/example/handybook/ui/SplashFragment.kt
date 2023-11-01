package com.example.handybook.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.handybook.R
import com.example.handybook.databinding.FragmentSplashBinding
import com.example.handybook.model.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(inflater, container, false)
        val type = object : TypeToken<String>() {}.type
        var book: Book
        val gson = Gson()
        val activity: AppCompatActivity = activity as AppCompatActivity
        val cache = activity.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        val str = cache.getString("lang", "")

        if (!str.isNullOrEmpty()) {
            currentLanguage = gson.fromJson(str, type)
            setLocale(currentLanguage)
        }

        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.rotation)
        val handle = Handler()
        binding.imageView.startAnimation(anim)
        handle.postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_enterFragment)
        }, 1000)

        return binding.root
    }

    private fun setLocale(localeName: String) {
            val locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = SplashFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}