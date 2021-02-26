package com.v.vsocial.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.v.vsocial.R
import com.v.vsocial.api.Auth
import com.v.vsocial.viewmodels.UserProfileVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.title =null

        navigationView.selectedItemId= R.id.user
        supportFragmentManager.beginTransaction().replace(R.id.container, UserProfileFragment()).commit()

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.q -> {
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.user -> {
                        loadFragment(UserProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.e -> {
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