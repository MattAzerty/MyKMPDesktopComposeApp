package ui.screens

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.DataRepository

class LogScreenModel(
    private val dataRepository: DataRepository
):ScreenModel {

    val string = dataRepository.string
}