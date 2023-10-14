package data.repository

import data.MyData
import org.koin.core.annotation.Single

interface DataRepository {
    abstract val string: String

    fun findString(myString : String): MyData?
    fun addString(strings : List<MyData>)
}

@Single
class DataRepositoryImpl : DataRepository {

    private val _users = arrayListOf<MyData>()
    override val string = "myString"
    override fun findString(myString: String): MyData? {
        return _users.firstOrNull { it.myString == myString }
    }

    override fun addString(strings : List<MyData>) {
        _users.addAll(strings)
    }
}