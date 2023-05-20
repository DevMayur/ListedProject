package com.codestart.listed.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.codestart.listed.R
import com.codestart.listed.data.api.Link


@Composable
fun displayLinks(links: List<Link>) {
    LazyColumn(modifier = Modifier.height(500.dp)) { // change 200.dp to the height you want
        items(links) { link ->
            // Display each link
            LinkCard(link, LocalContext.current) // Replace with your own UI
        }
    }
}


@Composable
fun LinkCard(link: Link, context: Context) {
    val clipboardManager = LocalClipboardManager.current

    Box( modifier = Modifier.padding(12.dp) ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(64.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(if (link.thumbnail != "null") link.original_image else link.thumbnail),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp) // fixed size
                            .clip(RoundedCornerShape(8.dp)) // round corners with a radius of 8dp
                            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp)) // light border
                    )



                    Spacer(Modifier.width(8.dp))

                    Column(
                        modifier = Modifier.weight(6f) // 60% of parent's width
                    ) {
                        Text(
                            text = "${link.title}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "${link.created_at}", style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF999CA0)
                            )
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Column(
                        modifier = Modifier.weight(2f) // 20% of parent's width
                    ) {
                        Text(
                            text = "${link.total_clicks}", style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Clicks", style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF999CA0)
                            )
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .background(color = Color(0xFFA6C7FF)) // moved up
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clickable {
                            clipboardManager.setText(AnnotatedString(link.smart_link))
                            Toast.makeText(
                                context,
                                "Link copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = link.smart_link,
                        style = TextStyle(
                            color = Color(0xFF0E6FFF)
                        ),
                        modifier = Modifier

                    )
                    Image(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(18.dp),
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = stringResource(id = R.string.settings_image_description)
                    )
                }

            }
        }
    }
}
