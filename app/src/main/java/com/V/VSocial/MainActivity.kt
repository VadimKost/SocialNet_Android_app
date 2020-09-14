package com.V.VSocial


import android.os.Bundle
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


        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.q -> {
                }

                R.id.user-> {
                        loadFragment(MainUserFragment())
                }

                R.id.e-> {
                }

            }
            true

        }
        navigationView.selectedItemId=R.id.user

    }

    fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}