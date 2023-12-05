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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Robyn
 */
public class AccesoDOM {
    
    Document doc; //creamos el documento que inicializaremos para que apunte al árbol DOM
    
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
            return 0;
        }catch(Exception ex){
            System.out.println("Error" +ex);
            ex.printStackTrace();
            return -1;
        }
    }
    
    public void recorreDOMyMuestra(){ //función que muestra el árbol DOM por pantalla
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
    
    public int insertarLibroEnDOM(String titulo, String autor, String publicado){ //función que añade un libro al DOM dados unos parámetros
        System.out.println("Añadiendo libro al DOM con los datos: "+titulo
            +"; "+autor+"; "+publicado);
        try{
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
            ex.printStackTrace();
            return -1;
        }
    }
    
    public int borrarLibro(String titulo){ //elimina un libro del DOM por título
        System.out.println("Se va a borrar el libro " + titulo + "...");
        try {
            //Node root = doc.getDocumentElement();
            NodeList nlTitulo = doc.getElementsByTagName("Titulo"); //coge los nodos Titulo
            Node node;
            for (int i=0;i<nlTitulo.getLength();i++){
                node = nlTitulo.item(i);
                //if(node.getNodeType() == Node.ELEMENT_NODE){} innecesario por getElementsByTagName()
                if(node.getChildNodes().item(0).getNodeValue().equals(titulo)){
                    System.out.println("Borrando libro con título: " +titulo+ "...");
                    //accedemos al parent node hasta llegar al root y borramos su hijo libro
                    node.getParentNode().getParentNode().removeChild(node.getParentNode());
                }
            }
            System.out.println("Libro borrado.\n");
            return 0;
        } catch (Exception ex){
            System.out.println("Error: " +ex);
            ex.printStackTrace();
            return -1;
        }
    }
    
    public void guardarDOMaArchivo (String archivo) { //guarda el árbol DOM con sus cambios en un fichero
        try{
            Source source = new DOMSource(doc); //origen
            StreamResult result = new StreamResult (new File(archivo)); //destino
            //declarando el Transformer con el método transform que vamos a usar
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //propiedad para darle una sangría al archivo
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //transform() utiliza el origen (árbol DOM) y destino establecidos
            transformer.transform(source, (javax.xml.transform.Result) result);
            
            System.out.println("Archivo creado con éxito.");
        } catch (Exception ex) {
            System.out.println("Error: "+ex);
            ex.printStackTrace();
        }
    }
    
}
