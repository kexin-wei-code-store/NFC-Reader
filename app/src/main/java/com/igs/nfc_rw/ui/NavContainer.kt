package com.igs.nfc_rw.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.igs.nfc_rw.data.NFCReader

@Composable
fun NavContainer(nfcReader: NFCReader? = null) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME_UI
    ) {
        composable(Routes.WELCOME_UI) { WelcomeUI(navController) }
        composable(Routes.ATTENDEE_REGISTER_UI) { AttendeeRegisterUI(navController, nfcReader) }
        composable(Routes.VISITOR_REGISTER_UI) { VisitorRegisterUi(navController) }
        composable(Routes.VISITOR_LIST_UI) { VisitorListUI(navController) }

    }
}