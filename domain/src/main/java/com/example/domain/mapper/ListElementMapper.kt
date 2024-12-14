package com.example.domain.mapper

import com.example.domain.data.entity.ListElement
import com.example.domain.data.repository.LocalStorageRepository
import com.example.domain.entity.ListElementEntity

class ListElementMapper(
    private val localStorageRepository: LocalStorageRepository
) : Mapper<ListElement, ListElementEntity> {
    override suspend fun map(from: ListElement): ListElementEntity {
        return ListElementEntity(
            id = from.id,
            image = from.image,
            title = from.title,
            subtitle = from.subtitle,
            content = from.content,
            button = from.button,
            like = localStorageRepository.isLiked(from.id)
        )
    }
}