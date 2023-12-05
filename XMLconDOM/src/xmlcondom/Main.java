/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package xmlcondom;

import java.io.File;

/**
 *
 * @author Robyn
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AccesoDOM acceso = new AccesoDOM();
        File file = new File("./Libros.xml");
        acceso.abrirXMLaDOM(file);
        //llamadas a los métodos de acceso
        acceso.recorreDOMyMuestra();
        //añadimos nuevo libro y mostramos
        acceso.insertarLibroEnDOM("Astérix & Obélix", "Uderzo", "1959");
        acceso.recorreDOMyMuestra();
        //borramos Don Quijote
        acceso.borrarLibro("Don Quijote");
        acceso.recorreDOMyMuestra();
        //guardamos el árbol DOM en un nuevo fichero XML
        acceso.guardarDOMaArchivo("./LibrosDOM.xml");
    }
    
}
