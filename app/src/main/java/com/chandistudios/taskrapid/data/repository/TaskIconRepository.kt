package com.chandistudios.taskrapid.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.chandistudios.taskrapid.data.entity.TaskIcon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskIconRepository() {
    private val iconList = getIconList()

    fun getTaskIconWithId(id: Long): TaskIcon {
        iconList.forEach { ic ->
            if(ic.iconId == id)
                return ic
        }
        return iconList.get(0)
    }

    fun getIconList(): List<TaskIcon> {
        return mutableListOf(
            TaskIcon(iconId = 0, iconName = "Default Task", icon = Icons.Filled.Task),
            TaskIcon(iconId = 1, iconName = "Home/Household", icon = Icons.Filled.Home),
            TaskIcon(iconId = 2, iconName = "Outdoors/Eco-friendly", icon = Icons.Filled.Eco),
            TaskIcon(iconId = 3, iconName = "Work/City", icon = Icons.Filled.EmojiTransportation),
            TaskIcon(iconId = 4, iconName = "Mail", icon = Icons.Filled.Mail),
            TaskIcon(iconId = 5, iconName = "Travel", icon = Icons.Filled.Flight),
            TaskIcon(iconId = 6, iconName = "Shopping", icon = Icons.Filled.ShoppingCart),
            TaskIcon(iconId = 7, iconName = "Payments", icon = Icons.Filled.Payments),
            TaskIcon(iconId = 8, iconName = "School/Studies", icon = Icons.Filled.School),
            TaskIcon(iconId = 9, iconName = "Reading", icon = Icons.Filled.MenuBook),
            TaskIcon(iconId = 10, iconName = "Food/Dining", icon = Icons.Filled.Fastfood),
            TaskIcon(iconId = 11, iconName = "Beverages", icon = Icons.Filled.EmojiFoodBeverage),
            TaskIcon(iconId = 12, iconName = "Wellbeing", icon = Icons.Filled.Accessibility),
            TaskIcon(iconId = 13, iconName = "Sports", icon = Icons.Filled.SportsTennis),
            TaskIcon(iconId = 14, iconName = "Entertainment", icon = Icons.Filled.TheaterComedy),
            TaskIcon(iconId = 15, iconName = "Movies/Series", icon = Icons.Filled.Movie),
            TaskIcon(iconId = 16, iconName = "Gaming", icon = Icons.Filled.SportsEsports),
            TaskIcon(iconId = 17, iconName = "Celebration/Party", icon = Icons.Filled.Celebration),
            TaskIcon(iconId = 18, iconName = "Energy/Energetic", icon = Icons.Filled.Bolt),
            TaskIcon(iconId = 19, iconName = "Favorites", icon = Icons.Filled.Favorite),
            TaskIcon(iconId = 20, iconName = "Misc", icon = Icons.Filled.Category),
        )
    }
}