package com.jeanmeza.dispensapp.initialiser

import androidx.work.Constraints
import androidx.work.NetworkType


val ExpiryCheckConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()