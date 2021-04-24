package com.androidgang.lessonapikudago

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var titleMovies: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleMovies = findViewById(R.id.tv_title_list)

        getMovies()
            .subscribeOn(Schedulers.io())//где выполняется (другой поток input/output)
            .observeOn(AndroidSchedulers.mainThread())//где получаем ответ (в главном потоке)
            .doOnNext { moviesResponse -> displayMovies(moviesResponse) }//выводит результат в лог
            .subscribe()

    }

    private fun getMovies(): Observable<MoviesResponse> {
        return NetworkService.getInstance().getJsonApi().getMovies() //создание http клиента и вызов метода с сервера
    }

    private fun displayMovies(moviesResponse: MoviesResponse) {
        val stringBuilder = StringBuilder()
        moviesResponse.result?.let {
            it.forEach { result -> Log.e("TAG", result.title)
                stringBuilder.append(result.title)
                stringBuilder.append("\n")
            }
        }
        titleMovies.text = stringBuilder
    }
}