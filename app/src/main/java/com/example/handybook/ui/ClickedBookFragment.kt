package com.example.handybook.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.handybook.R
import com.example.handybook.adapter.DarslikAdapter
import com.example.handybook.adapter.RomanAdapter
import com.example.handybook.adapter.ViewPagerAdapter
import com.example.handybook.databinding.FragmentClickedBookBinding
import com.example.handybook.model.Book
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClickedBookFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClickedBookFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var saved_list = mutableListOf<Book>()

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
        val binding = FragmentClickedBookBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)
        var list = mutableListOf<Book>()
        val id = arguments?.getInt("id")
        val type = object : TypeToken<MutableList<Book>>() {}.type
        var book: Book
        val gson = Gson()
        val activity: AppCompatActivity = activity as AppCompatActivity
        val cache = activity.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        val edit = cache.edit()
        val str = cache.getString("saved", "")
        binding.audiokitob.setBackgroundColor(Color.TRANSPARENT)
        if (!str.isNullOrEmpty()) {
            saved_list = gson.fromJson(str, type)
        }


        api.getthebook(id!!).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                book = response.body()!!
                binding.booksImage.load(book.image)
                binding.audioImage.load(book.image)
                binding.name.text = book.name
                binding.bookRayting.text = book.reyting.toDouble().toString()
                binding.bookauthor.text = book.author
                if (isTheBookSaved(book)) {
                    binding.save.setBackgroundResource(R.drawable.baseline_bookmark_24)
                } else binding.save.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                binding.save.setOnClickListener {
                    if (isSaved(book)) {
                        binding.save.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                        Toast.makeText(requireContext(), "Unsaved", Toast.LENGTH_SHORT).show()
                    } else binding.save.setBackgroundResource(R.drawable.baseline_bookmark_24)

                    edit.putString("saved", gson.toJson(saved_list)).apply()
                }
                binding.booksImage.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("bookid", book.id)
                    findNavController().navigate(
                        R.id.action_clickedBookFragment_to_commentsFragment, bundle
                    )
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {

            }
        })

        api.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                list = (response.body() as MutableList<Book>?)!!
                binding.tavsiyalarrecycle.adapter =
                    DarslikAdapter(list, object : RomanAdapter.OnClickBook {
                        override fun onClickRoman(book: Book) {
                            Toast.makeText(requireContext(), "Fuck You too", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG1", "onFailure: $t")
            }
        })

        binding.audiokitob.setOnClickListener {
            binding.audiokitob.setBackgroundResource(R.drawable.darkblue_button)
            binding.ekitob.setBackgroundColor(Color.TRANSPARENT)
            binding.audioImage.visibility = View.VISIBLE
            binding.booksImage.visibility = View.GONE

        }
        binding.ekitob.setOnClickListener {
            binding.ekitob.setBackgroundResource(R.drawable.darkblue_button)
            binding.audiokitob.setBackgroundColor(Color.TRANSPARENT)
            binding.audioImage.visibility = View.INVISIBLE
            binding.booksImage.visibility = View.VISIBLE
        }

        binding.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter



        return binding.root
    }


    fun isSaved(book: Book): Boolean {
        for (i in saved_list) {
            if (i == book) {
                saved_list.remove(i)
                return true
            }
        }
        saved_list.add(book)
        return false
    }

    fun isTheBookSaved(book: Book): Boolean {
        for (i in saved_list) {
            if (i == book) {
                return true
            }
        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClickedBookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}