package mouhamed.dore.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController

@Composable
fun FilmDetailScreen(
    filmDetail: FilmDetail,
    viewModel: MainViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

            .verticalScroll(
                enabled = true,
                state = rememberScrollState()
            )
    ) {


        // Affiche l'image du film
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${filmDetail.backdrop_path}",
            ),
            contentDescription = filmDetail.title,
            modifier = Modifier
                .fillMaxWidth()
                .size(180.dp)
        )

        Text("")

        Row() {
            Column {
                Image(
                    painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w500${filmDetail.poster_path}",
                    ),
                    contentDescription = filmDetail.title,
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column {
                Row {
                    // Affiche le titre du film -> gras et grande taille
                    Text("${filmDetail.title}", fontWeight = FontWeight.Bold, style = typography.h4)
                }
                // Affiche la date de sortie du film
                Row {
                    Text(
                        text = "${(filmDetail.release_date)}",
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Row {
                    Text(
                        text = "Genres: ${filmDetail.genres.joinToString(", ") { it.name }}",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = Int.MAX_VALUE
                    )
                }
            }
        }

        Text(text = "Synopsis :", fontWeight = FontWeight.Bold, style = typography.h4)

        Text(text = "${filmDetail.overview}", textAlign = TextAlign.Justify)

        Text(
            text = "Têtes d'affiche",
            fontWeight = FontWeight.Bold,
            style = typography.h4,
            modifier = Modifier.padding(8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filmDetail.credits.cast.take(10)) { actor ->
                CastCard(actor = actor, navController)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastCard(actor: Cast, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        onClick = {
            navController.navigate("personDetail/${actor.id}")
        }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)  // Ajoute des marges à l'intérieur de la colonne
        )
        {

            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500${actor.profile_path}",
                ),
                contentDescription = "image acteur",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = actor.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .width(180.dp),
            )
            Text(
                text = actor.character
            )
        }
    }
}