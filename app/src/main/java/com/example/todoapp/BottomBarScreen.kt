package com.example.todoapp

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {

    object AddTask: BottomBarScreen(
        route = "addTask",
        title = "Add Task",
        icon = R.drawable.add_note,
        icon_focused = R.drawable.add_note_active
    )

    object Tasks : BottomBarScreen(
        "allTasks",
        title = "All Task",
        icon = R.drawable.todo_list,
        icon_focused = R.drawable.todo_list_active
    )
}