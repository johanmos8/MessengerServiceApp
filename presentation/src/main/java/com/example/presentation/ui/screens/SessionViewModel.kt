package com.example.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.UserContact
import com.example.domain.useCases.session.LogInUserUseCase
import com.example.domain.useCases.session.LogOutUserUseCase
import com.example.domain.useCases.session.SignUpUseCase
import com.example.presentation.ui.state.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _sessionData = MutableStateFlow(SessionState())
    val sessionData: StateFlow<SessionState> = _sessionData

    private val _owner = MutableStateFlow<UserContact?>(null)
    val owner: MutableStateFlow<UserContact?> = _owner

    fun LogIn(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val successLogin = logInUserUseCase.invoke(username, password)
            if (successLogin) {

                _owner.value?.let {
                    _sessionData.value = SessionState(true, it, password)
                }
            }
        }
    }

    fun LogOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUserUseCase.invoke()

            _sessionData.value = SessionState()
        }
    }

    fun SignUpUser() {

        val owner = UserContact(
            name = "Leiner Mosquera",
            phoneNumber = "3223153245",
            profilePicture = ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.invoke(owner)

        }

    }
}