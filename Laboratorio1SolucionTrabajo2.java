




import java.io.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solucion clase 1 del Laboratorio de Estructuras de Datos y Algoritmos
 * <p>
 * Se pide generar un código java que copie la informacion de uno de los archivos .csv a un ArrayList de ArrayLists y luego escribir ese contenido a un nuevo archivo .csv
 */
public class Laboratorio1SolucionTrabajo2 {

    private static final char CARACTER_SEPARADOR_CSV_SALIDA = '|';
    public static final String DIRECTORIO_DATASET_SALIDA = "dataset/mexico/csv-out";

    //Metodo principal de ejecucion
    public static void main(String args[]) throws IOException {
        System.out.println("¡Comencemos!");

        //Guardo una marca de tiempo para luego calcular el tiempo total de ejecucion
        Instant inicio = Instant.now();

        //Creo un objeto del tipo File pasandole en su constructor la ruta donde se encuentran los archivos que quiero procesar. Recordar que en Windows el separador en las rutas es doble backslash \\
        File directorioDataSet = new File("dataset/mexico/csv");

        //Listo todos los archivos que hay dentro de la carpeta a procesar y genero un array de Files
        File[] archivos = directorioDataSet.listFiles();

        //Itero el array para trabajar con cada archivo que se encuentra en el directorio
        for (File archivo : archivos) {

            //Creo un ArrayList de ArrayLists vacio
            List<List<String>> listaDeListas = new ArrayList<>();

            //Imprimo el nombre del archivo que voy a leer
            System.out.println("Procesando el archivo " + archivo.getName());

            //Construyo una instancia del tipo BufferedReader para facilitar y optimizar la lectura del archivo
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));

            String linea;
            List<String> listaValoresLinea;

            //Leo cada linea del archivo, cuando reciba un valor null dejo de leer
            while ((linea = bufferedReader.readLine()) != null) {

                //Separo los distintos valores que hay en la linea basandome en el divisor pipe |
                String[] valoresLinea = linea.split("\\|");


                //Transformo el array de valores a una lista
                listaValoresLinea = Arrays.asList(valoresLinea);

                //Agrego la lista de valores al Array de Arrays (Lista de listas)
                listaDeListas.add(listaValoresLinea);

            }

            //Creo una instancia del tipo File para indicar el directorio donde escribire archivos de salida
            File directorioSalida = new File(DIRECTORIO_DATASET_SALIDA);

            //Creo el o las carpetas necesarias para el directorio de salida si es que este no existe
            Files.createDirectories(directorioSalida.toPath());


            //Uso el mismo nombre de salida del archivo pero le concateno el sufijo -out y mantengo la extension .csv
            String nombreArchivoSalida = archivo.getName().replace(".csv", "-out.csv");

            //Genero un objeto File para referenciar al archivo que voy a escribir
            File archivoSalida = new File(DIRECTORIO_DATASET_SALIDA + "/" + nombreArchivoSalida);

            //Uso una instancia de BufferedWriter para escribir de forma eficiente el archivo
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivoSalida));

            System.out.println("Escribiendo archivo " + nombreArchivoSalida);

            List<String> listaProductos = new ArrayList<>();

            //Itero sobre el ArrayList de ArrayList (listaDeListas) y asigno cada lista a una variable llamada listaValoresGuardados
            for (int i=0; i<listaDeListas.size(); i++) {
                List<String> listaValoresGuardados = listaDeListas.get(i);


                // Leo cada valor de la variable listaValoresGuardados y lo asigno a la variable lineaArray
                for (String lineaArray : listaValoresGuardados) {
                    bufferedWriter.write(lineaArray); // Escribo el valor de la iteracion en el archivo
                    bufferedWriter.write(CARACTER_SEPARADOR_CSV_SALIDA); // Escribo el separador elegido

                }
                bufferedWriter.newLine(); // despues de escribir todos los valores de la lista que equivalen a una linea, entonces agrego un salto de linea

                //Ignoro la primera lista ya que corresponde a los títulos de las columnas y no a productos (i>0) y si la lista tiene más de 3 valores significa que hay un nombre de producto
                if(i>0 && listaValoresGuardados.size()>3){
                    String nombreProducto = listaValoresGuardados.get(3); //leo la posicion numero 3 de la lista, que corresponde a la columna 4 del archivo, que a la vez corresponde al nombre del producto
                    listaProductos.add(nombreProducto); // agrego el nombre a una lista que contendrá todos los nombres de producto del archivo
                }
            }
            //Cierro el archivo para asegurar que se escriba lo que queda en el buffer a disco
            bufferedWriter.close();
            System.out.println("Finalizada la escritura del  " + nombreArchivoSalida);

            //El nombre de categoria es el mismo para todos los productos dentro de un mismo archivo por lo que lo determino antes de recorrer todos los productos
            String nombreCategoria= archivo.getName().replace("mx-","").replace(".csv","");

            //Preparo una lista para guardar las tripletas
            List<Tripleta> listaTripleta = new ArrayList<>();

            //Recorro la lista de productos
            for(int i=0; i<listaProductos.size(); i++){
                //Obtengo el nombre del producto
                String nombreProducto= listaProductos.get(i);

                //Valido si el producto ya fue ingresado a la lista de tripletas, si no existe ejecuto lógica dentro del if
                if(!existeTripleta(listaTripleta, nombreProducto)){
                    Tripleta tripleta = new Tripleta();
                    //cuento cuantas veces aparece el mismo nombre del producto en la lista de productos
                    int apariciones=contarAparicionesProducto(listaProductos,nombreProducto);

                    //Creo una estructura Tripleta con los valores de categoria, nombre y numero de veces que aparece el producto
                    tripleta.setCategoria(nombreCategoria);
                    tripleta.setNombreProducto(nombreProducto);
                    tripleta.setNumeroVeces(apariciones);

                    //agrego la tripleta a la lista
                    listaTripleta.add(tripleta);
                }

            }

            //Imprimo los valores de la lista de tripleta para validar los resultados
            for(Tripleta tripleta:listaTripleta){
                System.out.println(tripleta);
              //  System.out.println("shrek");
            }



        }

        //Guardo una marca de tiempo para calcular el tiempo de ejecucion
        Instant fin = Instant.now();
        System.out.println("Todos los arhivos fueron escritos de forma exitosa, tiempo total de procesamiento: ");
        System.out.println("Tiempo total: " + Duration.between(inicio, fin).toMillis() + " milisegundos");

    }

    private static boolean existeTripleta(List<Tripleta> listaTripleta, String nombreProducto) {
        //Recorro toda la lista de tripletas y busco si el nombre del producto ya existe
        for (Tripleta tripleta : listaTripleta) {
            if (tripleta.getNombreProducto().compareToIgnoreCase(nombreProducto) == 0) {
                return true;
            }
        }
        return false;
    }


    public static int contarAparicionesProducto(List<String> lista, String nombreProductoAbuscar){
        int numeroDeVeces=0;
        //Recorro la lista de productos en busca del nombre, y cada vez que este aparece, entonces aumento el contador en uno, sino se encuentra nunca, entonces el contador tendrá el valor 0
        for(int i=0; i<lista.size(); i++){
            String producto = lista.get(i);
            if(producto.compareToIgnoreCase(nombreProductoAbuscar)==0){
                numeroDeVeces++;
            }
        }
        return numeroDeVeces;
    }
}
