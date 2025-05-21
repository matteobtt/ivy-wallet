package com.ivy.planned.list

sealed interface PlannedPaymentsScreenEvent {
    data class OnRecurringPaymentsExpanded(val isExpanded: Boolean) : PlannedPaymentsScreenEvent
}
