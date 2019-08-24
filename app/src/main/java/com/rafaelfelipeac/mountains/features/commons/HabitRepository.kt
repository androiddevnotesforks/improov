package com.rafaelfelipeac.mountains.features.commons

import androidx.lifecycle.LiveData
import com.rafaelfelipeac.mountains.features.commons.Habit
import com.rafaelfelipeac.mountains.features.commons.data.HabitDAO
import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDAO: HabitDAO) {

    fun getHabits(): LiveData<List<Habit>> {
        return habitDAO.getAll()
    }

    fun getHabit(habitId: Long): LiveData<Habit> {
        return habitDAO.get(habitId)
    }

    fun save(habit: Habit): Long {
        return habitDAO.save(habit)
    }

    fun delete(habit: Habit) {
        return habitDAO.delete(habit)
    }
}
