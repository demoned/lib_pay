package com.demons.pay.base.fragmentation.fragmentation.exception;

import android.util.Log;

/**
 * Perform the transaction action after onSaveInstanceState.
 * This is dangerous because the action can
 * be lost if the activity needs to later be restored from its state.
 * If you don't want to lost the action:
 * <p>
 * AfterSaveStateTransactionWarning
 */
public class AfterSaveStateTransactionWarning extends RuntimeException {

    public AfterSaveStateTransactionWarning(String action) {
        super("Warning: Perform this " + action + " action after onSaveInstanceState!");
        Log.w("Fragmentation", getMessage());
    }

}
