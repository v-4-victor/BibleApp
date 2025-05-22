package com.v4vic.bibleapp.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@Composable
fun PhoneAuthScreen(
    onAuthSuccess: () -> Unit
) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf<String?>(null) }
    var isCodeSent by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Вход по номеру телефона", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Номер телефона") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (isCodeSent) {
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Код из СМС") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (!isCodeSent) {
                    sendVerificationCode(
                        context = context,
                        phoneNumber = phoneNumber,
                        onCodeSent = {
                            verificationId = it
                            isCodeSent = true
                        },
                        onFailure = { error = it.message }
                    )
                } else {
                    verificationId?.let {
                        verifyCode(
                            verificationId = it,
                            code = code,
                            onSuccess = onAuthSuccess,
                            onFailure = { error = it.message }
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isCodeSent) "Подтвердить код" else "Отправить код")
        }
    }
}

fun sendVerificationCode(
    context: Context,
    phoneNumber: String,
    onCodeSent: (String) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val activity = context as? Activity ?: return
    val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
        .setPhoneNumber(phoneNumber)
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                FirebaseAuth.getInstance().signInWithCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                onFailure(e)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                onCodeSent(verificationId)
            }
        })
        .build()

    PhoneAuthProvider.verifyPhoneNumber(options)
}

fun verifyCode(
    verificationId: String,
    code: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val credential = PhoneAuthProvider.getCredential(verificationId, code)
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { onFailure(it) }
}
