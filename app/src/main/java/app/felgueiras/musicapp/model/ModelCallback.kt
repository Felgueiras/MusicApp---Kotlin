package app.felgueiras.musicapp.model

/**
 * Callback called after Model executes API call
 * Rationale: allow callback mocking by implementing interface
 */
interface ModelCallback<in T> {

    fun onSuccess(t: T?)

    fun onError()
}