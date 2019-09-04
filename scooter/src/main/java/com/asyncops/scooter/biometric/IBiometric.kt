package com.asyncops.scooter.biometric

import androidx.biometric.BiometricPrompt


/**
 * Biometric interface
 * This interface have all necessary callbacks for biometric works.
 */
interface IBiometric {
    fun onError(errorCode: Int, errMessage: String, nothing: Nothing?)
    fun onFailure()
    fun onResponse(result: BiometricPrompt.AuthenticationResult)
}