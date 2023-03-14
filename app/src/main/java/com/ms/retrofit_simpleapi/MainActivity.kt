package com.ms.retrofit_simpleapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import retrofit2.*
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val baseURL = "https://jsonplaceholder.typicode.com/posts/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL).build()
            .create(ApiInterface :: class.java)

        val retrofitData = retrofitBuilder.getData()


        // after write retrofitData.enqueue(object) press ctrl+shift+space for override methods
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val txtId = findViewById<TextView>(R.id.txtId)

                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for (myData in responseBody){
                    myStringBuilder.append(myData.id)
                    myStringBuilder.append("\n")
                }
                txtId.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }
}