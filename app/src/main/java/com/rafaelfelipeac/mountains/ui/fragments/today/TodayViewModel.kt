package com.rafaelfelipeac.mountains.ui.fragments.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafaelfelipeac.mountains.models.Repetition
import com.rafaelfelipeac.mountains.models.User
import com.rafaelfelipeac.mountains.ui.base.BaseViewModel

class TodayViewModel: BaseViewModel() {
    private var repetitions: LiveData<List<Repetition>>? = null

    var user: MutableLiveData<User>? = MutableLiveData()

    init {
        verifyUser()

        repetitions = repetitionRepository.getRepetitions()
    }

    private fun verifyUser() {
        if (userRepository.getUserByUUI(auth.currentUser?.uid!!) == null) {
            val userToSave = User()

            userToSave.uui = auth.currentUser?.uid!!
            userToSave.email = auth.currentUser?.email!!

            userRepository.save(userToSave)
        }

        user?.value = userRepository.getUserByUUI(auth.currentUser?.uid!!)
    }

    // Repetition
    fun getRepetitions(): LiveData<List<Repetition>>? {
        return repetitions
    }

    fun saveRepetition(repetition: Repetition) {
        repetitionRepository.save(repetition)

        this.repetitions = repetitionRepository.getRepetitions()
    }
}