package th.co.gis.cdg.checkoryorapplication.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import th.co.gis.cdg.checkoryorapplication.model.Oryor

@Dao
interface UploadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg oryor: Oryor): Completable

    @Query("SELECT * FROM UPLOAD WHERE UPLOAD_ID = :id")
    fun getOryorByID(id: Int): Single<Oryor>

    @Query("SELECT * FROM UPLOAD")
    fun getUpload(): Single<List<Oryor>>

    @Query("DELETE FROM UPLOAD WHERE UPLOAD_ID = :id")
    fun delete(id: String): Completable

}