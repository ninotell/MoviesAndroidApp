package com.cursokotlin.moviesandroidapp.movies.ui.PopularMovies

import androidx.compose.runtime.Composable
import com.cursokotlin.moviesandroidapp.movies.data.Genre
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel


val movies = listOf<MovieModel>(
    MovieModel(
        299536,
        "Avengers: Infinity War",
        "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
        358.99,
        8.3,
        "2018-04-25",
        "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
        "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg",
        listOf(
            Genre(28, "Horror"),
            Genre(12, "Thriller")
        )
    ),
    MovieModel(
        550,
        "Fight Club",
        "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
        71.12,
        8.72,
        "1999-10-15",
        "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
        listOf(
            Genre(1, "Horror"),
            Genre(2, "Thriller")
        )
    ),
    MovieModel(
        550,
        "Fight Club",
        "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
        71.12,
        8.72,
        "1999-10-15",
        "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
        listOf(
            Genre(1, "Horror"),
            Genre(2, "Thriller")
        )
    ),
    MovieModel(
        550,
        "Fight Club",
        "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
        71.12,
        8.72,
        "1999-10-15",
        "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
        listOf(
            Genre(1, "Horror"),
            Genre(2, "Thriller")
        )
    ), MovieModel(
        550,
        "Fight Club",
        "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
        71.12,
        8.72,
        "1999-10-15",
        "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
        listOf(
            Genre(1, "Horror"),
            Genre(2, "Thriller")
        )
    )
)

@Composable
fun PopularMoviesScreen() {

}