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
fun RegisterScreen(
    fullName: String,
    email: String,
    password: String,
    isLoading: Boolean,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onLoginNavigate: () -> Unit
) {
    AuthShell(
        title = "Create Account",
        subtitle = "Join NexFin and start managing your digital finances with less friction."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CustomTextField(fullName, onFullNameChange, "Full Name")
            CustomTextField(email, onEmailChange, "Email")
            CustomTextField(password, onPasswordChange, "Password", isPassword = true)

            CustomButton(
                text = if (isLoading) "Creating Account..." else "Register",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 16.dp)
            )

            CustomButton(
                text = "Back To Sign In",
                onClick = onLoginNavigate,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }
    }
}
