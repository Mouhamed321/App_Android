package mouhamed.dore.myapplication

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
import BottomAppBarExample
import SerieDetailScreen




class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val navController = rememberNavController()
            val viewModel: MainViewModel by viewModels()


            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,

                        startDestination = "image"
                    ) {
                        composable("image") {
                            Screen(windowSizeClass, navController)
                        }
                        composable("film") {
                            BottomAppBarExample(windowSizeClass, navController, viewModel)
                        }
                        composable("filmscreen") {
                            Film(windowSizeClass, navController, viewModel)
                        }

                        composable("NvelleDestination") {
                            NewDestination(windowSizeClass, navController, viewModel)
                        }

                        composable("seriescreen") {
                            Serie(windowSizeClass, navController, viewModel)
                        }
                        composable("personscreen") {
                            Persons(windowSizeClass, navController, viewModel)
                        }
                        composable("filmDetail/{filmId}") { backStackEntry ->
                            val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull()
                            if (filmId != null) {
                                viewModel.filmDetailbyID(filmId)
                                val filmDetail by viewModel.detailfilm.collectAsState()
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
                        .padding(16.dp)
                ) {
                    photoProfil()
                    Spacer(modifier = Modifier.height(16.dp))
                    Texte()
                    Spacer(modifier = Modifier.height(16.dp))
                    Adresse()
                    Spacer(modifier = Modifier.height(16.dp))
                    bouton(navController)
                    NvlleDestination(navController)
                }
            }

            else -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column() {
                        photoProfil()
                    }
                    Column() {
                        Texte()
                        Spacer(modifier = Modifier.height(16.dp))
                        Adresse()
                        Spacer(modifier = Modifier.height(16.dp))
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
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
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
                navController.navigate("film")
            }
        ) {
            Text(text = "Démarrer")
        }
    }

    @Composable
    fun NvlleDestination(navController: NavController){
        androidx.compose.material3.Button(
            onClick = {
                navController.navigate("NewDestination")
            }
        ) {
            Image(
                painterResource (R.drawable.pouce) ,
                contentDescription = "pouce")
        }

        }


}

