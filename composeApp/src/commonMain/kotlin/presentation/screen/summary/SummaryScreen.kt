package presentation.screen.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import domain.ToDoTask
import presentation.screen.home.HomeScreen



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(task: ToDoTask, navigator: Navigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Task Summary")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.pop()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween // Arrange content with space between
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Title: ${task.title}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Description
                Text(
                    text = "Description: ${task.description}",
                    style = MaterialTheme.typography.bodyMedium
                )

                // Priority
                Text(
                    text = "Priority: ${task.priority}",
                    style = MaterialTheme.typography.bodyMedium
                )

                // Due Date
                Text(
                    text = "Due Date: ${task.dueDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Spacer to push the FAB to the bottom
            Spacer(modifier = Modifier.weight(1f))

            FloatingActionButton(
                onClick = {
                    navigator.popUntilRoot()
                    navigator.push(HomeScreen())
                },
                modifier = Modifier
                    .align(Alignment.End), // Align FAB to the end of the screen
                shape = RoundedCornerShape(size = 12.dp) // Same shape as TaskScreen FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checkmark Icon"
                )
            }
        }
    }
}

