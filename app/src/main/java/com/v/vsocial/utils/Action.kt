package com.v.vsocial.utils

sealed class Action{
    object logout : Action()
    object waitingAction : Action()
}
