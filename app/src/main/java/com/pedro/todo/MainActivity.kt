package com.pedro.todo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.pedro.todo.databinding.ActivityMainBinding
import com.pedro.todo.tasklist.ui.TaskListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerView.id,  TaskListFragment())
            .commit()
    }
}