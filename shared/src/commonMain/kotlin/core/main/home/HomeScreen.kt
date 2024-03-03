package core.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import util.theme.primary
import util.theme.white

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen() {

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier.fillMaxWidth().clip(
                RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
            ).background(primary).padding(start = 16.dp, end = 16.dp, top = 36.dp, bottom = 24.dp)
        ) {

            Column {

                Text(
                    text = "سلام عادل",
                    style = MaterialTheme.typography.h5,
                    color = white,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "هوای خوبیه برای دوییدن نه؟",
                    color = white
                )

            }

            Image(
                modifier = Modifier.size(40.dp).align(Alignment.TopEnd),
                painter = painterResource("icon_smile.png"),
                contentDescription = null
            )
        }


    }


}