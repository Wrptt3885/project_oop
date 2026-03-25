package com.nexfin.frontend.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.ui.screens.components.CustomTextField
import com.nexfin.frontend.ui.screens.components.LoadingIndicator

@Composable
fun RegisterScreen(
    fullName: String,
    email: String,
    password: String,
    isLoading: Boolean,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackToLoginClick: () -> Unit
) {
    AuthShell(
        title = "Create your wallet",
        subtitle = "Open an account in a minute and start tracking balance, top-ups, and transfers."
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CustomTextField(fullName, onFullNameChange, "Full name")
            CustomTextField(email, onEmailChange, "Email")
            CustomTextField(password, onPasswordChange, "Password", isPassword = true)
            CustomButton(
                text = if (isLoading) "Creating..." else "Create Account",
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            if (isLoading) {
                LoadingIndicator("Creating your account")
            }
            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium
            )
            CustomButton(
                text = "Back to Login",
                onClick = onBackToLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }
    }
}
