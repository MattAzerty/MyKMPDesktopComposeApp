package ui

import data.repository.DataService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataApplication : KoinComponent {

    private val dataService : DataService by inject()

    init {
        dataService.saveDefaultData()
    }

    fun sayHello(){
        val myString = dataService.getDefaultData()
        println(myString)
    }
}