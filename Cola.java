import java.io.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class Cola<T> implements Iterable<T> {

  private Node<T> primero;
  private Node<T> ultimo;
  private int numeroElementos;

  public boolean isVacio(){
    return primero == null;
  }

  public int tamano(){
    return numeroElementos;
  }

  public T mirar(){
    if(isVacio()){
      throw new NoSuchElementException("La cola esta vacia");
    }
    return primero.getItem();
  }

  public void encolar(T objeto){
    Node<T> penultimo = ultimo;
    ultimo = new Node<T>();
    ultimo.setItem(objeto);
    ultimo.setSiguiente(null);
    if(isVacio()){
      primero = ultimo;
    }else {
      penultimo.setSiguiente(ultimo);
    }
    numeroElementos++;
  }

  public T sacar(){
    if(isVacio()){
      throw new NoSuchElementException("no hay elementos en la cola");
    }else {
      T elemento = primero.getItem();
      primero = primero.getSiguiente();
      numeroElementos--;
      if(isVacio()){
        ultimo = null;
      }
      return elemento;
    }
  }

  public String toString(){
    StringBuilder stringBuilder = new StringBuilder();
    for(T elemento : this){
      stringBuilder.append(elemento);
      stringBuilder.append(' ');
    }
    return stringBuilder.toString();
  }

  @Override
  public Iterator<T> iterator() {
    return new IteratorEnlazado(primero);
  }

}
