package com.inav.mychild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.DateFormat
import java.time.LocalDate
import java.util.*

private const val LAST_SELECTED_ITEM = "last_selected_item"
internal const val CUR_CHILD = "CUR_CHILD"

lateinit var bottomMenu : BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomMenu = findViewById(R.id.bottom_navigation)

        bottomMenu.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.help -> replaceFragment(GraphFragment())
                R.id.about -> replaceFragment(AboutFragment())
            }
            true
        }

        bottomMenu.selectedItemId = savedInstanceState?.getInt(LAST_SELECTED_ITEM) ?: R.id.home
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(LAST_SELECTED_ITEM, bottomMenu.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}