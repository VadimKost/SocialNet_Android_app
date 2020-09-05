package com.V.VSocial

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(),Data.VolleyCallBack {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Data.getCurrentUser(this)


    }

    override fun onReqestDone() {
        textView.text=Data.CurrentUser.toString()
    }

    fun next(view: View) {textView.text=Data.CurrentUser.toString()}

}



