package com.nexfin.frontend

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nexfin.frontend.di.AppModule
import com.nexfin.frontend.ui.navigation.AppDestination
import com.nexfin.frontend.ui.navigation.NavGraph
import com.nexfin.frontend.ui.screens.auth.LoginScreen
import com.nexfin.frontend.ui.screens.auth.RegisterScreen
import com.nexfin.frontend.ui.screens.components.ErrorDialog
import com.nexfin.frontend.ui.screens.home.DashboardScreen
import com.nexfin.frontend.ui.screens.profile.ProfileScreen
import com.nexfin.frontend.ui.screens.transaction.SlipScreen
import com.nexfin.frontend.ui.screens.transaction.TopUpScreen
import com.nexfin.frontend.ui.screens.transaction.TransactionHistoryScreen
import com.nexfin.frontend.ui.screens.transaction.TransferScreen
import com.nexfin.frontend.ui.theme.NexFinTheme
import com.nexfin.frontend.utils.AppLogger
import com.nexfin.frontend.utils.ValidationUtils

private data class SlipState(
    val amount: String = "",
    val toUserId: String = "",
    val reference: String = ""
)

@Composable
fun NexFinApp(appModule: AppModule? = null) {
    val module = appModule ?: remember { AppModule() }
    val authState by module.authViewModel.uiState.collectAsState()
    val walletState by module.walletViewModel.uiState.collectAsState()
    val transactionState by module.transactionViewModel.uiState.collectAsState()

    var destination by remember { mutableStateOf<AppDestination>(NavGraph.startDestination) }
    var slipState by remember { mutableStateOf(SlipState()) }

    val currentUserId = authState.userId
    val currentEmail = authState.email

    LaunchedEffect(authState.isLoggedIn, currentUserId, currentEmail) {
        if (authState.isLoggedIn && currentUserId != null && currentEmail != null) {
            AppLogger.info("NexFinApp", "Login state committed. Loading dashboard data.")
            module.walletViewModel.loadWallet(currentUserId)
            module.transactionViewModel.loadTransactions(currentUserId)
            destination = AppDestination.Dashboard
        }
    }

    val errorMessage = authState.errorMessage ?: walletState.errorMessage ?: transactionState.errorMessage

    NexFinTheme {
        when (destination) {
            AppDestination.Login -> LoginRoute(module, authState.isLoading) { destination = it }
            AppDestination.Register -> RegisterRoute(module, authState.isLoading) { destination = it }
            AppDestination.TopUp -> TopUpRoute(
                module = module,
                userId = currentUserId,
                isLoading = walletState.isLoading,
                errorMessage = walletState.errorMessage,
                onNavigate = { destination = it },
                onTopUpSuccess = { amount, reference ->
                    slipState = SlipState(
                        amount = amount,
                        toUserId = currentUserId ?: "-",
                        reference = reference
                    )
                    destination = AppDestination.Slip
                }
            )

            AppDestination.Transfer -> TransferRoute(
                module = module,
                userId = currentUserId,
                isLoading = transactionState.isLoading,
                errorMessage = transactionState.errorMessage,
                onNavigate = { destination = it },
                onTransferSuccess = { amount, toUserId, reference ->
                    slipState = SlipState(
                        amount = amount,
                        toUserId = toUserId,
                        reference = reference
                    )
                    destination = AppDestination.Slip
                }
            )

            AppDestination.Dashboard -> DashboardScreen(
                wallet = walletState.wallet,
                transactions = transactionState.transactions,
                userEmail = currentEmail.orEmpty(),
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

            AppDestination.History -> TransactionHistoryScreen(
                transactions = transactionState.transactions,
                onBack = { destination = AppDestination.Dashboard }
            )

            AppDestination.Slip -> SlipScreen(
                amount = slipState.amount,
                toUserId = slipState.toUserId,
                reference = slipState.reference,
                onBack = { destination = AppDestination.Dashboard }
            )

            AppDestination.Profile -> ProfileScreen(
                email = currentEmail.orEmpty(),
                userId = currentUserId.orEmpty(),
                onLogout = {
                    module.authViewModel.logout()
                    slipState = SlipState()
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

@Composable
private fun LoginRoute(module: AppModule, isLoading: Boolean, onNavigate: (AppDestination) -> Unit) {
    var email by remember { mutableStateOf("alice@example.com") }
    var password by remember { mutableStateOf("StrongPass1") }

    LoginScreen(
        email = email,
        password = password,
        isLoading = isLoading,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onSubmit = {
            if (ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password)) {
                module.authViewModel.login(email, password)
            } else {
                AppLogger.error("NexFinApp", "Login validation blocked request.")
            }
        },
        onRegisterNavigate = { onNavigate(AppDestination.Register) }
    )
}

@Composable
private fun RegisterRoute(module: AppModule, isLoading: Boolean, onNavigate: (AppDestination) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    RegisterScreen(
        fullName = name,
        email = email,
        password = password,
        isLoading = isLoading,
        onFullNameChange = { name = it },
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onSubmit = {
            if (name.isNotBlank() && ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password)) {
                module.authViewModel.register(name, email, password)
            } else {
                AppLogger.error("NexFinApp", "Register validation blocked request.")
            }
        },
        onLoginNavigate = { onNavigate(AppDestination.Login) }
    )
}

@Composable
private fun TopUpRoute(
    module: AppModule,
    userId: String?,
    isLoading: Boolean,
    errorMessage: String?,
    onNavigate: (AppDestination) -> Unit,
    onTopUpSuccess: (amount: String, reference: String) -> Unit
) {
    var amount by remember { mutableStateOf("500") }
    var reference by remember { mutableStateOf("PROMPTPAY_TOPUP_001") }
    var pending by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading, errorMessage, pending) {
        if (pending && !isLoading) {
            if (errorMessage == null) {
                AppLogger.info("NexFinApp", "Top-up completed. Navigating to slip screen.")
                userId?.let { module.transactionViewModel.loadTransactions(it) }
                onTopUpSuccess(amount, reference)
            }
            pending = false
        }
    }

    TopUpScreen(
        amount = amount,
        reference = reference,
        isLoading = isLoading,
        onAmountChange = { amount = it },
        onReferenceChange = { reference = it },
        onSubmit = {
            userId?.let { id ->
                amount.toDoubleOrNull()?.let { value ->
                    pending = true
                    module.walletViewModel.topUp(id, value, reference)
                }
            }
        },
        onBack = { onNavigate(AppDestination.Dashboard) }
    )
}

@Composable
private fun TransferRoute(
    module: AppModule,
    userId: String?,
    isLoading: Boolean,
    errorMessage: String?,
    onNavigate: (AppDestination) -> Unit,
    onTransferSuccess: (amount: String, toUserId: String, reference: String) -> Unit
) {
    var toUserId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("125") }
    var reference by remember { mutableStateOf("PAYMENT_001") }
    var pending by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading, errorMessage, pending) {
        if (pending && !isLoading) {
            if (errorMessage == null) {
                AppLogger.info("NexFinApp", "Transfer completed. Navigating to slip screen.")
                userId?.let {
                    module.walletViewModel.loadWallet(it)
                    module.transactionViewModel.loadTransactions(it)
                }
                onTransferSuccess(amount, toUserId, reference)
            }
            pending = false
        }
    }

    TransferScreen(
        toUserId = toUserId,
        amount = amount,
        reference = reference,
        isLoading = isLoading,
        onToUserIdChange = { toUserId = it },
        onAmountChange = { amount = it },
        onReferenceChange = { reference = it },
        onSubmit = {
            userId?.let { fromId ->
                amount.toDoubleOrNull()?.let { value ->
                    pending = true
                    module.transactionViewModel.transfer(fromId, toUserId.trim(), value, reference)
                }
            }
        },
        onBack = { onNavigate(AppDestination.Dashboard) }
    )
}
