package mouhamed.dore.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun Film(windowClass: WindowSizeClass, navController: NavController, viewModel: MainViewModel) {
    val movies by viewModel.movies.collectAsState()
    LaunchedEffect(true) {
        viewModel.filmsTendance()
    }
    when (windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 5.dp),
                    columns = GridCells.Fixed(2)
                ) {
                    items(movies) { movie -> CardFilm(movie, navController) }
                }
            }
        }
        else -> {
            LazyRow(
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(movies) { movie -> CardFilm(movie, navController) }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardFilm(film: Movie, navController: NavController) {
    val navController = navController
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        onClick = {
            navController.navigate("filmDetail/${film.id}")
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        )
        {
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500${film.poster_path}",
                ),
                contentDescription = "${film.id}",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "${film.title}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .width(180.dp),
            )
            Text(
                text = "${film.release_date}"
            )
        }
    }
}
