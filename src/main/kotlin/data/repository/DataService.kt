package data.repository

import data.MyData
import org.koin.core.annotation.Single
import utils.DEFAULT_DATA
import utils.DEFAULT_DATAS

@Single
class DataService(
    private val dataRepository: DataRepository
) {

    fun getDefaultData() : MyData = dataRepository.findString(DEFAULT_DATA.myString) ?: error("Can't find default value")

    fun saveDefaultData() {
        dataRepository.addString(DEFAULT_DATAS)
    }
}