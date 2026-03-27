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

@Composable
fun LoginScreen(
    email: String,
    password: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onRegisterNavigate: () -> Unit
) {
    AuthShell(
        title = "Welcome Back",
        subtitle = "Sign in to manage your balance, transfers, and transaction history."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CustomTextField(email, onEmailChange, "Email")
            CustomTextField(password, onPasswordChange, "Password", isPassword = true)

            CustomButton(
                text = if (isLoading) "Signing In..." else "Sign In",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Text(
                text = "Don't have an account yet?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 16.dp)
            )

            CustomButton(
                text = "Create Account",
                onClick = onRegisterNavigate,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }
    }
}
