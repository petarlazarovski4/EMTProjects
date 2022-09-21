package com.marco.scmexc.models.auth

//import javax.validation.constraints.NotBlank

data class ChangePasswordPayload (
//    @NotBlank
    val oldPassword: String,

//    @NotBlank
    val password: String,

//    @NotBlank
    val confirmPassword: String
)
