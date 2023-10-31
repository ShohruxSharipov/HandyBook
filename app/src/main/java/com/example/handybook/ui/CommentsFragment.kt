package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.handybook.R
import com.example.handybook.adapter.CommentAdapter
import com.example.handybook.databinding.FragmentCommentsBinding
import com.example.handybook.model.AddComment
import com.example.handybook.model.Comment
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
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentsFragment : Fragment() {
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
        val binding = FragmentCommentsBinding.inflate(inflater, container, false)
        val id = arguments?.getInt("bookid")
        val api = APIClient.getInstance().create(APIService::class.java)
        Log.d("TAG3", "onCreateView: $id")

        api.bookComment(id!!).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                Log.d("TAG2", "onResponse: ${response.body()}")
                binding.commentsRv.adapter = CommentAdapter(response.body()!!.reversed())
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })

        binding.send.setOnClickListener {
            if (!binding.comment.text.isNullOrBlank()) {
                val comment = AddComment(book_id = id, text = binding.comment.text.toString(), user_id = 1 )
                api.createComment(comment).enqueue(object :Callback<AddComment>{
                    override fun onResponse(
                        call: Call<AddComment>,
                        response: Response<AddComment>
                    ) {
                        Toast.makeText(requireContext(), "Sent", Toast.LENGTH_SHORT).show()
                        binding.comment.text!!.clear()
                    }

                    override fun onFailure(call: Call<AddComment>, t: Throwable) {
                        Log.d("TAG", "onFailure: $t")
                    }
                })
                api.bookComment(id).enqueue(object : Callback<List<Comment>> {
                    override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                        Log.d("TAG2", "onResponse: ${response.body()}")
                        binding.commentsRv.adapter = CommentAdapter(response.body()!!.reversed())
                    }

                    override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                        Log.d("TAG", "onFailure: $t")
                    }
                })

            } else Toast.makeText(requireContext(), "Write something", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}