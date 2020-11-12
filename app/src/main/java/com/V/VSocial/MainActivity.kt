package com.V.VSocial


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.title =null

        navigationView.selectedItemId=R.id.user
        supportFragmentManager.beginTransaction().replace(R.id.container, MainUserFragment()).commit()

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.q -> {
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.user-> {
                        loadFragment(MainUserFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.e-> {
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
        removeUserCredentials(this)
    }


}