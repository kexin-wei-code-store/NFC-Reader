package com.igs.nfc_rw.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.igs.nfc_rw.R
import com.igs.nfc_rw.utils.Logger


@Composable
fun VisitorListUI(navController: NavController) {
    val loggerHead = "VisitorListUI"
    var visitors: List<String>? = null
    val padding = 20f.dp
    // TODO("get visitors from database")
    visitors = listOf(
        "John Doe",
        "Jane Smith",
        "Bob Johnson",
        "Alice Brown",
        "Tom Wilson",
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListOfCards(visitors)
        Button(
            modifier = Modifier.padding(top = padding, bottom = padding),
            onClick = {
                Logger.d(loggerHead, "Button clicked")
                navController.navigate(Routes.WELCOME_UI)
            },
        ) {
            Text(stringResource(R.string.button_go_home))
        }
    }

}

@Composable
private fun ListOfCards(visitors: List<String>?) {
    val padding = 20f.dp
    val cardPadding = 10f.dp
    if (visitors != null) {
        LazyColumn {
            visitors.forEach {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(cardPadding),
                        onClick = {
                            Logger.d("VisitorListUI", "Card clicked")
                            //delete visitor from database
                            //TODO("delete visitor from database")
                            //delete card from list
                            //TODO("delete card from list")
                        }
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                modifier = Modifier.padding(padding),
                                text = it
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListOfCards_Preview() {
    val visitors =
        listOf(
            "John Doe",
            "Jane Smith",
            "Bob Johnson",
            "Alice Brown",
            "Tom Wilson",
            "Sara Lee",
            "John Doe",
            "Jane Smith",
            "Bob Johnson",
            "Alice Brown",
            "Tom Wilson",
            "Sara Lee",
        )
    ListOfCards(visitors)
}

