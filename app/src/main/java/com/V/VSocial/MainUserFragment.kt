package com.V.VSocial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main_user.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainUserFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =inflater.inflate(R.layout.fragment_main_user, container, false)
        v.rv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        v.rv.adapter= LinksAdapter(Links.data())
        SocialService.Api()
            .getCurrentUser(context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.getString("UAC",""))
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user: User? = response.body()
                    if (response.isSuccessful){
                        if (user != null) {
                            Toast.makeText(context, "Loaded", Toast.LENGTH_SHORT).show()
                            v.username_f.text=user.username
                            v.address_f.text=user.user_i.adress
                            v.about_f.text=user.user_i.aboutMe
                            val mPicasso = Picasso.with(context)
                            mPicasso.setIndicatorsEnabled(true)
                            mPicasso.load(user.img.photo).centerCrop().fit().into(v.image)
                        }
                    }
                    else if (response.code() == 401){
                        context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()?.remove("UAC")?.apply()
                        startActivity(Intent(context,LoginActivity::class.java))
                    }
                    else{
                        Toast.makeText(context, "Something went WRONGs", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("","lox")
                    t.printStackTrace()
                }
            })
        return v
    }

}