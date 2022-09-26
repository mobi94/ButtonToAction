package com.serhii.stasiuk.buttontoaction.domain.usecase

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty
import java.util.*

class GetButtonActionUseCase {
    operator fun invoke(
        properties: List<ButtonProperty>,
        lastTimeChosen: Long?
    ): ButtonActionType? {
        return properties.filter {
            it.isEnabled && isCurrentDay(it.validDays) &&
                    canBeChosenAgain(it.coolDownMillis, lastTimeChosen)
        }.run {
            val maxValue = maxByOrNull { it.priority } ?: 0
            val filtered = filter { it.priority == maxValue }
            filtered.takeIf { it.isNotEmpty() }?.random()?.type
        }
    }

    private fun isCurrentDay(daysOfWeek: List<Int>): Boolean {
        val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return daysOfWeek.mapNotNull {
            when (it) {
                0 -> Calendar.MONDAY
                1 -> Calendar.TUESDAY
                2 -> Calendar.WEDNESDAY
                3 -> Calendar.THURSDAY
                4 -> Calendar.FRIDAY
                5 -> Calendar.SATURDAY
                6 -> Calendar.SUNDAY
                else -> null
            }
        }.contains(currentDayOfWeek)
    }

    private fun canBeChosenAgain(
        coolDownMillis: Long, lastTimeChosen: Long?
    ): Boolean {
        val currentTime = System.currentTimeMillis()
        return lastTimeChosen?.let {
            currentTime - it > coolDownMillis
        } ?: true
    }
}