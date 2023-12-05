package com.rafiul.buzzbulletin.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rafiul.buzzbulletin.R
import com.rafiul.buzzbulletin.models.Article


@Composable
fun NewRowComponent(page: Int, article: Article) {


    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {
        Card(
            modifier = Modifier.wrapContentSize()
        ) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_foreground)
            )
        }

        Spacer(modifier = Modifier.size(20.dp))
        article.title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(all = 4.dp),
                fontFamily = FontFamily.Monospace,
                color = Color.White
            )
        }


        Spacer(modifier = Modifier.size(10.dp))
        article.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(all = 4.dp),
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        AuthorDetailsComponents(
            authorName = article.author,
            sourceName = article.source?.name
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(color = Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (page + 1).toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }


}

@Composable
fun AuthorDetailsComponents(authorName: String? = "", sourceName: String? = "") {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = authorName ?: "N/A",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = sourceName ?: "N/A",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

    }
}