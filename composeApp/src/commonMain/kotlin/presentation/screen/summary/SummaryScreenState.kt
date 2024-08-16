package presentation.screen.summary

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import domain.ToDoTask

data class SummaryScreenState(val task: ToDoTask, val navigator: Navigator) : Screen {
    @Composable
    override fun Content() {
        SummaryScreen(task, navigator)
    }
}