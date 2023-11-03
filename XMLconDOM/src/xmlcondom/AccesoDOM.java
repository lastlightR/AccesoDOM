/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xmlcondom;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.io.*;

/**
 *
 * @author Robyn
 */
public class AccesoDOM {
    
    Document doc;
    
    public int abrirXMLaDOM (File file){
        try{
            System.out.println("Abriendo archivo XML file y generando DOM....");

            //creamos nuevo objeto DocumentBuilder al que apunta la variable factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //ignorar comentarios y espacios blancos
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            
            //DocumentBuilder tiene el método parse que es el que genera DOM en memoria
            DocumentBuilder builder=factory.newDocumentBuilder();
            doc=builder.parse(file);
            
            // ahora doc apunta al arbol DOM y podemos recorrerlo
            System.out.println("DOM creado con éxito.\n");
            return 0;//si el método funciona
        }catch(Exception e){
            System.out.println(e);
            return -1;//if the method aborta en algún punto
        }
    }
    
    public void recorreDOMyMuestra(){
        String[] data = new String[3]; //para la info de cada libro
        Node node = null;
        Node root = doc.getFirstChild();
        NodeList libros = root.getChildNodes(); //recorriendo a los hijos de root (libros)
        
        for(int i=0;i<libros.getLength();i++){
            node = libros.item(i); //node adquiere cada valor de libro
            if (node.getNodeType() == Node.ELEMENT_NODE){ //mirando nodos de tipo Element
                Node tempnode = null;
                int count = 1;
                //sacamos el valor del atributo publicado
                data[0] = node.getAttributes().item(0).getNodeValue();
                //sacamos valores de los hijos de nodo (título y autor)
                NodeList titulos_autores = node.getChildNodes();
                for (int j=0;j<titulos_autores.getLength();j++){
                    tempnode = titulos_autores.item(j);
                    if (tempnode.getNodeType() == Node.ELEMENT_NODE){
                        //para obtener el texto de título y autor, se puede hacer con
                        //getNodeValue() o con getTextContent() si es ELEMENT
                        data[count] = tempnode.getTextContent();
                        //data[cont] = tempnode.getChildNodes().item(0).getNodeValue();
                        count++;
                    }
                }
                //data[0] es el atributo publicado, data[2] es el hijo autor, data[1] es el hijo título 
                System.out.println(data[0] + "--" + data[2] + "--" + data[1]);
            }
        }
        System.out.println("");
    }
    
    public int insertarLibroEnDOM(String titulo, String autor, String publicado){
        try{
            System.out.println("Añadiendo libro al DOM con los datos: "+titulo
            +"; "+autor+"; "+publicado);
            
            //crear nodo título
            Node nodeTitulo = doc.createElement("Titulo"); //crea la etiqueta <Titulo/> en sí
            Node nodeTitulo_text = doc.createTextNode(titulo); //añade el título a la etiqueta
            nodeTitulo.appendChild(nodeTitulo_text);
            //crear nodo autor
            Node nodeAutor = doc.createElement("Autor");
            Node nodeAutor_text = doc.createTextNode(autor);
            nodeAutor.appendChild(nodeAutor_text);
            //crear libro, con atributo e hijos anteriores
            Node nodeLibro = doc.createElement("Libro");
            ((Element)nodeLibro).setAttribute("publicado", publicado);
            nodeLibro.appendChild(nodeTitulo);
            nodeLibro.appendChild(nodeAutor);
            
            //añadimos libro al root
            nodeLibro.appendChild(doc.createTextNode("\n"));
            Node root = doc.getFirstChild();
            root.appendChild(nodeLibro);
            System.out.println("Libro agregado exitosamente.\n");
            return 0;
            
        } catch (Exception ex){
            System.out.println("Error: " +ex);
            return -1;
        }
    }
    
}
