package com.igs.nfc_rw.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.igs.nfc_rw.R
import com.igs.nfc_rw.utils.Logger

@Composable
fun VisitorRegisterUi(navController: NavHostController) {
    val padding = 30f.dp
    val loggerHead = "VisitorRegisterUi"
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var firstNameError by remember { mutableStateOf(true) }
    var lastNameError by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(top = padding),
            text = stringResource(R.string.staff_register_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.padding(top = padding),
            text = stringResource(R.string.staff_register_description),
            textAlign = TextAlign.Center,
        )
        TextField(
            modifier = Modifier.padding(top = padding),
            value = firstName, onValueChange = {
                firstName = it
                firstNameError = it.isEmpty()
            },
            label = { Text("First Name") },
            singleLine = true,
            supportingText = {
                if (firstNameError) {
                    Text(
                        text = "*First name is required",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        )

        TextField(
            modifier = Modifier.padding(top = padding),
            value = lastName, onValueChange = {
                lastName = it
                lastNameError = it.isEmpty()
            },
            label = { Text("Last Name") },
            singleLine = true,
            supportingText = {
                if (lastNameError) {
                    Text(
                        text = "*Last name is required",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        )

        Button(
            modifier = Modifier.padding(top = padding, bottom = padding),
            onClick = {
                Logger.d(loggerHead, "Button clicked")
                firstNameError = firstName.isEmpty()
                lastNameError = lastName.isEmpty()
                if (firstNameError || lastNameError) return@Button
//                TODO("validate fields and add to database")
                navController.navigate(Routes.WELCOME_UI)
            },
        ) {
            Text(stringResource(R.string.button_register))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VisitorRegisterUi_Preview() {
    VisitorRegisterUi(navController = rememberNavController())
}

