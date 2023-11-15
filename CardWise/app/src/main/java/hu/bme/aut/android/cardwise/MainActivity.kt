package hu.bme.aut.android.cardwise
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.cardwise.data.DataRepository


class MainActivity : AppCompatActivity(), DataRepositoryProvider {

    private lateinit var dataRepository: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataRepository = DataRepository(this)
        //dataRepository.createTestData()
        setContentView(R.layout.activity_mainmenu)
    }

    override fun getDataRepository(): DataRepository {
        return dataRepository
    }
}

interface DataRepositoryProvider {
    fun getDataRepository() :DataRepository
}
