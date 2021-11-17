package com.inav.mychild

import android.content.Intent
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

val children = listOf<MyChild>(
    MyChild("Лиза", LocalDate.of(2016, 9, 17), Sex.GIRL),
    MyChild("Степан", LocalDate.of(2013, 6, 23), Sex.BOY),
    MyChild("Егор", LocalDate.of(2011, 8, 20), Sex.BOY),
    MyChild("Вова", LocalDate.of(2002, 4, 22), Sex.BOY),
    MyChild("Женя", LocalDate.of(1998, 10, 16), Sex.BOY),
    MyChild("Мила", LocalDate.of(2017, 2, 10), Sex.GIRL)
)

lateinit var bottomMenu : BottomNavigationView
var curChildId = 0


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
                R.id.share -> share(children[curChildId])
            }
            true
        }

        bottomMenu.selectedItemId = savedInstanceState?.getInt(LAST_SELECTED_ITEM) ?: R.id.home
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(LAST_SELECTED_ITEM, bottomMenu.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    /*
    * Replaces current Fragment with a specified one.
    * */
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /*
    * Shares a specified Child using Intent.
    * */
    private fun share(child : MyChild){

        val textToShare = "${child.name} : ${child.ageToString()}"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, textToShare)

        startActivity(intent)
    }
}