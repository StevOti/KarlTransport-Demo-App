package presentation.screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.TaskAction
import domain.ToDoTask
import presentation.screen.summary.SummaryScreen
import presentation.screen.summary.SummaryScreenState

const val DEFAULT_TITLE = "Enter the Title"
const val DEFAULT_DESCRIPTION = "Add some description"

data class TaskScreen(val task: ToDoTask? = null) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<TaskViewModel>()
        var currentTitle by remember { mutableStateOf(task?.title ?: "") }
        var currentDescription by remember { mutableStateOf(task?.description ?: "") }
        var currentPriority by remember { mutableStateOf(task?.priority ?: "") }
        var currentDueDate by remember { mutableStateOf(task?.dueDate ?: "") }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Task Details")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back Arrow"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                if (currentTitle.isNotEmpty() && currentDescription.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = {
                            val newTask = ToDoTask().apply {
                                title = currentTitle
                                description = currentDescription
                                priority = currentPriority
                                dueDate = currentDueDate
                                if (task != null) {
                                    _id = task._id // Preserve the ID if updating an existing task
                                }
                            }

                            if (task != null) {
                                viewModel.setAction(TaskAction.Update(newTask))
                            } else {
                                viewModel.setAction(TaskAction.Add(newTask))
                            }

                            // Navigate to SummaryScreen
                            navigator.push(SummaryScreenState(newTask, navigator))
                        },
                        shape = RoundedCornerShape(size = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Checkmark Icon"
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 24.dp)
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                OutlinedTextField(
                    value = currentTitle,
                    onValueChange = { title -> currentTitle = title },
                    label = { Text("Enter the Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Description
                OutlinedTextField(
                    value = currentDescription,
                    onValueChange = { description -> currentDescription = description },
                    label = { Text("Add some description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 5
                )

                // Priority
                OutlinedTextField(
                    value = currentPriority,
                    onValueChange = { priority -> currentPriority = priority },
                    label = { Text("Enter priority") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Due Date
                OutlinedTextField(
                    value = currentDueDate,
                    onValueChange = { dueDate -> currentDueDate = dueDate },
                    label = { Text("Enter due date") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}