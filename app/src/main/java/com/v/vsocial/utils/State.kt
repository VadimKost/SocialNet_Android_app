package com.v.vsocial.utils

sealed class State{
    object loading : State()
    data class success(var data:Any, val label:String): State()
    object networkError: State()
}
