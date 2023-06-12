package com.example.presentation.ui.screens

import android.util.Log
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _isLogged = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> = _isLogged

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> get() = _errorMessage
    fun logIn(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val successLogin = logInUserUseCase.invoke(username, password)?.collect {
                _owner.value = it
                if (_owner.value != null) {

                    withContext(Dispatchers.Main) {
                        _isLogged.value = true
                        _sessionData.value = SessionState(true, it, password)
                    }
                } else {
                    _isLogged.value = false
                }
            }

        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUserUseCase.invoke()

            _sessionData.value = SessionState()
        }
    }

    fun signUpUser() {

        val owner = UserContact(
            name = "Leiner Mosquera",
            phoneNumber = "3223153245",
            profilePicture = ""
        )
        _owner.value = owner
        Log.d("DataStore", "user$owner")
        viewModelScope.launch(Dispatchers.IO) {
            val response = signUpUseCase.invoke(owner)
            if (!response) {
                _errorMessage.value = "The request failed. Please try again."
            } else {
                _errorMessage.value = ""
            }

        }

    }
}
sealed class ViewState {
    object Loading: ViewState() // hasLoggedIn = unknown
    object LoggedIn: ViewState() // hasLoggedIn = true
    object NotLoggedIn: ViewState() // hasLoggedIn = false
}