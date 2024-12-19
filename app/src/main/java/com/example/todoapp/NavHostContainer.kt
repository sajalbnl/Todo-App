package com.example.todoapp

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todoapp.ui.AddTaskScreen
import com.example.todoapp.ui.AllTaskScreen
import com.example.todoapp.utils.publicsansBold
import com.example.todoapp.utils.publicsansRegular

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = "addTask",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable("addTask") {
                AddTaskScreen(navController)
            }
            composable("alltasks") {
                AllTaskScreen(navController)
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.AddTask,
        BottomBarScreen.Tasks,
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(start=10.dp,end=10.dp,bottom=50.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color("#bdd5f0".toColorInt()))
            .fillMaxWidth().border(BorderStroke(1.dp, Color("#bdd5f0".toColorInt())),
                RoundedCornerShape(20)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}


@SuppressLint("NewApi")
@Composable
fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    Box(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 12.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // Only navigate if the selected tab is not already active
                if (!selected) {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                    contentDescription = "",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                )
                Text(
                    modifier = Modifier,
                    text = screen.title,
                    fontSize = 12.sp,
                    fontFamily = if(selected) publicsansBold else publicsansRegular,
                    color = if (selected) Color("#000000".toColorInt()) else Color("#333333".toColorInt())
                )
            }
        }
    }
}
