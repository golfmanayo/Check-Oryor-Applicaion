package th.co.gis.cdg.checkoryorapplication.database

import android.content.Context
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import th.co.gis.cdg.checkoryorapplication.model.Oryor

open class DatabaseManager(context: Context) {
    protected val database =
        Room.databaseBuilder(context, AppDatabase::class.java, "oryor.db")
            .build()

    companion object {
        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager {
            return INSTANCE ?: DatabaseManager(context)
        }
    }

    fun insertUpload(uploadList: Array<Oryor>): Completable {
        return database.uploadDao().insert(*uploadList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { database.close() }
    }

    fun getOryorByID(id: Int) : Single<Oryor>{
        return database.uploadDao().getOryorByID(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { database.close() }
    }

    fun getUpload(): Single<List<Oryor>> {
        return database.uploadDao().getUpload()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { database.close() }
    }

    fun deleteUpload(id: String): Completable {
        return database.uploadDao().delete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { database.close() }
    }


}