package com.example.lab6_ph35419.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lab6_ph35419.ui.screens.CinemaSeatBookingScreen
import com.example.lab6_ph35419.ui.screens.createTheaterSeating

// để quản lí trạng thái của danh sách
enum class ListType {
    ROW, COLUMN, GRID
}

// tạo class data mẫu
data class Movie(
    val title: String,
    val year: String,
    val posterUrl: String,
    val shotDescription: String,
    val genre: String,
    val releaseDate: String,
    val duration: String
) {
    companion object {
        fun getSampleMovie() = listOf(
            Movie(
                "Kẻ ăn tạng",
                "2023",
                "https://media.molistar.com/thumb_w/editors/2023/11/06/press-teaser-kah-poster_680.png",
                "con chim",
                "con chó",
                "con mèo",
                "con khỉ"
            ),
            Movie(
                "Kẻ ăn tạng",
                "2023",
                "https://media.molistar.com/thumb_w/editors/2023/11/06/press-teaser-kah-poster_680.png",
                "con chim",
                "con chó",
                "con mèo",
                "con khỉ"
            ),
            Movie(
                "Kẻ ăn tạng",
                "2023",
                "https://media.molistar.com/thumb_w/editors/2023/11/06/press-teaser-kah-poster_680.png",
                "con chim",
                "con chó",
                "con mèo",
                "con khỉ"
            ),
            Movie(
                "Kẻ ăn tạng",
                "2023",
                "https://media.molistar.com/thumb_w/editors/2023/11/06/press-teaser-kah-poster_680.png",
                "con chim",
                "con chó",
                "con mèo",
                "con khỉ"
            ),
            Movie(
                "Kẻ ăn tạng",
                "2023",
                "https://media.molistar.com/thumb_w/editors/2023/11/06/press-teaser-kah-poster_680.png",
                "con chim",
                "con chó",
                "con mèo",
                "con khỉ"
            ),
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MovieScreen(Movie.getSampleMovie())

            CinemaSeatBookingScreen(
                createTheaterSeating(
                    totalRows = 12,
                    totalSeatsPerRow = 9,
                    aislePositionInRow = 4,
                    aislePositionInColumn = 5
                ), totalSeatsPerRow = 9
            )

        }
    }
}

@Composable
fun MovieScreen(movies: List<Movie>) {
    var listType by remember { mutableStateOf(ListType.ROW) }

    Column {

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(onClick = { listType = ListType.ROW }) {
                Text(text = "Row")
            }

            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { listType = ListType.COLUMN }) {
                Text(text = "Column")
            }

            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { listType = ListType.GRID }) {
                Text(text = "Grid")
            }
        }
        when (listType) {
            ListType.ROW -> MovieRow(movies)
            ListType.COLUMN -> MovieColumn(movies)
            ListType.GRID -> MovieGrid(movies)
        }

    }


}


@Composable
fun MovieRow(movies: List<Movie>) {
    LazyRow(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size) { index ->
            MovieItem(movie = movies[index], listType = ListType.ROW)
        }
    }
}

@Composable
fun MovieColumn(movies: List<Movie>) {
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size) { index ->
            MovieColumnItem(
                movie = movies[index], listType =
                ListType.COLUMN
            )
        }
    }
}

@Composable
fun MovieGrid(movies: List<Movie>) {
    val gridState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,

        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies.size) { index ->
            MovieItem(movie = movies[index], listType = ListType.GRID)
        }
    }
}


@Composable
fun MovieColumnItem(movie: Movie, listType: ListType) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            6.dp
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .then(getItemSizeModifier(listType))
                    .wrapContentHeight()
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                BoldValueText(
                    label = "Thời lượng: ", value =
                    movie.duration
                )
                BoldValueText(
                    label = "Khởi chiếu: ", value =
                    movie.releaseDate
                )
                BoldValueText(label = "Thể loại: ", value = movie.genre)
                Text(
                    text = "Tóm tắt nội dung",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = 4.dp, bottom =
                        2.dp
                    )
                )
                Text(
                    text = movie.shotDescription,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 2.dp)
                )
            }
        }
    }
}

// Item áp dụng cho row và staggered
@Composable
fun MovieItem(movie: Movie, listType: ListType) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            6.dp
        ),
    ) {
        Column(
            modifier = Modifier.then(getItemSizeModifier(listType))
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                BoldValueText(
                    label = "Thời lượng: ", value =
                    movie.duration
                )
                BoldValueText(
                    label = "Khởi chiếu: ", value =
                    movie.releaseDate
                )
            }
        }
    }
}


@Composable
fun BoldValueText(
    label: String, value: String, style: TextStyle = MaterialTheme.typography.bodySmall
) {
    Text(buildAnnotatedString {
        append(label)
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(value)
        }
    }, style = style)
}

@Composable
private fun getItemSizeModifier(listType: ListType): Modifier {
    return when (listType) {
        ListType.ROW -> Modifier.width(175.dp)
        ListType.COLUMN -> Modifier
            .width(130.dp)

        ListType.GRID -> Modifier
            .fillMaxWidth()
    }
}

