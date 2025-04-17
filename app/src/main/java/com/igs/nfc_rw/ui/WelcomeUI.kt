package com.igs.nfc_rw.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.igs.nfc_rw.R
import com.igs.nfc_rw.utils.Logger

@Composable
fun WelcomeUI(navController: NavController) {
    val padding = 30f
    val loggerHeader = "WelcomeUI"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding.dp),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(padding.dp)
        ) {
            Text(
                text = stringResource(R.string.greeting_igs),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = padding.dp)
            )
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.nfc),
                contentDescription = "NFC Reading Icon",
            )
            Button(
                onClick = {
                    Logger.d(loggerHeader, "Button clicked")
                    navController.navigate(Routes.ATTENDEE_REGISTER_UI)
                },
            ) {
                Text(stringResource(R.string.button_go_register_staff))
            }

            Button(
                onClick = {
                    Logger.d(loggerHeader, "Button clicked")
                    navController.navigate(Routes.VISITOR_REGISTER_UI)
                },
            ) {
                Text(stringResource(R.string.button_go_register_visitor))
            }

            Button(
                onClick = {
                    Logger.d(loggerHeader, "Button clicked")
                    navController.navigate(Routes.VISITOR_LIST_UI)
                },
                modifier = Modifier.padding(bottom = padding.dp)
            ) {
                Text(stringResource(R.string.button_go_visitor_check_out))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NFCReaderUIPreview() {
    WelcomeUI(navController = rememberNavController())
}