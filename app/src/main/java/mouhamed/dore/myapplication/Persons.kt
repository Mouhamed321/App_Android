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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
// Fonction pour afficher une liste de personnes basée sur la classe de taille de la fenêtre
fun Persons(windowClass: WindowSizeClass, navController: NavController, viewModel: MainViewModel) {
    // Récupère l'état actuel des personnes à partir du ViewModel
    val persons by viewModel.persons.collectAsState()

    // Effect lancé lorsque le composant est créé
    LaunchedEffect(true) {
        // Charge les acteurs tendances
        viewModel.acteursTendance()
    }

    // juster l'affichage en fonction de la taille de la fenêtre
    when (windowClass.widthSizeClass) {
        // Si la fenêtre est de taille compacte
        WindowWidthSizeClass.Compact -> {
            //LazyVerticalGrid -> afficher les personnes par 2 en largeur
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()  // Occupe toute la taille disponible de l'écran
                        .padding(bottom = 5.dp),  // Ajoute des marges en bas
                    columns = GridCells.Fixed(2)  // Affiche 2 colonnes dans la grille
                ) {
                    items(persons) { person -> CardPerson(person, navController) }
                }
            }
        }
        // Si la fenêtre n'est pas de taille compacte
        else -> {
            // Utilisation d'une liste horizontale défilante (LazyRow)
            LazyRow(
                contentPadding = PaddingValues(8.dp),  // Ajoute des marges autour des éléments dans la liste
                modifier = Modifier.fillMaxHeight()  // Occupe toute la hauteur disponible
            ) {
                items(persons) { person -> CardPerson(person, navController) }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPerson(person: Person, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        onClick = {
            // naviguer vers l'écran de détails de la personne
            navController.navigate("personDetail/${person.id}")
        }
    ) {
        // Colonnes alignées horizontalement au centre dans la carte
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Affiche l'image de la personne s'il y en a une, sinon affiche une icône par défaut
            Image(
                painter = if (person.profile_path != null) {
                    rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${person.profile_path}")
                } else {
                    // Sinon, utilise une icône par défaut
                    painterResource(R.drawable.acteur)
                },
                contentDescription = person.name,
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)  // Centre l'image horizontalement
            )
            // Affiche le nom
            Text(
                text = person.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,  // Affiche ... si le texte est trop long
                maxLines = 1,  // Limite le texte à une seule ligne
                modifier = Modifier
                    .width(180.dp),  // Limite la largeur du texte à 180dp
            )
        }
    }
}
