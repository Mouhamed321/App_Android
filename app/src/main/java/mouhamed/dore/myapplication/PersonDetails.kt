package mouhamed.dore.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun PersonDetailScreen(personDetail: PersonDetail, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        Row {
            Column {
                Image(
                    painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w500${personDetail.profile_path}",
                    ),
                    contentDescription = personDetail.name,
                    modifier = Modifier
                        .size(180.dp) // You can adjust the height as needed
                )
            }
            Column {

                Row {
                    Text(
                        "${personDetail.name}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h4
                    )
                }
                Row {
                    Text(
                        "Né le ${personDetail.birthday}"
                    )
                }
                Row {
                    Text(
                        "à ${personDetail.place_of_birth}"
                    )
                }
            }
        }
        Text(
            text = "Biographie :",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h4
        )
        Text(text = "${personDetail.biography}", textAlign = TextAlign.Justify)

    }
}