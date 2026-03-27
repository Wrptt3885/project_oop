// Frontend/src/commonMain/kotlin/com/nexfin/frontend/Main.kt
package com.nexfin.frontend

import com.nexfin.frontend.di.AppModule
import com.nexfin.frontend.ui.navigation.AppDestination
import com.nexfin.frontend.ui.navigation.NavGraph
import com.nexfin.frontend.ui.screens.auth.LoginScreen
import com.nexfin.frontend.ui.screens.auth.RegisterScreen
import com.nexfin.frontend.ui.screens.components.ErrorDialog
import com.nexfin.frontend.ui.screens.home.DashboardScreen
import com.nexfin.frontend.ui.screens.profile.ProfileScreen
import com.nexfin.frontend.ui.screens.transaction.TopUpScreen
import com.nexfin.frontend.ui.screens.transaction.TransactionHistoryScreen
import com.nexfin.frontend.ui.screens.transaction.TransferScreen
import com.nexfin.frontend.ui.theme.NexFinTheme
import com.nexfin.frontend.utils.AppLogger
import com.nexfin.frontend.utils.ValidationUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun NexFinApp(
    appModule: AppModule? = null
) {
    val module = appModule ?: remember { AppModule() }
    val authState by module.authViewModel.uiState.collectAsState()
    val walletState by module.walletViewModel.uiState.collectAsState()
    val transactionState by module.transactionViewModel.uiState.collectAsState()
    var destination by remember { mutableStateOf<AppDestination>(NavGraph.startDestination) }

    var loginEmail by remember { mutableStateOf("alice@example.com") }
    var loginPassword by remember { mutableStateOf("StrongPass1") }
    var registerName by remember { mutableStateOf("") }
    var registerEmail by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }
    var topUpAmount by remember { mutableStateOf("500") }
    var topUpReference by remember { mutableStateOf("PROMPTPAY_TOPUP_001") }
    var transferUserId by remember { mutableStateOf("") }
    var transferAmount by remember { mutableStateOf("125") }
    var transferReference by remember { mutableStateOf("PAYMENT_001") }
    var pendingTopUp by remember { mutableStateOf(false) }
    var pendingTransfer by remember { mutableStateOf(false) }
    val currentUserId = authState.userId
    val currentEmail = authState.email

    LaunchedEffect(authState.isLoggedIn, currentUserId, currentEmail) {
        if (authState.isLoggedIn && currentUserId != null && currentEmail != null) {
            AppLogger.info("NexFinApp", "Login state committed. Navigating to dashboard for userId=$currentUserId")
            module.sharedViewModel.setSession(currentUserId, currentEmail)
            module.walletViewModel.loadWallet(currentUserId)
            module.transactionViewModel.loadTransactions(currentUserId)
            destination = AppDestination.Dashboard
        }
    }

    LaunchedEffect(walletState.isLoading, walletState.wallet, walletState.errorMessage, currentUserId, pendingTopUp) {
        if (pendingTopUp && !walletState.isLoading) {
            if (walletState.errorMessage == null) {
                AppLogger.info("NexFinApp", "Top-up completed. Returning to dashboard.")
                currentUserId?.let { module.transactionViewModel.loadTransactions(it) }
                destination = AppDestination.Dashboard
            }
            pendingTopUp = false
        }
    }

    LaunchedEffect(
        transactionState.isLoading,
        transactionState.lastTransaction,
        transactionState.errorMessage,
        currentUserId,
        pendingTransfer
    ) {
        if (pendingTransfer && !transactionState.isLoading) {
            if (transactionState.errorMessage == null && transactionState.lastTransaction != null) {
                AppLogger.info("NexFinApp", "Transfer completed. Returning to dashboard.")
                currentUserId?.let {
                    module.walletViewModel.loadWallet(it)
                    module.transactionViewModel.loadTransactions(it)
                }
                destination = AppDestination.Dashboard
            }
            pendingTransfer = false
        }
    }

    val errorMessage = authState.errorMessage ?: walletState.errorMessage ?: transactionState.errorMessage

    NexFinTheme {
        when (destination) {
            AppDestination.Login -> LoginScreen(
                email = loginEmail,
                password = loginPassword,
                isLoading = authState.isLoading,
                onEmailChange = { loginEmail = it },
                onPasswordChange = { loginPassword = it },
                onSubmit = { // <--- แก้ไขแล้ว
                    if (ValidationUtils.isValidEmail(loginEmail) && ValidationUtils.isValidPassword(loginPassword)) {
                        module.authViewModel.login(loginEmail, loginPassword)
                    } else {
                        AppLogger.error(
                            "NexFinApp",
                            "Login validation blocked request. emailValid=${ValidationUtils.isValidEmail(loginEmail)} passwordValid=${ValidationUtils.isValidPassword(loginPassword)}"
                        )
                    }
                },
                onRegisterNavigate = { destination = AppDestination.Register } // <--- แก้ไขแล้ว
            )

            AppDestination.Register -> RegisterScreen(
                fullName = registerName,
                email = registerEmail,
                password = registerPassword,
                isLoading = authState.isLoading,
                onFullNameChange = { registerName = it },
                onEmailChange = { registerEmail = it },
                onPasswordChange = { registerPassword = it },
                onSubmit = { // <--- แก้ไขแล้ว
                    if (registerName.isNotBlank() &&
                        ValidationUtils.isValidEmail(registerEmail) &&
                        ValidationUtils.isValidPassword(registerPassword)
                    ) {
                        module.authViewModel.register(registerName, registerEmail, registerPassword)
                    } else {
                        AppLogger.error(
                            "NexFinApp",
                            "Register validation blocked request. nameBlank=${registerName.isBlank()} emailValid=${ValidationUtils.isValidEmail(registerEmail)} passwordValid=${ValidationUtils.isValidPassword(registerPassword)}"
                        )
                    }
                },
                onLoginNavigate = { destination = AppDestination.Login } // <--- แก้ไขแล้ว
            )

            AppDestination.Dashboard -> DashboardScreen(
                wallet = walletState.wallet,
                transactions = transactionState.transactions,
                userEmail = currentEmail ?: module.sharedViewModel.sessionState.value.currentEmail.orEmpty(),
                isLoading = walletState.isLoading || transactionState.isLoading,
                onRefresh = {
                    currentUserId?.let {
                        module.walletViewModel.loadWallet(it)
                        module.transactionViewModel.loadTransactions(it)
                    }
                },
                onTopUpNavigate = { destination = AppDestination.TopUp },
                onTransferNavigate = { destination = AppDestination.Transfer },
                onHistoryNavigate = { destination = AppDestination.History },
                onProfileNavigate = { destination = AppDestination.Profile }
            )

            AppDestination.TopUp -> TopUpScreen(
                amount = topUpAmount,
                reference = topUpReference,
                isLoading = walletState.isLoading,
                onAmountChange = { topUpAmount = it },
                onReferenceChange = { topUpReference = it },
                onSubmit = {
                    currentUserId?.let { userId ->
                        topUpAmount.toDoubleOrNull()?.let { amountValue ->
                            pendingTopUp = true
                            module.walletViewModel.topUp(userId, amountValue, topUpReference)
                        }
                    }
                },
                onBack = { destination = AppDestination.Dashboard }
            )

            AppDestination.Transfer -> TransferScreen(
                toUserId = transferUserId,
                amount = transferAmount,
                reference = transferReference,
                isLoading = transactionState.isLoading,
                onToUserIdChange = { transferUserId = it },
                onAmountChange = { transferAmount = it },
                onReferenceChange = { transferReference = it },
                onSubmit = {
                    currentUserId?.let { fromUserId ->
                        transferAmount.toDoubleOrNull()?.let { amountValue ->
                            pendingTransfer = true
                            module.transactionViewModel.transfer(
                                fromUserId = fromUserId,
                                toUserId = transferUserId.trim(),
                                amount = amountValue,
                                reference = transferReference
                            )
                        }
                    }
                },
                onBack = { destination = AppDestination.Dashboard }
            )

            AppDestination.History -> TransactionHistoryScreen(
                transactions = transactionState.transactions,
                onBack = { destination = AppDestination.Dashboard }
            )

            AppDestination.Profile -> ProfileScreen(
                email = currentEmail ?: module.sharedViewModel.sessionState.value.currentEmail.orEmpty(),
                userId = currentUserId ?: module.sharedViewModel.sessionState.value.currentUserId.orEmpty(),
                onLogout = {
                    module.authViewModel.logout()
                    module.sharedViewModel.clearSession()
                    pendingTopUp = false
                    pendingTransfer = false
                    destination = AppDestination.Login
                },
                onBack = { destination = AppDestination.Dashboard }
            )
        }

        if (!errorMessage.isNullOrBlank()) {
            ErrorDialog(
                message = errorMessage,
                onDismiss = {
                    module.authViewModel.clearError()
                    module.walletViewModel.clearError()
                    module.transactionViewModel.clearError()
                }
            )
        }
    }
}