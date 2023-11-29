package com.rafiul.buzzbulletin.widgets


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rafiul.buzzbulletin.R
import com.rafiul.buzzbulletin.models.Article

@Composable
fun NewRowComponent(page: Int, article: Article) {

    AsyncImage(
        model = article.urlToImage,
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .height(240.dp),
        contentScale = ContentScale.FillBounds,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        error = painterResource(id = R.drawable.ic_launcher_foreground)
    )

    Spacer(modifier = Modifier.size(20.dp))
    article.title?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(all = 4.dp),
            fontFamily = FontFamily.Monospace
        )
    }


    Spacer(modifier = Modifier.size(10.dp))
    article.description?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(all = 4.dp),
        )
    }

    Spacer(modifier = Modifier.size(20.dp))

    AuthorDetailsComponents(
        page = page + 1,
        authorName = article.author,
        sourceName = article.source?.name
    )


}

@Composable
fun AuthorDetailsComponents(authorName: String? = "", sourceName: String? = "", page: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {

        Text(
            text = authorName ?: "N/A",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.SansSerif,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = sourceName ?: "N/A",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.SansSerif,
        )

    }
}