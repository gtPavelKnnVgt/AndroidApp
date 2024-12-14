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
            image= "https://www.google.com/search?q=nba+logo&oq=nba+logo&gs_lcrp=EgZjaHJvbWUyCQgAEEUYORiABDIHCAEQABiABDIHCAIQABiABDIHCAMQABiABDIJCAQQABgKGIAEMgcIBRAAGIAEMgcIBhAAGIAEMgcIBxAAGIAEMgcICBAAGIAEMgcICRAAGIAEqAIAsAIA&sourceid=chrome&ie=UTF-8#vhid=Y7raWSxwzbdZJM&vssid=_qFxdZ_7jDYiywPAP67X7oAU_38",
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
            image= "https://www.sportslogos.net/logos/view/56/Chicago_Blackhawks/2000/Primary_Logo",
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
            image= "https://www.sportslogos.net/logos/view/1149791984/Detroit_Red_Wings/1984/Primary_Dark_Logo",
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
            image= "https://www.sportslogos.net/logos/view/2463622017/Pittsburgh_Penguins/2017/Primary_Logo",
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
