package org.example.project.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.repository.MockWikidataRepository
import org.example.project.domain.model.WikidataEntity

class WikidataViewModel : ViewModel() {
    private val repository = MockWikidataRepository()

    private val _entities = MutableStateFlow<List<WikidataEntity>>(repository.getEntities())
    val entities: StateFlow<List<WikidataEntity>> = _entities.asStateFlow()

    fun getEntity(id: String): WikidataEntity? {
        return repository.getEntityById(id)
    }
}
