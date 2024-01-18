package util.helper

sealed interface Validator {
    val message: String
    fun isValid(input: String): Boolean

    class NotEmpty(override val message: String) : Validator {
        override fun isValid(input: String): Boolean {
            return input.isNotEmpty()
        }
    }

    class MinimumLength(val min: Int, override val message: String) :
        Validator {
        override fun isValid(input: String): Boolean {
            return input.length >= min
        }

    }

    class MaximumLength(val max: Int, override val message: String) :
        Validator {
        override fun isValid(input: String): Boolean {
            return input.length <= max
        }
    }

    class IsIpAddress(override val message: String) : Validator {
        override fun isValid(input: String): Boolean {
            if (message.isEmpty()) {
                return false
            }
            else if (message.length > 6 &&
                message.substring(0, 7) == "http://"
                && message.length < 8
            ) {
                return false
            }
            else if (message.length > 7 &&
                message.substring(0, 8) == "https://"
                && message.length < 9
            ) {
                return false
            }
            return true
        }
    }
}

fun List<Validator>.extractErrorMessage(input: String): String? {
    find {
        !it.isValid(input)
    }?.let {
        return it.message
    }
    return null
}