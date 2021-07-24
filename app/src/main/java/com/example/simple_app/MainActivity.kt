package com.example.simple_app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simple_app.model.User


private lateinit var pref: SharedPreferences
private const val APP_PREFERENCES = "user_storage"
private var isLoggedIn: Boolean = false
private const val patternEmail: String =
        "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}\$"

private val marina = User(
        "Марина",
        "Григорьева",
        "mar@yandex.ru",
        "Cvbf77",
        R.drawable.avatar_1
)
private val alexandr = User(
        "Александр",
        "Павлов",
        "sashaaaa@mail.ru",
        "hcvFFgcwh007",
        R.drawable.avatar_2
)

private val users: MutableList<User> = ArrayList()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        users.add(alexandr)
        users.add(marina)

        val btnSignIn: Button = findViewById(R.id.sign_in)
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)

        btnSignIn.setOnClickListener {
            if(!checkEmail(email.text.toString())){
                Toast.makeText(this, "Некорректный email", Toast.LENGTH_SHORT)
                        .show()
            }
            if(!checkPassword(password.text.toString())){
                Toast.makeText(this, "Некорректный пароль", Toast.LENGTH_SHORT)
                        .show()
            }
            else {
                val currentUser =
                        hasUser(email.text.toString(), password.text.toString())
                if (currentUser != null) {
                    rememberUser(currentUser)
                    goToProfile(currentUser)
                } else {
                    Toast.makeText(this, "Пользователя не существует", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (pref.contains("is_logged_in")) {
            val user = User(pref.getString("first_name", "Имя").toString(),
                    pref.getString("last_name", "Фамилия").toString(),
                    pref.getString("email", "smth@mail.ru").toString(),
                    pref.getString("password", "qWeRtY007").toString(),
                    pref.getInt("photo", 0))
            goToProfile(user)
        }
    }

    private fun hasUser(email: String, password: String): User? {
        for (user in users) {
            if (email == user.email && password == user.password) {
                return user
            }
        }
        return null
    }

    private fun rememberUser(user: User){
        val editor = pref.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.putString("first_name", user.firstName)
        editor.putString("last_name", user.lastName)
        editor.putString("email", user.email)
        editor.putString("password", user.password)
        editor.putInt("photo", user.photo)
        editor.apply()
    }

    private fun goToProfile(user: User){
        val openAccount = Intent(this, ProfileActivity::class.java)
        openAccount.putExtra("full_name", user.fullName)
        openAccount.putExtra("email", user.email)
        openAccount.putExtra("photo", user.photo)
        startActivity(openAccount)
    }

    private fun checkPassword(str: String): Boolean{
        return str.length >= 6 &&
                Regex("[A-Z]").containsMatchIn(str) &&
                Regex("[a-z]").containsMatchIn(str) &&
                Regex("[0-9]").containsMatchIn(str)
    }

    private fun checkEmail(str: String): Boolean{
        return Regex(patternEmail).containsMatchIn(str)
    }
}
