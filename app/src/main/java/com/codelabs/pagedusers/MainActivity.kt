package com.codelabs.pagedusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codelabs.pagedusers.userlist.UserListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, UserListFragment.newInstance())
                .commit()
        }
    }
}