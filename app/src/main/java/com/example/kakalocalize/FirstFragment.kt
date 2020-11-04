package com.example.kakalocalize

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_first.*
import java.net.HttpURLConnection


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
val url = "https://dapi.kakao.com/v2/local/search/address.json?query={address to search}"

class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val queue = KRestAPI.getInstance(view.context).requestQueue

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        view.findViewById<Button>(R.id.button_test).setOnClickListener {
//            _volley(it.context, url)

            // 2. Make use of a Singleton class to capsulate volley
            KRestAPI.getInstance(view.context).addToRequestQueue(
                object : StringRequest(Method.GET, url,
                    Response.Listener<String> {
                        Log.d("Kakao", it)
                        textview_first.text = it
                    }, Response.ErrorListener{
                        Log.e("Kakao", it.toString())
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header: MutableMap<String, String> = HashMap()
                        header["Authorization"] = "KakaoAK {REST API KEY}"
                        return header
                    }
                }
            )
        }
    }

    // 1. Typical request work using volley
    fun _volley(context: Context, url: String) {
        val requestQueue = Volley.newRequestQueue(context)
        val request:StringRequest = object: StringRequest(Method.GET, url,
            Response.Listener<String> {
                textview_first.text =it
                Log.d("Kakao", it)

            }, Response.ErrorListener {
                textview_first.text = it.toString()
                print(it)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val params:MutableMap<String, String> = HashMap()
                params["Authorization"] = "KakaoAK {REST API KEY}"
                return params
            }
        }
        requestQueue.add(request)
    }
}