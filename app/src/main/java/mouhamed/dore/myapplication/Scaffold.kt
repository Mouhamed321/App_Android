package mouhamed.dore.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mouhamed.dore.myapplication.Film
import mouhamed.dore.myapplication.MainViewModel
import mouhamed.dore.myapplication.Persons
import mouhamed.dore.myapplication.R
import mouhamed.dore.myapplication.Serie


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBarExample(
    windowClass: WindowSizeClass,
    navController: NavController,
    viewModel: MainViewModel
) {
    var value by remember { mutableStateOf("films") }
    var searchVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    when (windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            if (searchVisible) {
                                TextField(
                                    value = searchQuery,
                                    onValueChange = {
                                        searchQuery = it
                                        when (value) {
                                            "series" -> viewModel.searchSeries(searchQuery)
                                            "films" -> viewModel.searchMovies(searchQuery)
                                            "acteurs" -> viewModel.searchPersons(searchQuery)
                                        }
                                    },
                                    placeholder = { Text("Rechercher") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                searchVisible = !searchVisible
                                if (!searchVisible) {
                                    searchQuery = ""
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon"
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                    ) {

                        BottomAppBar(
                            contentPadding = PaddingValues(top = 1.dp, bottom = 1.dp),
                            contentColor = LocalContentColor.current
                        ) {

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { value = "films" }) {
                                    Image(
                                        painter = painterResource(R.drawable.film),
                                        contentDescription = "Films",
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                                Text(text = "Films", color = Color.Black) // Texte blanc
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { value = "series" }) {
                                    Image(
                                        painter = painterResource(R.drawable.serie),
                                        contentDescription = "Séries",
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                                Text(text = "Séries", color = Color.Black)
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { value = "acteurs" }) {
                                    Image(
                                        painter = painterResource(R.drawable.acteur),
                                        contentDescription = "Acteurs",
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                                Text(text = "Acteurs", color = Color.Black)
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (value) {
                            "films" -> Film(windowClass, navController, viewModel)
                            "series" -> Serie(windowClass, navController, viewModel)
                            "acteurs" -> Persons(windowClass, navController, viewModel)
                            else -> Text(text = "Contenu inconnu")
                        }
                    }
                }
            }
        }

        else -> {
            var value by remember { mutableStateOf("films") }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(72.dp)
                    .background(MaterialTheme.colorScheme.primary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = { value = "films" }) {
                    Image(
                        painter = painterResource(R.drawable.film),
                        contentDescription = "Films",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(text = "Films", color = Color.Black) // Texte blanc

                Spacer(modifier = Modifier.height(8.dp))

                IconButton(onClick = { value = "series" }) {
                    Image(
                        painter = painterResource(R.drawable.serie),
                        contentDescription = "Séries",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(text = "Séries", color = Color.Black)


                Spacer(modifier = Modifier.height(8.dp))

                IconButton(onClick = { value = "acteurs" }) {
                    Image(
                        painter = painterResource(R.drawable.acteur),
                        contentDescription = "Acteurs",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(text = "Acteurs", color = Color.Black)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 72.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (value) {
                    "films" -> Film(windowClass, navController, viewModel)
                    "series" -> Serie(windowClass, navController, viewModel)
                    "acteurs" -> Persons(windowClass, navController, viewModel)
                    else -> Text(text = "Contenu inconnu")
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.Gray, CircleShape)
                        .clickable {
                        },
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                        searchVisible = !searchVisible
                        if (!searchVisible) {
                            searchQuery = ""
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    }

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                if (searchVisible) {
                    TextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            when (value) {
                                "series" -> viewModel.searchSeries(searchQuery)
                                "films" -> viewModel.searchMovies(searchQuery)
                                "acteurs" -> viewModel.searchPersons(searchQuery)
                            }
                        },
                        placeholder = { Text("Rechercher") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}