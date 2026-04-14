package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val TAG: String = "CHECK_RESPONSE"

    private lateinit var tvComments: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvComments = findViewById(R.id.tvComments)

        getAllComments()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getAllComments() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: retrofit2.Call<List<Comments>?>,
                response: Response<List<Comments>?>
            ) {
                if (response.isSuccessful) {
                    val comments = response.body().orEmpty()

                    // Show on screen
                    val text = comments.joinToString(separator = "\n") { it.email }
                    tvComments.text = text

                    // Optional: still log
                    comments.forEach { comment ->
                        Log.i(TAG, "onResponse: ${comment.email}")
                    }
                }
            }

            override fun onFailure(
                call: retrofit2.Call<List<Comments>?>,
                t: Throwable
            ) {
                tvComments.text = "Error: ${t.message}"
                Log.i(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
