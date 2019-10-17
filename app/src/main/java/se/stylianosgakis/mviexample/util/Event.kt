package se.stylianosgakis.mviexample.util

/**
 * Used as a wrapper class for data that is exposed via a LiveData that represents an event.
 * Consumable means that we allow an event to be consumed just once, and not show again.
 * This triggering the event on rotation of the screen when re-subscribing to the listener
 */
class Event<T>(private val content: T) {

    var hasBeenHandled = false
        private set //Allow external read but not write

    /**
     * Returns the content and prevents its use again
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it has already been handled
     */
    fun peekContent(): T = content

    override fun toString(): String {
        return "Event(content=$content,hasBeenHandled=$hasBeenHandled"
    }

    companion object {
        //We don't want an event if there is no data
        fun <T> dataEvent(data: T?): Event<T>? {
            data?.let {
                return Event(it)
            }
            return null
        }

        //We don't want an event if there is no message
        fun messageEvent(message: String?): Event<String>? {
            message?.let {
                return Event(message)
            }
            return null
        }
    }

}