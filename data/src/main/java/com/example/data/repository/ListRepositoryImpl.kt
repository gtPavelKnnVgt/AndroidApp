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
            title=  "NBA LOGO",
            subtitle= "Accepted Basketball LOGO",
            content= "Actual logo was accepted by association basketball commit",
            image= "https://images.seeklogo.com/logo-png/24/1/nba-logo-png_seeklogo-247736.png?v=638687141570000000",
            button = ListButton(
                title= "Learn More",
                url= "https://ya.ru"
                )
            ),
        ListElement(
            id= 1,
            title= "NHL TEAM LOGO - CHICAGO",
            subtitle= "Accepted Hockey team LOGO",
            content= "Chicago Blackhawks LOGO was accepted in 1999 till present.",
            image= "https://images.footballfanatics.com/chicago-blackhawks/wincraft-chicago-blackhawks-logo-auto-emblem_pi4297000_ff_4297695-ee65a611fc316ae550a7_full.jpg?_hv=2",
            button = ListButton(
                title= "Learn More",
                url= "https://ya.ru"
            )
        ),
        ListElement(
            id= 2,
            title= "NHL TEAM LOGO - DETROIT",
            subtitle= "Accepted Hockey team LOGO",
            content=  "Detroit Red Wings Logo was accepted 1984 till present.",
            image= "https://upload.wikimedia.org/wikipedia/en/thumb/e/e0/Detroit_Red_Wings_logo.svg/1200px-Detroit_Red_Wings_logo.svg.png",
            button = ListButton(
                title= "Learn More",
                url= "https://ya.ru"
            )
        ),
        ListElement(
            id= 3,
            title= "NHL TEAM LOGO - PITTSBURGH",
            subtitle= "Accepted Hockey team LOGO",
            content=  "Pittsburgh Penguins Logo was accepted 2016 till present.",
            image= "https://upload.wikimedia.org/wikipedia/en/thumb/c/c0/Pittsburgh_Penguins_logo_%282016%29.svg/1200px-Pittsburgh_Penguins_logo_%282016%29.svg.png",
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
