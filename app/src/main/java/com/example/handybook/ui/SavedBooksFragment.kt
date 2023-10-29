package com.example.handybook.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.handybook.R
import com.example.handybook.adapter.RomanAdapter
import com.example.handybook.adapter.SearchAdapter
import com.example.handybook.databinding.FragmentSavedBooksBinding
import com.example.handybook.model.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedBooksFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSavedBooksBinding.inflate(inflater,container,false)
        val type = object : TypeToken<MutableList<Book>>() {}.type
        var books = mutableListOf<Book>()
        val gson = Gson()
        val activity: AppCompatActivity = activity as AppCompatActivity
        val cache = activity.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        val str = cache.getString("saved", "")
        if (!str.isNullOrEmpty()){
            books = gson.fromJson(str,type)
        }
        if (books.isEmpty()){
            binding.nothingfound.visibility = View.VISIBLE
        }

        val adapter = SearchAdapter(books,object :RomanAdapter.OnClickBook{
            override fun onClickRoman(book: Book) {
                val bundle = Bundle()
                bundle.putInt("id", book.id)
                findNavController().navigate(
                    R.id.action_mainFragment_to_clickedBookFragment, bundle
                )
            }
        })
        binding.booksRv.adapter = adapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SavedBooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}