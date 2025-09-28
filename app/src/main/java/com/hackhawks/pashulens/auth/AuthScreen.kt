package com.hackhawks.pashulens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hackhawks.pashulens.ui.theme.DarkBlue
import com.hackhawks.pashulens.ui.theme.PashuLensTheme
import kotlinx.coroutines.launch

private enum class AuthScreenState { CREATE_ACCOUNT, LOGIN, VERIFY }

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    var currentState by remember { mutableStateOf(AuthScreenState.CREATE_ACCOUNT) }
    var phoneNumber by remember { mutableStateOf("") }

    when (currentState) {
        AuthScreenState.CREATE_ACCOUNT -> {
            CreateAccountScreen(
                onSignInClicked = { currentState = AuthScreenState.LOGIN },
                onSignUpClicked = { name, phone, email ->
                    viewModel.signUpUser(name, phone, email)
                    phoneNumber = phone
                    currentState = AuthScreenState.VERIFY
                }
            )
        }
        AuthScreenState.LOGIN -> {
            LoginScreen(
                onCreateAccountClicked = { currentState = AuthScreenState.CREATE_ACCOUNT },
                onSendOtpClicked = { phone ->
                    // --- THIS IS THE FIX ---
                    // This line is now active and will send the OTP
                    viewModel.sendOtp(phone)
                    phoneNumber = phone
                    currentState = AuthScreenState.VERIFY
                }
            )
        }
        AuthScreenState.VERIFY -> {
            VerifyScreen(
                phoneNumber = phoneNumber,
                onVerifyClicked = { otp ->
                    viewModel.verifyOtp(phoneNumber, otp, onVerificationSuccess = onLoginSuccess)
                }
            )
        }
    }
}

// ... The rest of the file (CreateAccountScreen, LoginScreen, VerifyScreen, etc.) is unchanged ...
// For completeness, it's all included below.

@Composable
private fun CreateAccountScreen(
    onSignInClicked: () -> Unit,
    onSignUpClicked: (String, String, String?) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Join the smart livestock management", color = Color.Gray)
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, leadingIcon = { Text("+91") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Id (Optional)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onSignUpClicked(name, phone, email.ifEmpty { null }) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Sign Up", modifier = Modifier.padding(8.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account?", color = Color.Gray)
            TextButton(onClick = onSignInClicked) {
                Text("Sign in", color = DarkBlue)
            }
        }
    }
}

@Composable
private fun LoginScreen(onCreateAccountClicked: () -> Unit, onSendOtpClicked: (String) -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Login With Phone Number", color = Color.Gray, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Enter Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onSendOtpClicked(phoneNumber) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Send Otp", modifier = Modifier.padding(8.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account?", color = Color.Gray)
            TextButton(onClick = onCreateAccountClicked) {
                Text("Create account", color = DarkBlue)
            }
        }
    }
}

@Composable
private fun VerifyScreen(phoneNumber: String, onVerifyClicked: (String) -> Unit) {
    var otp by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(text = "PashuLens", color = DarkBlue, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Verify your phone number", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "We've sent an SMS with an activation code to your phone $phoneNumber",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        OtpInputField(length = 6, onOtpChanged = { otp = it })
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { onVerifyClicked(otp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Verify Now", modifier = Modifier.padding(8.dp))
        }
        TextButton(onClick = { /* TODO: Resend code */ }) {
            Text("I didn't receive a code. Resend", color = Color.Gray)
        }
    }
}

@Composable
private fun OtpInputField(length: Int, onOtpChanged: (String) -> Unit) {
    var otp by remember { mutableStateOf("") }
    val focusRequesters = remember { List(length) { FocusRequester() } }

    LaunchedEffect(otp) {
        onOtpChanged(otp)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        for (i in 0 until length) {
            OutlinedTextField(
                value = otp.getOrNull(i)?.toString() ?: "",
                onValueChange = {
                    if (it.length <= 1) {
                        val newOtp = otp.take(i) + it + otp.drop(i + 1)
                        otp = newOtp.take(length)
                        if (it.isNotEmpty() && i < length - 1) {
                            focusRequesters[i + 1].requestFocus()
                        }
                    }
                },
                modifier = Modifier.width(48.dp).focusRequester(focusRequesters[i]),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    PashuLensTheme {
        AuthScreen(onLoginSuccess = {})
    }
}
