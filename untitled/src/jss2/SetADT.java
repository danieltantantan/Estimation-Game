
//********************************************************************
//  SetADT.java       Authors:  Lewis, Chase, Coleman
//
//  Defines the interface to a set data structure.
//********************************************************************

package jss2;

import java.util.Iterator;

public interface SetADT<T>
{
    //  Adds one element to this bag
    public void add (T element);

    //  Removes and returns a random element from this bag
    public T removeRandom ();

    //  Removes and returns the specified element from this bag
    public T remove (T element);

    //  Returns the union of this bag and the parameter
    public SetADT union (SetADT set);

    //  Returns true if this bag contains the parameter
    public boolean contains (T target);

    //  Returns true if this bag and the parameter contain exactly
    //  the same elements
    public boolean equals (SetADT set);

    //  Returns true if this set contains no elements
    public boolean isEmpty();

    //  Returns the number of elements in this set
    public int size();

    //  Returns an iterator for the elements in this bag
    public Iterator iterator();

    //  Returns a string representation of this bag
    public String toString();
}