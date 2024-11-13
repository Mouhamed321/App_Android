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
// Fonction pour afficher une liste de films basée sur la classe de taille de la fenêtre (WindowSizeClass)
fun Film(windowClass: WindowSizeClass, navController: NavController, viewModel: MainViewModel) {
    // Récupère l'état actuel des films à partir du ViewModel
    val movies by viewModel.movies.collectAsState()

    // Effect lancé lorsque le composant est créé
    LaunchedEffect(true) {
        // Charge les films tendances
        viewModel.filmsTendance()
    }

    // Utilisation d'une instruction when pour ajuster l'affichage en fonction de la classe de taille de la fenêtre
    when (windowClass.widthSizeClass) {
        // Si la fenêtre est de taille compacte
        WindowWidthSizeClass.Compact -> {
            //LazyVerticalGrid ->afficher les films par 2 en largeur
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 5.dp),
                    columns = GridCells.Fixed(2)  // Affiche 2 colonnes dans la grille
                ) {
                    // Itère sur la liste de films et affiche chaque film en utilisant la fonction CardFilm
                    items(movies) { movie -> CardFilm(movie, navController) }
                }
            }
        }
        // Si la fenêtre n'est pas de taille compacte
        else -> {
            // Utilisation d'une liste horizontale défilante (LazyRow) pour afficher les films un par un
            LazyRow(
                contentPadding = PaddingValues(8.dp),  // Ajoute des marges autour des éléments dans la liste
                modifier = Modifier.fillMaxHeight()  // Occupe toute la hauteur disponible
            ) {
                // Itère sur la liste de films et affiche chaque film en utilisant la fonction CardFilm
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
            // naviguer vers l'écran de détails du film
            navController.navigate("filmDetail/${film.id}")
        }
    ) {
        // Colonnes alignées horizontalement au centre dans la carte
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        )
        {
            // Affiche l'image du film
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500${film.poster_path}",
                ),
                contentDescription = "${film.id}",
                modifier = Modifier
                    .size(180.dp)  // Définit la taille de l'image
                    .align(Alignment.CenterHorizontally)  // Centre l'image horizontalement
            )
            // Affiche le titre
            Text(
                text = "${film.title}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,  // Affiche ... si le texte est trop long
                maxLines = 1,  // Limite le texte à une seule ligne
                modifier = Modifier
                    .width(180.dp),  // Limite la largeur du texte à 180dp
            )
            // Affiche la date de sortie du film
            Text(
                text = "${film.release_date}"
            )
        }
    }
}
