package com.v.vsocial.utils

sealed class ActionVM{
    object logout : ActionVM()
    object showLoadingBar : ActionVM()
    object hideLoadingBar : ActionVM()
    object waitingAction:ActionVM()
}