package hu.bme.aut.android.cardwise
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.data.UserDataRepository


class MainActivity : AppCompatActivity(), UserDataRepositoryProvider {

    private lateinit var userDataRepository: UserDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = this.intent.getLongExtra(USER_ID_TAG, -1)

        userDataRepository = UserDataRepository(this, userId)
        setContentView(R.layout.activity_mainmenu)
    }

    override fun getUserDataRepository(): UserDataRepository {
        return userDataRepository
    }

    companion object {
        const val USER_ID_TAG = "UserId"
    }
}

interface UserDataRepositoryProvider {
    fun getUserDataRepository() :UserDataRepository
}
