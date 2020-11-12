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
    ): View {
        val v = inflater.inflate(R.layout.fragment_main_user, container, false)
        v.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        SocialNetService.Api().getContactAndLinks(getUserCredentials(context)).enqueue(
            object : Callback<List<Contact_and_links>> {
                override fun onResponse(call: Call<List<Contact_and_links>>, response: Response<List<Contact_and_links>>) {
                    if (response.isSuccessful) {
                        v.rv.adapter = response.body()?.let { LinksAdapter(it) }
                    } else {
                        Toast.makeText(context, "Something went WRONGs", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Contact_and_links>>, t: Throwable) {
                    Log.e("", "lox")
                    t.printStackTrace()
                }
            }
        )


        SocialNetService.Api().getCurrentUser(getUserCredentials(context)).enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user: User? = response.body()
                    if (response.isSuccessful && user != null) {
                            Toast.makeText(context, "Loaded", Toast.LENGTH_SHORT).show()
                            v.username_f.text = user.username
                            v.birthday_f.text = user.user_profile.data
                            v.gender_f.text =
                                context?.resources?.getStringArray(R.array.gender)!![user.user_profile.gender!! - 1]
                            v.address_f.text = user.user_profile.adress
                            v.about_f.text = user.user_profile.aboutMe

                            val mPicasso = Picasso.with(context)
                            mPicasso.setIndicatorsEnabled(true)
                            mPicasso.load(user.user_profile.photo).centerCrop().fit().into(v.image)

                    } else if (response.code() == 401) {
                        removeUserCredentials(context)
                        startActivity(Intent(context, LoginActivity::class.java))
                    } else {
                        Toast.makeText(context, "Something went WRONGs", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("", "lox")
                    t.printStackTrace()
                }
            })


        return v
    }

}
