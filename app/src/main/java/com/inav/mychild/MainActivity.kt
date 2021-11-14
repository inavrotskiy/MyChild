package com.inav.mychild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val children = listOf<MyChild>(
            MyChild("Лиза", LocalDate.of(2016, 9, 17), Sex.GIRL),
            MyChild("Степан", LocalDate.of(2013, 6, 23), Sex.BOY),
            MyChild("Егор", LocalDate.of(2011, 8, 20), Sex.BOY),
            MyChild("Вова", LocalDate.of(2002, 4, 22), Sex.BOY),
            MyChild("Женя", LocalDate.of(1998, 10, 16), Sex.BOY),
            MyChild("Мила", LocalDate.of(2017, 2, 10), Sex.GIRL)
        )

        val childrenRecyclerView : RecyclerView = findViewById(R.id.children_recyclerView)
        childrenRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        childrenRecyclerView.adapter = ChildrenRecyclerViewAdapter(children)
    }
}