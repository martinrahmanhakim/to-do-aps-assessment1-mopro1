package com.d3if3058.assessment1.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.RadioButton
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d3if3058.assessment1.R
import com.d3if3058.assessment1.data.Task
import com.d3if3058.assessment1.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = { Text(text = stringResource(id = R.string.app_name )) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {navController.navigate(Screen.About.route)}) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ){
            paddingValues -> ScreenContent(Modifier.padding(paddingValues));
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        var tasks by rememberSaveable {
            mutableStateOf(listOf<Task>())
        }
        var judul by rememberSaveable {
            mutableStateOf("")
        }
        var judulError by rememberSaveable {
            mutableStateOf(false)
        }
        var isi by rememberSaveable {
            mutableStateOf("")
        }
        var isiError by rememberSaveable {
            mutableStateOf(false)
        }
        val radioOptions = listOf (
            stringResource(id = R.string.prioritas),
            stringResource(id = R.string.non_prioritas)
        )
        var priority by rememberSaveable {
            mutableStateOf((radioOptions[0]))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = judul,
                onValueChange = {
                    judul = it
                    judulError = it.isEmpty()
                },
                label = { Text(text = stringResource(R.string.labelJudul)) },
                isError = judulError,
                trailingIcon = { IconPicker(judulError, "") },
                supportingText = { Errorhint(judulError) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = isi,
                onValueChange = {
                    isi = it
                    isiError = it.isEmpty()
                },
                label = { Text(text = stringResource(R.string.labelIsi)) },
                isError = isiError,
                trailingIcon = { IconPicker(isiError, "") },
                supportingText = { Errorhint(isiError) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            ) {
                radioOptions.forEach{
                        text ->
                    PriorityOption(
                        label = text,
                        isSelected = priority == text,
                        modifier = Modifier
                            .selectable(
                                selected = priority == text,
                                onClick = { priority = text },
                                role = Role.RadioButton
                            )
                            .padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (judul.isNotBlank() && isi.isNotBlank()) {
                    tasks = tasks + Task(
                        id = tasks.size,
                        judul = judul,
                        isi = isi,
                        isCompleted = false,
                        priority = priority

                    )
                    judul = ""
                    isi = ""
                } else {
                    judulError = judul.isBlank()
                    isiError = isi.isBlank()
                }
            }) {
                Text(stringResource(id = R.string.tambah_task))
            }
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(tasks.sortedByDescending { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onDelete = { tasks = tasks.filter { it.id != task.id }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            RadioButton(selected = true, onClick = null)
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        } else {
            RadioButton(selected = false, onClick = null)
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit) {
    val context = LocalContext.current
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.padding(4.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = task.judul,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = task.isi,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            if (task.priority == stringResource(id = R.string.prioritas)) {
                Icon(
                    imageVector = Icons.Default.Star, // Icon prioritas
                    contentDescription = stringResource(id = R.string.prioritas),
                    tint = Color.Red
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(id = R.string.hapusTugas))
                }
                Spacer(modifier = Modifier.height(7.dp))
                Button(
                    onClick = {
                        shareData(
                            context = context,
                            message = context.getString(R.string.bagikan_template, task.judul, task.isi),
                            status = task.priority
                        )
                    },
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = stringResource(id = R.string.bagikan),
                        Modifier.scale(0.8f)
                    )
                }
            }
        }
    }
}

private fun shareData(context: Context, message: String, status: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        val messageWithStatus = if (status.isNotBlank()) {
            "$message \nStatus: $status"
        } else {
            message
        }
        putExtra(Intent.EXTRA_TEXT, messageWithStatus)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun Errorhint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewToDoApp() {
    MainScreen(rememberNavController())
}