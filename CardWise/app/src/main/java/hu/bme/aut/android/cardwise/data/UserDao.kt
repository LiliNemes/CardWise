package hu.bme.aut.android.cardwise.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE name=:name")
    fun getByName(name: String): User?

    @Query("SELECT * FROM User WHERE id=:userId")
    fun getById(userId: Long): User

    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}