package com.example.feedbackapp.Common;

import java.util.Observable;

public class BroadcastObserver extends Observable
{
    public void triggerObservers()
    {
// Sets the changed flag for this Observable
       // setChanged();
// notify registered observer objects by calling the update method
        notifyObservers();

    }
    /*@Override
    public boolean hasChanged() {
        return true; //super.hasChanged();
    }*/

}
