package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.handybook.adapter.RomanAdapter
import com.example.handybook.databinding.FragmentBoshSahifaBinding
import com.example.handybook.model.Book
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BoshSahifaFragment : Fragment() {
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
        val binding = FragmentBoshSahifaBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)

        api.getAllBooks().enqueue(object :Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                Log.d("TAG", "onResponse: ${response.body()}")
                val list = response.body()
                val adapter = RomanAdapter(list!!,object :RomanAdapter.OnClickBook{
                    override fun onClickRoman(book: Book) {
                        Toast.makeText(requireContext(), "Fuck You", Toast.LENGTH_SHORT).show()
                    }
                })
                binding.romanlarrecycle.adapter = adapter
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onResponse: $t")
            }

        })
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BoshSahifaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}