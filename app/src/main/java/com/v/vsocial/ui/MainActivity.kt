package com.v.vsocial.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.v.vsocial.R
import com.v.vsocial.api.Auth
import com.v.vsocial.databinding.ActivityMainBinding
import com.v.vsocial.ui.profile.UserProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var toolbar =activityBinding.toolbar as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.title=null


        activityBinding.navigationView.selectedItemId= R.id.user
        supportFragmentManager.beginTransaction().replace(R.id.container, UserProfileFragment()).commit()

        activityBinding.navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.q -> {
                    activityBinding.title.text="1"
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.user -> {
                        loadFragment(UserProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.e -> {
                    activityBinding.title.text="3"
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }






    }


    fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun vvv(view: View) {
        Auth.removeUserCredentials(this)
    }


}