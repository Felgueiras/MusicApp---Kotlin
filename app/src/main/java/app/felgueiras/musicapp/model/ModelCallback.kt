package app.felgueiras.musicapp.model

interface ModelCallback<in T> {
    fun onSuccess(t: T?)
    fun onError()
}