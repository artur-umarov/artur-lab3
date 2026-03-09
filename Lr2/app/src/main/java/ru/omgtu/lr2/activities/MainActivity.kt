package ru.omgtu.lr2.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.omgtu.lr2.R
import ru.omgtu.lr2.fragments.BaseFragment  // ← ЭТОТ ИМПОРТ НУЖЕН!
import ru.omgtu.lr2.fragments.FirstFragment
import android.content.Intent
import android.widget.Button
import ru.omgtu.lr2.activities.ProfileActivity

class MainActivity : AppCompatActivity(), BaseFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FirstFragment())
                .commit()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onButtonPressed(buttonId: Int) {
        when (buttonId) {
            1 -> Toast.makeText(this, "Хотим открыть второй экран", Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(this, "Хотим показать Toast", Toast.LENGTH_SHORT).show()
        }
    }
}