package com.edit.netdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.edit.netdemo.databinding.ActivityMainBinding
import okhttp3.*
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var url = "http://121.9.213.58/zk_client_version/androidapi/?act=list&passcode=456945"

    lateinit var binding: ActivityMainBinding;
    lateinit var client: OkHttpClient;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        client = OkHttpClient.Builder().build()
        binding.btn1.setOnClickListener() {
            synchGet()

        }
    }

    //同步请求
    fun synchGet() {
        val request = Request.Builder()
            .url(url)
            .build()

        Thread(object : Runnable {
            override fun run() {
                try {
                    var response: Response = client.newCall(request).execute()
                    var data: String = response.body()?.string().toString()
                    Log.d("OkHttp", "获取到的数据为：$data")
                    runOnUiThread(
                        Runnable { binding.text1.text = data }
                    )

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }).start()

    }


    //异步请求
    fun asyncGet() {

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    TODO("Not yet implemented")
                }
            })
    }

    //异步post请求
    private fun asyncPost() {
        val url = ""
        //添加post请求参数
        val requestBody = FormBody.Builder()
            .add("userName", "name")
            .add("passWord", "pass")
            .build()

        //创建request请求对象
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        //创建call并调用enqueue()方法实现网络请求
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                }
            })
    }


    //异步上传Multipart文件
//    private fun asyncUploadMultipart() {
//        val url = ""
//        //创建MultipartBody
//        val requestBody=MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("title","img")
//            .addFormDataPart("image","test.png",RequestBody.create("image/png".toMediaType(),File("/sdcard/test.png")))
//            .build()
//
//        //创建request请求对象
//        val request = Request.Builder()
//            .url(url)
//            .post(requestBody)
//            .build()
//
//        //创建call并调用enqueue()方法实现网络请求
//        OkHttpClient().newCall(request)
//            .enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                }
//            })
//
//    }


}