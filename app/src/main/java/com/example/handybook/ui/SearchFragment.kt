package com.example.handybook.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.handybook.R
import com.example.handybook.adapter.CategoryAdapter
import com.example.handybook.adapter.DarslikAdapter
import com.example.handybook.adapter.RomanAdapter
import com.example.handybook.adapter.SearchAdapter
import com.example.handybook.databinding.FragmentSearchBinding
import com.example.handybook.model.Book
import com.example.handybook.model.Category
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)
        var allbooks = mutableListOf<Book>()
        var recents = mutableListOf<Book>()
        val type2 = object : TypeToken<MutableList<Book>>() {}.type
        val gson = Gson()
        val activity: AppCompatActivity = activity as AppCompatActivity
        val cache = activity.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        val edit = cache.edit()
        val str = cache.getString("recent_books", "")
        if (!str.isNullOrEmpty()) {
            recents = gson.fromJson(str, type2)
            binding.recentSearched.adapter = RomanAdapter(recents.reversed(),object :RomanAdapter.OnClickBook{
                override fun onClickRoman(book: Book) {
                    val bundle = Bundle()
                    bundle.putInt("id", book.id)
                    findNavController().navigate(
                        R.id.action_mainFragment_to_clickedBookFragment, bundle
                    )
                }
            })
        }else{
            Toast.makeText(requireContext(), "No recent books", Toast.LENGTH_SHORT).show()
        }

        api.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                Log.d("TAG", "onResponse: ${response.body()?.size}")
                allbooks = (response.body() as MutableList<Book>?)!!
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onResponse: $t")
            }

        })

        binding.search.addTextChangedListener {
            val list1 = mutableListOf<Book>()
            if (it.toString().isNullOrBlank()) {
                binding.filtrlayout.visibility = View.VISIBLE
                binding.searchedLay.visibility = View.GONE
            } else {
                binding.filtrlayout.visibility = View.GONE
                binding.searchedLay.visibility = View.VISIBLE
            }
            for (i in allbooks) {
                if (i.name.toLowerCase().contains(it.toString().toLowerCase())) {
                    list1.add(i)
                }
            }
            binding.searchRv.adapter = SearchAdapter(list1, object : RomanAdapter.OnClickBook {
                override fun onClickRoman(book: Book) {
                    if (recents.size == 4){
                        recents.removeAt(0)
                    }
                    recents.add(book)
                    edit.putString("recent_books",gson.toJson(recents)).apply()
                    val bundle = Bundle()
                    bundle.putInt("id", book.id)
                    findNavController().navigate(
                        R.id.action_mainFragment_to_clickedBookFragment, bundle
                    )
                }
            })
        }

        api.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                val adapter =
                    CategoryAdapter(response.body()!!, object : CategoryAdapter.onClickCategorya {
                        override fun onClickCategory(category: Category) {
                            Toast.makeText(requireContext(), "Fuck you", Toast.LENGTH_SHORT).show()
                        }
                    })
                binding.categories.adapter = adapter
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}