package com.example.myapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var autoCompleteAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация базы данных
        databaseHelper = DatabaseHelper(this)

        // Настройка AutoCompleteTextView
        val searchAutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.searchAutoCompleteTextView)
        autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())
        searchAutoCompleteTextView.setAdapter(autoCompleteAdapter)

        // Обновление подсказок при вводе текста
        searchAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val suggestions = databaseHelper.getQuestionsByPrefix(s.toString())
                autoCompleteAdapter.clear()
                autoCompleteAdapter.addAll(suggestions)
                autoCompleteAdapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Обработка нажатия кнопки поиска
        val searchButton = findViewById<Button>(R.id.searchButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        searchButton.setOnClickListener {
            val question = searchAutoCompleteTextView.text.toString()
            val answer = databaseHelper.getAnswerByQuestion(question)
            resultTextView.text = answer ?: "Ответ не найден"
        }
    }
}
