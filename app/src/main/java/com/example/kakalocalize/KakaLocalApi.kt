package com.example.kakalocalize
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class KRestAPI constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: KRestAPI? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: KRestAPI(context).also {
                instance = it
            }
        }
    }

    val requestQueue: RequestQueue by lazy {
        // To prohibit Activity or BroadcastReceiver from leaking
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

}



