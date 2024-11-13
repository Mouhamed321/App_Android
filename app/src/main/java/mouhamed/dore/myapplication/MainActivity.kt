package mouhamed.dore.myapplication

import BottomAppBarExample
import SerieDetailScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import mouhamed.dore.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Définit le contenu de l'activité avec Jetpack Compose
        setContent {
            // Récupère la classe de taille de la fenêtre (WindowSizeClass)
            val windowSizeClass = calculateWindowSizeClass(this)

            // Initialise le NavController pour gérer la navigation entre les écrans
            val navController = rememberNavController()

            // Initialise le ViewModel pour gérer les données et la logique de l'application
            val viewModel: MainViewModel by viewModels()

            // Définit le thème global de l'application
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Configurer la navigation avec Jetpack Compose Navigation
                    NavHost(
                        navController = navController,
                        startDestination = "image"  // Écran de départ de l'application
                    ) {
                        // Définit les écrans de l'application et leurs composants associés
                        composable("image") {
                            // Affiche l'écran principal avec l'ensemble des films
                            Screen(windowSizeClass, navController)
                        }
                        composable("film") {
                            // Affiche l'écran de liste des films
                            BottomAppBarExample(windowSizeClass, navController, viewModel)
                        }
                        composable("filmscreen") {
                            // Affiche l'écran des films avec la grille ou la liste défilante
                            Film(windowSizeClass, navController, viewModel)
                        }
                        composable("seriescreen") {
                            // Affiche l'écran des séries avec la grille ou la liste défilante
                            Serie(windowSizeClass, navController, viewModel)
                        }
                        composable("personscreen") {
                            // Affiche l'écran des personnes avec la grille ou la liste défilante
                            Persons(windowSizeClass, navController, viewModel)
                        }
                        composable("filmDetail/{filmId}") { backStackEntry ->
                            // Récupère l'ID du film de l'argument passé à partir du BackStackEntry
                            val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull()

                            // Vérifie si l'ID du film est valide (non null)
                            if (filmId != null) {
                                // Appelle la fonction pour récupérer les détails du film par son ID
                                viewModel.filmDetailbyID(filmId)

                                // Récupère l'état actuel des détails du film
                                val filmDetail by viewModel.detailfilm.collectAsState()

                                // Affiche l'écran de détails du film
                                FilmDetailScreen(filmDetail, viewModel, navController)
                            }
                        }
                        composable("serieDetail/{serieId}") { backStackEntry ->
                            // Affiche les détails d'une série en fonction de son ID
                            val serieId = backStackEntry.arguments?.getString("serieId")?.toIntOrNull()
                            if (serieId != null) {
                                viewModel.serieDetailbyID(serieId)
                                val serieDetail by viewModel.detailserie.collectAsState()
                                SerieDetailScreen(serieDetail, viewModel, navController)
                            }
                        }
                        composable("personDetail/{personId}") { backStackEntry ->
                            // Affiche les détails d'une personne en fonction de son ID
                            val personId = backStackEntry.arguments?.getString("personId")?.toIntOrNull()
                            if (personId != null) {
                                viewModel.personDetailbyID(personId)
                                val personDetail by viewModel.detailperson.collectAsState()
                                PersonDetailScreen(personDetail, viewModel)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Screen(windowClass: WindowSizeClass, navController: NavController) {
        when (windowClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp) // Ajoute des marges à l'intérieur du conteneur
                ) {
                    photoProfil()
                    Spacer(modifier = Modifier.height(16.dp))
                    Texte()
                    Spacer(modifier = Modifier.height(16.dp))
                    Adresse()
                    Spacer(modifier = Modifier.height(16.dp))
                    bouton(navController)
                }
            }

            else -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp) // Ajoute des marges à l'intérieur du conteneur
                ) {
                    Column() {
                        photoProfil()
                    }
                    Column() {
                        Texte()
                        Spacer(modifier = Modifier.height(16.dp)) // Ajoute un espace vertical
                        Adresse()
                        Spacer(modifier = Modifier.height(16.dp)) // Ajoute un espace vertical
                        bouton(navController)
                    }
                }
            }
        }
    }

    @Composable
    fun photoProfil() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.shifu),
                contentDescription = "profil",
                contentScale = ContentScale.Crop, // redimensionnement de l'image
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape) //cercle // taille l'espace disponible
            )
            Text(
                text = "Mouhamed DORE",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(10.dp)
            )
        }
    }


    @Composable
    fun Texte() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Etudiant en FIE4",
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Ecole d'ingénieur ISIS",
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }


    @Composable
    fun Adresse() {
        Column() {
            Row(Modifier.padding(top = 20.dp)) {
                Image(
                    painterResource(R.drawable.mail),
                    contentDescription = "Mail",
                    Modifier.size(30.dp)
                )
                Text(text = "mouhamed.dore@etud.univ-jfc.fr")
            }
            Row() {
                Image(
                    painterResource(R.drawable.linkedin),
                    contentDescription = "Linkedin",
                    Modifier.size(30.dp)
                )
                Text(text = "www.linkedin.com")
            }
        }
    }



    @Composable
    fun bouton(navController: NavController) {
        androidx.compose.material3.Button(
            onClick = {
                navController.navigate("film") // naviguer vers l'acceuil de l'appli (affichage des films par défault)
            }
        ) {
            Text(text = "Démarrer")
        }
    }


}

