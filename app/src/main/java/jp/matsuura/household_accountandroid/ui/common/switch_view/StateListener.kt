package jp.matsuura.household_accountandroid.ui.common.switch_view

/**
 * Listener of state changes.
 */
fun interface StateListener {
    /**
     * Invoked when a state is selected.
     *
     * @param stateIndex index of the state.
     * @param state      state instance.
     */
    fun onStateSelected(stateIndex: Int, state: State)
}