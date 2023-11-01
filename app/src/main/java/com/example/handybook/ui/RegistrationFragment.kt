package com.example.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.handybook.R
import com.example.handybook.databinding.FragmentRegistrationBinding
import com.example.handybook.model.User
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        val api = APIClient.getInstance().create(APIService::class.java)

        binding.royxatdanOtish.setOnClickListener {
            if (binding.ismi.text.isNullOrEmpty() || binding.familiyasi.text.isNullOrEmpty() || binding.emaili.text.isNullOrEmpty() || binding.paroli.text.isNullOrEmpty() || binding.paroli2.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            }else{
                if (binding.paroli.text.toString() == binding.paroli2.text.toString()){
                    val fullname = binding.ismi.text.toString() +" " + binding.familiyasi.text.toString()
                    val user1 = User(user_name = "admin", full_name = "Xosilov Qodir", email = "ali@gmail.com", password = "45761888")

                    api.createUser(user1).enqueue(object :Callback<User>{
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
                            Log.d("TAG8", "onResponse: ${response.body()}")
                            Log.d("TAG8", "user: ${user1}")
                            findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.d("TAG", "onFailure: $t")
                        }
                    })
                }
                else{
                    Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.backarrow.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment2)
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