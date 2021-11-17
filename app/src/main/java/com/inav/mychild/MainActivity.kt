package com.inav.mychild

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

private const val LAST_SELECTED_ITEM = "last_selected_item"
internal const val CUR_CHILD = "CUR_CHILD"

private val HOME_FRAGMENT = HomeFragment().javaClass.name
private val GRAPH_FRAGMENT = GraphFragment().javaClass.name
private val ABOUT_FRAGMENT = AboutFragment().javaClass.name

val children = listOf(
    MyChild("Лиза", LocalDate.of(2016, 9, 17), Sex.GIRL),
    MyChild("Степан", LocalDate.of(2013, 6, 23), Sex.BOY),
    MyChild("Егор", LocalDate.of(2011, 8, 20), Sex.BOY),
    MyChild("Вова", LocalDate.of(2002, 4, 22), Sex.BOY),
    MyChild("Женя", LocalDate.of(1998, 10, 16), Sex.BOY),
    MyChild("Мила", LocalDate.of(2017, 2, 10), Sex.GIRL)
)

var curChildId = 0
class MainActivity : AppCompatActivity() {
    lateinit var bottomMenu : BottomNavigationView
    private lateinit var toastNotAvailable : Toast
    private var homeFragment = HomeFragment()
    private var graphFragment = GraphFragment()
    private var aboutFragment = AboutFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toastNotAvailable = Toast.makeText(this, "Activity is not available.", Toast.LENGTH_LONG)

        bottomMenu = findViewById(R.id.bottom_navigation)

        bottomMenu.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home ->{
                    val fragment =
                        savedInstanceState?.let {
                            supportFragmentManager.getFragment(it, HOME_FRAGMENT)
                        } ?: homeFragment
                    replaceFragment(fragment)
                }
                R.id.graph ->{
                    val fragment =
                        savedInstanceState?.let {
                            supportFragmentManager.getFragment(it, GRAPH_FRAGMENT)
                        } ?: graphFragment
                    replaceFragment(fragment)
                }
                R.id.about -> {
                    val fragment =
                        savedInstanceState?.let {
                            supportFragmentManager.getFragment(it, ABOUT_FRAGMENT)
                        } ?: aboutFragment
                    replaceFragment(fragment)
                }
                R.id.share ->{
                    share(children[curChildId])
                }
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
            .addToBackStack(null)
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

    override fun startActivity(intent: Intent?) {
        if(intent?.resolveActivity(packageManager) != null)
            super.startActivity(intent)
        else
            toastNotAvailable.show()
    }
}