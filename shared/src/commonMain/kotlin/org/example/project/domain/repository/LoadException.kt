package org.example.project.domain.repository

enum class LoadFailure { NETWORK, SERVER, RESPONSE }

class LoadException(val failure: LoadFailure, cause: Throwable? = null) : Exception(cause)
