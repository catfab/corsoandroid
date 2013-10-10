package com.example.corso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class Parser {
	
	ArrayList<Persona> parsedData = new ArrayList<Persona>();
	
	public ArrayList<Persona> getParsedData() {
		
		return parsedData;
	}
	
	 public void parseXml(String xmlUrl){

	        Document doc;
	        try {

	            doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(xmlUrl).openStream());
	            //Costruiamo il nostro documento a partire dallo stream dati fornito dall'URL
	            Element root=doc.getDocumentElement();
	            //Elemento(nodo) radice del documento

	            Log.v("debugParse","Root element :" + root.getNodeName());
	            Log.v("debugParse", "");

	            //NodeList notes=root.getElementsByTagName("persone"); //potremmo direttamente prendere gli elementi note
	            NodeList notes=root.getChildNodes();
	            //ma prediamo tutti i "figli" diretti di root. Utile se non avessimo solo "note" come figli di root

	            for(int i=0;i<notes.getLength();i++){//per ogni
	                Node c= notes.item(i);//nodo

	                if(c.getNodeType()==Node.ELEMENT_NODE){//controlliamo se questo è un nodo elemento (un tag)
	                    //se avessimo usato root.getElementsByTagName("persone") questo controllo
	                    //non sarebbe stato necessario

	                    Persona newPersona=new Persona(-1);

	                    Element note=(Element)c; //cast da nodo a Elemento

	                    //non controlliamo if(note.getNodeName().equals("note"))
	                    // in quanto sappiamo di avere solo "persone" come childs

	                    NodeList noteDetails=c.getChildNodes();  //per ogni nota abbiamo i vari dettagli
	                    for(int j=0;j<noteDetails.getLength();j++){
	                        Node c1=noteDetails.item(j);

	                        if(c1.getNodeType()==Node.ELEMENT_NODE){ //anche in questo caso controlliamo se si tratta di tag
	                            Element detail=(Element)c1; //cast
	                            String nodeName=detail.getNodeName(); //leggo il nome del tag
	                            String nodeValue=detail.getFirstChild().getNodeValue();//leggo il testo in esso contenuto


	                            //a dipendenza del nome del nodo (del dettaglio) settiamo il relativo valore nell'oggetto
	                            if(nodeName.equals("nome"))
	                                newPersona.setNome(nodeValue);
	                            if(nodeName.equals("cognome"))
	                                newPersona.setCognome(nodeValue);
	                            if(nodeName.equals("ddn"))
	                                newPersona.setDataNascita(nodeValue);
	                            if(nodeName.equals("numero"))
	                                newPersona.setNumTelefono(nodeValue);
	                        }
	                    }
	                    Log.v("debugParse","");

	                    parsedData.add(newPersona); //aggiungiamo il nostro oggetto all'arraylist
	                }

	            }
	            //gestione eccezioni
	        } catch (SAXException e) {
	        	Log.v("debugParse",e.toString());
	        } catch (IOException e) {
	        	Log.v("debugParse",e.toString());
	        } catch (ParserConfigurationException e) {
	        	Log.v("debugParse",e.toString());
	        } catch (FactoryConfigurationError e) {
	        	Log.v("debugParse",e.toString());
	        }

	    }
}
