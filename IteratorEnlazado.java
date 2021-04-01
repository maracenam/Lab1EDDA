import java.util.NoSuchElementException;
import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IteratorEnlazado implements Iterator<T> {

  private Node<T> actual;

  public <T> IteratorEnlazado(Node<T> primero){
    actual=primero;
  }

  public boolean hasNext(){
    return actual!=null;
  }

  public T next(){
    if(!hasNext()){
      throw new NoSuchElementException();
    }else{
      T elemento = actual.getItem();
      actual = actual.getSiguiente();
      return elemento;
    }
  }
}
