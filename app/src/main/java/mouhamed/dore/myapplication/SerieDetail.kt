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
import mouhamed.dore.myapplication.CastCard
import mouhamed.dore.myapplication.FilmDetail
import mouhamed.dore.myapplication.MainViewModel
import mouhamed.dore.myapplication.SerieDetails


@Composable
fun SerieDetailScreen(serieDetail: SerieDetails, viewModel: MainViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        Text(serieDetail.name, fontWeight = FontWeight.Bold, style = typography.h4)
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${serieDetail.backdrop_path}",
            ),
            contentDescription = serieDetail.name,
            modifier = Modifier
                .fillMaxWidth()
                .size(180.dp)
        )
        Text("")
        Row() {
            Column {
                Image(
                    painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w500${serieDetail.poster_path}",
                    ),
                    contentDescription = serieDetail.name,
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column {
                Row {
                    Text(
                        text = (serieDetail.first_air_date),
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Row {
                    Text(
                        text = "Genres: ${serieDetail.genres.joinToString(", ") { it.name }}",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = Int.MAX_VALUE
                    )

                }
            }
        }
        Text(text = "Synopsis :", fontWeight = FontWeight.Bold, style = typography.h4)
        Text(text = serieDetail.overview, textAlign = TextAlign.Justify)
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
            items(serieDetail.credits.cast.take(10)) { actor ->
                CastCard(actor = actor, navController)
            }
        }

    }
}
