package app.felgueiras.musicapp.contracts

interface ModelContract {

    /**
     * Operations offered from Model to Presenter
     */
    interface Model {
        fun makeAPICall(presenter: Object?, parameter: String, callType: String)
    }


}
