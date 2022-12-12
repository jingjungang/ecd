package com.ukang.clinic.application;

import java.util.Observable;

public class PagerObservered extends Observable
{
  public void notifition()
  {
    setChanged();
    notifyObservers();
  }
}