package com.example.data.repository

import com.example.domain.data.entity.ListButton
import com.example.domain.data.entity.ListElement
import com.example.domain.data.repository.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ListRepositoryImpl : ListRepository {

    private val list = listOf(
        ListElement(
            id=  0,
            title=  "Playful Otters",
            subtitle= "Meet the adorable aquatic duo making waves",
            content= "These two otters have been inseparable since birth, delighting visitors with their playful antics at the local zoo.",
            image= "https://avatars.mds.yandex.net/i?id=1d4f460ef92a3d96fb919d7be23d2094_l-12473993-images-thumbs&n=13",
            button = ListButton(
                title= "Learn More",
                url= "https://ya.ru"
                )
            ),
        ListElement(
            id= 1,
            title= "Sleepy Sloths",
            subtitle= "Curious creatures caught napping in the trees",
            content= "These sleepy sloths are known for their leisurely lifestyle and can be observed snoozing high up in the canopy.",
            image= "https://static.mk.ru/upload/entities/2021/08/09/14/articles/facebookPicture/82/7f/7d/7b/8aeb5a415ffef24a5e5ad91cbdcc780f.jpg",
            button = ListButton(
                title= "Learn More",
                url= "https://ya.ru"
            )
        ),
        ListElement(
            id= 2,
            title= "Clever Crows",
            subtitle= "Intelligent birds outsmarting humans with innovative tricks",
            content=  "These crafty crows have been observed using tools and solving complex puzzles to obtain food rewards.",
            image= "https://cdn.culture.ru/images/cbd5474c-67b5-5257-8fd3-42d8ea7aa69e",
            button = ListButton(
                title= "Learn More",
                url= "https://ya.ru"
            )
        ),
        ListElement(
            id= 3,
            title= "Graceful Gazelles",
            subtitle= "Majestic antelopes mesmerize with their elegant movements",
            content=  "These beautiful gazelles are known for their speed and agility, gracefully leaping through the savannah.",
            image= "https://i.pinimg.com/originals/79/d9/bc/79d9bc50bde8a58c69a3ed84d7df7e93.jpg",
            button = ListButton(
                title= "Learn More",
                url= "https://ya.ru"
            )
        )
    )

    override suspend fun getList(): List<ListElement> = withContext(Dispatchers.IO) {
        return@withContext list
    }

    override suspend fun getElement(id: Long): ListElement {
        return list.find { it.id == id }!!
    }
}
