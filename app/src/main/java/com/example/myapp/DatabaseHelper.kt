package com.example.myapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Здесь ничего не нужно, так как база уже существует
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Обновление базы данных при необходимости
    }

    // Метод для поиска вопросов по префиксу
    fun getQuestionsByPrefix(prefix: String): List<String> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT question FROM questions_answers WHERE question LIKE ?", arrayOf("$prefix%"))
        val questions = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return questions
    }

    // Метод для получения ответа по вопросу
    fun getAnswerByQuestion(question: String): String? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT answer FROM questions_answers WHERE question = ?", arrayOf(question))
        var answer: String? = null
        if (cursor.moveToFirst()) {
            answer = cursor.getString(0)
        }
        cursor.close()
        return answer
    }
}
