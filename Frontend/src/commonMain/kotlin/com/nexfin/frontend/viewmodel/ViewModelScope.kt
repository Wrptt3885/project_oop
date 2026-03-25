package com.nexfin.frontend.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class ViewModelScope {
    protected val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
