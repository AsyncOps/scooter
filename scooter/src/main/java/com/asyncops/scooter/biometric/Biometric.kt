package com.asyncops.scooter.biometric

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors


private class Biometric(
    var context: FragmentActivity,
    var response: IBiometric
) {

    lateinit var bioPromptInfo: BiometricPrompt.PromptInfo
    lateinit var bioPrompt: BiometricPrompt

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            bioPromptInfo = BiometricPrompt.PromptInfo.Builder()
                .setSubtitle("Authentication needed to proceed to application")
                .setTitle("Authentication Needed")
                .setDescription("For login, you must authenticate your credentials with you biometric.")
                .setNegativeButtonText("Cancelar")
                .build()
        }
    }

    fun Builder(context: FragmentActivity, response: IBiometric): Biometric {
        return Biometric(context, response)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun tryAuthentication() {
        bioPrompt = BiometricPrompt(
            context,
            Executors.newSingleThreadExecutor(),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    response.onResponse(result)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    response.onError(errorCode, errString.toString(), null)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    response.onFailure()
                }
            })
        bioPrompt.authenticate(bioPromptInfo)
    }
}