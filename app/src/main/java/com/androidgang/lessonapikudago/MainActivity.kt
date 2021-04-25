package com.androidgang.lessonapikudago

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var titleMovies: TextView
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private val url = "https://img1.fonwall.ru/o/xq/blue-flowers-stairs-plants-gate.jpeg"

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleMovies = findViewById(R.id.tv_title_list)
        imageView = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.pb_loader)

        Glide.with(this)//передаем контекст (activity)
            .load(url)//откуда загружаем
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)//куда вставляем(загружаем)

        disposable = getMovies()
            .subscribeOn(Schedulers.io())//где выполняется (другой поток input/output)
            .observeOn(AndroidSchedulers.mainThread())//где получаем ответ (в главном потоке)
            .doOnNext { moviesResponse -> displayMovies(moviesResponse) }//выводит результат в лог
            .subscribe()

    }

    private fun getMovies(): Observable<MoviesResponse> {
        return NetworkService.getInstance().getJsonApi()
            .getMovies(50) //создание http клиента и вызов метода с сервера
    }

    private fun displayMovies(moviesResponse: MoviesResponse) {
        val stringBuilder = StringBuilder()
        moviesResponse.result?.let {
            it.forEach { result ->
                Log.e("TAG", result.title)//полученные данные выводим в лог
                stringBuilder.append(result.title)
                stringBuilder.append("\n")//новые данные добавляются с новой строки
            }
        }
        titleMovies.text = stringBuilder//готовые данные показываем в TextView
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}