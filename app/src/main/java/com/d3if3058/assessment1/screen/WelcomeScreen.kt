package com.d3if3058.assessment1.screen


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d3if3058.assessment1.R
import com.d3if3058.assessment1.navigation.Screen
import com.d3if3058.assessment1.ui.theme.Assessment1Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navController: NavHostController){
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.welcome )) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.Home.route)}) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_task),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ){
            paddingValues -> GambarWelcome(Modifier.padding(paddingValues))
    }
}


@Composable
fun GambarWelcome(modifier: Modifier){
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = R.drawable.welcome) ,
            contentDescription = stringResource(id = R.string.gambar_welcome),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.kalimat_welcome),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun WelcomePagePreview() {
    Assessment1Theme {
        WelcomeScreen(rememberNavController())
    }
}