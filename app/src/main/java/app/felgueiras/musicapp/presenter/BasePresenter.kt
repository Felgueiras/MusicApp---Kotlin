package app.felgueiras.musicapp.presenter

/**
 * Offer attach and detach view operations to Activity.
 */
abstract class BasePresenter<V> {
    protected var view: V? = null

    /**
     * Associate View to Presenter.
     */
    fun attachView(view: V) {
        this.view = view
    }

    /**
     * Deassociate View from Presenter.
     */
    fun detachView() {
        this.view = null
    }
}
