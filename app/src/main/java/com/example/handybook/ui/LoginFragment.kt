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
import com.example.handybook.databinding.FragmentLoginBinding
import com.example.handybook.model.Login
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
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
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
        val binding = FragmentLoginBinding.inflate(inflater,container,false)
        val api = APIClient.getInstance().create(APIService::class.java)
        binding.kirish.setOnClickListener {
            if (binding.emaili.text.isNullOrEmpty() || binding.paroli.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Fill all !", Toast.LENGTH_SHORT).show()
            }
            else{
                val login1 = Login(username = "admin", password = "45761888")
                api.login(login1).enqueue(object :Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.body() == null){
                            Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Log.d("TAG9", "onResponse: ${response.body()}")
                            Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
                            val bundle = Bundle()
                            val user = response.body()
                            bundle.putSerializable("user",user)
                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment,bundle)
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d("TAG", "onFailure: $t")
                    }
                })
                binding.royxatdan.setOnClickListener {
                    findNavController().navigate(R.id.action_loginFragment_to_registrationFragment2)
                }
            }
        }

        binding.royxatdan.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment2)
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}