package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.handybook.R
import com.example.handybook.adapter.DarslikAdapter
import com.example.handybook.adapter.RomanAdapter
import com.example.handybook.adapter.SearchAdapter
import com.example.handybook.databinding.FragmentSeeAllBinding
import com.example.handybook.model.Book
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SeeAllFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeeAllFragment : Fragment() {
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
        val binding = FragmentSeeAllBinding.inflate(inflater,container,false)
        val type_id = arguments?.getInt("type_id")
        val api = APIClient.getInstance().create(APIService::class.java)
        var list = mutableListOf<Book>()

        api.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
               val  allbooks = (response.body() as MutableList<Book>?)!!

                for(i in allbooks){
                    if (i.type_id == type_id){
                        list.add(i)
                    }
                }

                binding.booksRv.adapter =
                    SearchAdapter(list, object : RomanAdapter.OnClickBook {
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
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SeeAllFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SeeAllFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}