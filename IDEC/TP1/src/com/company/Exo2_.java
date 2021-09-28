package com.company;
import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.filter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Exo2_ {
    static List<org.jdom2.Document> document;
    static List<Element> racine;
    static String csvFilePath = "res3.csv";


    public static void main(String[] args){
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = new ArrayList<>();
            document.add(sxb.build(new File("src/com/company/data/angleterre.xml")));
            document.add(sxb.build(new File("src/com/company/data/espagne.xml")));
            document.add(sxb.build(new File("src/com/company/data/italie.xml")));

        }catch (Exception e){}
        racine = new ArrayList<>();
        for(org.jdom2.Document i : document){
            racine.add( i.getRootElement());
        }
        parserAll();
    }
    static void parserAll(){
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            fileWriter.write("ville,id,age,nom prenom,email,tel");
            for(Element element : racine) {
                List listPersons = element.getChildren("personne");
                Iterator i = listPersons.iterator();
                System.out.println(listPersons.size());

                while (i.hasNext()) {
                    Element courant = (Element) i.next();
                    String line = String.format("%s,%s,%s,\'%s\',%s,%s",
                            courant.getChild("ville").getText(),
                            courant.getAttribute("id").getValue(),
                            courant.getChild("age").getText(),
                            courant.getChild("prenom").getText() + " " + courant.getChild("nom").getText(),
                            courant.getChild("email").getText(),
                            courant.getChild("telephone").getText());
                    fileWriter.newLine();
                    fileWriter.write(line);
                    }
            }
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
