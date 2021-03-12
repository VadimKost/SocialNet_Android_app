package com.v.vsocial.utils

sealed class ActionVM(var msg:String? =null){
    object logout : ActionVM()
    object showLoadingBar : ActionVM()
    object hideLoadingBar : ActionVM()
    class showMessage(msg: String): ActionVM(msg)
    object waitingAction:ActionVM()
}