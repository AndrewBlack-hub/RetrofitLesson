package com.androidgang.lessonapikudago

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PAGE_SIZE = 50
        private const val FIELDS = "id,title,poster"
    }

    private lateinit var disposable: Disposable
    private lateinit var recycler: RecyclerView
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.rv_movies_list)

        disposable = getMovies()
            .subscribeOn(Schedulers.io())//где выполняется (другой поток input/output)
            .observeOn(AndroidSchedulers.mainThread())//где получаем ответ (в главном потоке)
            .doOnNext { moviesResponse -> displayMovies(moviesResponse) }//выводит результат в лог
            .subscribe()
    }

    private fun getMovies(): Observable<MoviesResponse> {
        return NetworkService.getInstance().getJsonApi()
            .getMovies(PAGE_SIZE, FIELDS) //создание http клиента и вызов метода с сервера
    }

    private fun displayMovies(moviesResponse: MoviesResponse) {
        moviesResponse.let {
            homeAdapter = HomeAdapter(this)
            recycler.adapter = homeAdapter
            (recycler.adapter as HomeAdapter).setList(it.result as ArrayList<MoviesResponse.Result>)
        }
    }

//    private fun displayMovies(moviesResponse: MoviesResponse) {
//        val stringBuilder = StringBuilder()
//        moviesResponse.result?.let {
//            it.forEach { result ->
//                Log.e("TAG", result.title)//полученные данные выводим в лог
//                stringBuilder.append(result.title)
//                stringBuilder.append("\n")//новые данные добавляются с новой строки
//            }
//        }
//        titleMovies.text = stringBuilder//готовые данные показываем в TextView
//    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}