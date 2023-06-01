package com.nt.moviesandroidapp.tmdb.ui.screens.PersonDetails

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nt.moviesandroidapp.R
import com.nt.moviesandroidapp.tmdb.ui.components.DetailsFavIcon
import com.nt.moviesandroidapp.tmdb.ui.components.PopularityIcon
import com.nt.moviesandroidapp.tmdb.ui.model.PersonModel
import com.nt.moviesandroidapp.tmdb.ui.screens.MovieDetails.debugPlaceholder
import com.nt.moviesandroidapp.util.calculateAge
import com.nt.moviesandroidapp.util.formatDate
import com.nt.moviesandroidapp.util.genderMap

@Composable
fun PersonDetailsScreen(
    personDetailsViewModel: PersonDetailsViewModel,
    navController: NavHostController
) {
    val isLoading: Boolean by personDetailsViewModel.isLoading.observeAsState(initial = false)
    val person by personDetailsViewModel.person.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        } else {
            person?.let {
                PersonDetails(it)
                DetailsFavIcon(
                    personDetailsViewModel.isFavMovie(it.id),
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                        .size(42.dp)
                ) { personDetailsViewModel.onFavButtonSelected(it) }
            }
        }
    }
}

@Composable
fun PersonDetails(person: PersonModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = person.name,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(16.dp))

                PopularityIcon(
                    indicatorValue = person.popularity.toInt(),
                    canvasSize = 110.dp,
                    backgroundIndicatorStrokeWidth = 15f,
                    foregroundIndicatorStrokeWidth = 15f,
                    bigTextFontSize = 22.sp,
                    smallTextFontSize = 10.sp
                )
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp)
                    .aspectRatio(9 / 16f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp)),
                    model = "https://image.tmdb.org/t/p/w500/${person.profilePath}",
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.FillWidth,
                    placeholder = debugPlaceholder(debugPreview = R.drawable.poster),

                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(5f)) {
                person.birthday.let { birthday ->
                    DetailText(
                        prefix = "Born: ",
                        detail = "${formatDate(birthday)} (${calculateAge(birthday)} years)"
                    )
                }

                DetailText(prefix = "Place of Birth: ", detail = person.placeOfBirth)

                person.deathday?.let { deathday ->
                    DetailText(prefix = "Death Date: ", detail = formatDate(deathday))
                }
                DetailText(prefix = "Gender: ", detail = genderMap[person.gender] ?: "Non binary")

                person.homepage?.let { homepage ->
                    DetailText(prefix = "Website: ", detail = homepage)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Biography",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = person.biography,
            textAlign = TextAlign.Justify
        )


    }
}

@Composable
fun DetailText(prefix: String, detail: String) {
    Row() {
        Text(
            text = prefix,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = detail,
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
fun PersonDetailsScreenPreview() {
    val person = PersonModel(
        1,
        "Scarlet Johanson",
        "/xndWFsBlClOJFRdhSt4NBwiPq2o.jpg",
        "Thomas Jeffrey Hanks (born July 9, 1956) is an American actor and filmmaker. Known for both his comedic and dramatic roles, Hanks is one of the most popular and recognizable film stars worldwide, and is widely regarded as an American cultural icon.\\n\\nHanks made his breakthrough with leading roles in the comedies Splash (1984) and Big (1988). He won two consecutive Academy Awards for Best Actor for starring as a gay lawyer suffering from AIDS in Philadelphia (1993) and a young man with below-average IQ in Forrest Gump (1994). Hanks collaborated with film director Steven Spielberg on five films: Saving Private Ryan (1998), Catch Me If You Can (2002), The Terminal (2004), Bridge of Spies (2015), and The Post (2017), as well as the 2001 miniseries Band of Brothers, which launched him as a director, producer, and screenwriter.\\n\\nHanks' other notable films include the romantic comedies Sleepless in Seattle (1993) and You've Got Mail (1998); the dramas Apollo 13 (1995), The Green Mile (1999), Cast Away (2000), Road to Perdition (2002), and Cloud Atlas (2012); and the biographical dramas Saving Mr. Banks (2013), Captain Phillips (2013), Sully (2016), and A Beautiful Day in the Neighborhood (2019). He has also appeared as the title character in the Robert Langdon film series, and has voiced Sheriff Woody in the Toy Story film series.\\n\\nDescription above from the Wikipedia article Tom Hanks, licensed under CC-BY-SA, full list of contributors on Wikipedia.",
        "1956-07-09",
        "Concord, California, USA",
        null,
        2,
        null,
        82.989
    )
//    val viewmodel = hiltViewModel<PersonDetailsViewModel>()
    PersonDetails(person = person)
}

