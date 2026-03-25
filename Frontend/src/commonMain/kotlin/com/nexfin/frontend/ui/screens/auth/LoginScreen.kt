package com.nexfin.frontend.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.ui.screens.components.CustomTextField
import com.nexfin.frontend.ui.screens.components.LoadingIndicator

@Composable
fun LoginScreen(
    email: String,
    password: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    AuthShell(
        title = "Welcome back",
        subtitle = "Securely sign in to manage your wallet balance, transfers, and recent activity."
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CustomTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "Email"
            )
            CustomTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = "Password",
                isPassword = true
            )
            CustomButton(
                text = if (isLoading) "Signing in..." else "Sign In",
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            if (isLoading) {
                LoadingIndicator("Checking your account")
            }
            Text(
                text = "New here?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            CustomButton(
                text = "Create Account",
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }
    }
}
