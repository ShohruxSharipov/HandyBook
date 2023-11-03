package com.example.handybook.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.handybook.R
import com.example.handybook.databinding.FragmentRegistrationBinding
import com.example.handybook.model.AddUser
import com.example.handybook.model.User
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
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

    lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        val gson = Gson()
        val activity: AppCompatActivity = activity as AppCompatActivity
        val cache = activity.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        val edit = cache.edit()

        binding.go.setOnClickListener {
            val api = APIClient.getInstance().create(APIService::class.java)
            user = User(
                binding.name.text.toString(),
                "${binding.surname.text?.trim().toString()} ${
                    binding.name.text?.trim().toString()
                }",
                binding.email.text.toString(),
                binding.password.text.toString()
            )

            api.createUser(user).enqueue(object : Callback<AddUser> {
                override fun onResponse(call: Call<AddUser>, response: Response<AddUser>) {
                    if (response.isSuccessful) {
                        edit.putString("status", gson.toJson(response.body())).apply()
                    }
                    findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
                    Log.d("TAG", "Oppa: ${response.body()}")
                }

                override fun onFailure(call: Call<AddUser>, t: Throwable) {
                    Log.d("TAG9", "onFailure: $t")
                }

            })
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
         * @return A new instance of fragment RegistrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}