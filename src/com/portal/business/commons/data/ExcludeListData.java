package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.ExcludeList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//import com.remettence.commons.utils.EjbUtils;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExcludeListData extends AbstractBusinessPortalWs {
    
    private List<ExcludeList>  excludeList;
    
    Boolean debugMode = Boolean.FALSE;
    
    Element eElement;

    public String getNodeString(String itemString) {
        NodeList nodeList;
        Node node;
        String respString;

        respString = "NULL";

        node = null;
        nodeList = eElement.getElementsByTagName(itemString);

        node = nodeList.item(0);
        if (node != null) {
            respString = node.getTextContent();
        }

        nodeList.getLength();

        return respString;
    }
    

    public BufferedReader get_URL_SDN_Descriptor(String URL_SDN) {
        URL url = null;
        BufferedReader br = null;

        if(debugMode) System.out.println("* * * * INICIANDO * * * * ");

        try {

            try {

                if(debugMode) System.out.println("* * * * CONTACTANDO URL * * * * ");
                if(debugMode) System.out.println("== Web Address: " + URL_SDN);

                url = new URL(URL_SDN);
            } catch (MalformedURLException ex) {
                java.util.logging.Logger.getLogger(ExcludeListData.class.getName()).log(Level.SEVERE, null, ex);
            }

            URLConnection conn = null;

            try {
                if(debugMode) System.out.println("* * * * CONECTANDO * * * * ");
                conn = url.openConnection();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ExcludeListData.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if(debugMode) System.out.println("* * * * BUFFER * * * * ");
                // open the stream and put it into BufferedReader
                br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ExcludeListData.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return br;
    }

    public void write_URL_SDN_File(File fXmlFile, BufferedReader br) {

        BufferedWriter bw = null;
        FileWriter fw = null;
        String line;
        try {
            //String content = "This is the content to write into file\n";

            fw = new FileWriter(fXmlFile);
            bw = new BufferedWriter(fw);

            while ((line = br.readLine()) != null) {
                bw.write(line);
                // must do this: .readLine() will have stripped line endings
                bw.newLine();
            }

            bw.close();
            fw.close();

            if(debugMode) System.out.println("* * * LISTO DESCARGA ARCHIVO SDN.XML * * *");

            if(debugMode) System.out.println("* * * PARSING ARCHIVO SDN.XML * * *");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

//                response.setCode(ResponseCodes.SUCCESSFUL_OPERATION);
//                response.setMessage(ResponseCodes.codes.get(ResponseCodes.SUCCESSFUL_OPERATION));
        }

        //return fXmlFile;
    }         

    public void readXMLFile(File fXmlFile) {

        ExcludeList excludeList;

        ExcludeListData excludeListData = new ExcludeListData();

        try {

            // String lastNameString = "";            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc;
            NodeList nList;
            String addressTemp;
            try {                  
                doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();
                if(debugMode) System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                nList = doc.getElementsByTagName("sdnEntry");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        //Element 

                        eElement = (Element) nNode;

//                    if(getNodeString("lastName").equals("RODRIGUEZ OREJUELA")) {
//                        NodeList eee = eElement.getElementsByTagName("idList");
//                        System.out.println("COUNT####### " + eee.getLength() );
//                    }
                        // if (getNodeString("lastName").equals("EL AISSAMI MADDAH")) {
                        //System.out.println("EXCLUDE \n\n" + excludeList.toString());
                        if(debugMode) {
                            System.out.println("");
                            System.out.println("###########################");
                            System.out.println("");
                            System.out.println("LAST NAME: " + getNodeString("lastName") + " FIRST NAME: " + getNodeString("firstName"));
                        }
                        
                        NodeList eee = eElement.getElementsByTagName("id");

                        int intNumIdNodes = eee.getLength();

                        if (intNumIdNodes == 0) {

                            excludeList = new ExcludeList();
                            excludeList.setLastName(getNodeString("lastName"));
                            excludeList.setFirstName(getNodeString("firstName"));
                            excludeList.setDateOfBirth(getNodeString("dateOfBirth"));
                            excludeList.setPlaceOfBirth(getNodeString("placeOfBirth"));
                            excludeList.setSdnType(getNodeString("sdnType"));
                            excludeList.setRemarks(getNodeString("remarks"));
                            excludeList.setIdCountry(getNodeString("idCountry"));
                            excludeList.setIdList(getNodeString("idList"));
                            excludeList.setTitle(getNodeString("title"));
                            excludeList.setAddressList(getNodeString("addressList"));
                            excludeListData.saveExcludeList(excludeList);

                        } else {

                            for (int l = 0; l < intNumIdNodes; l++) {

                                excludeList = new ExcludeList();

                                int numSubNodes = eee.item(l).getChildNodes().getLength();

                                if (numSubNodes == 0) {

                                    if(debugMode) System.out.println("############# SUBNODOS VACIOS");

                                } else {

                                    for (int k = 0; k < numSubNodes; k++) {

                                        String strRespuestaSubNodo = eee.item(l).getChildNodes().item(k).getTextContent();

                                        if (!strRespuestaSubNodo.trim().equals("")) {

                                            excludeList.setLastName(getNodeString("lastName").trim());
                                            excludeList.setFirstName(getNodeString("firstName").trim());
                                            excludeList.setDateOfBirth(getNodeString("dateOfBirth").trim());
                                            excludeList.setPlaceOfBirth(getNodeString("placeOfBirth").trim());
                                            excludeList.setSdnType(getNodeString("sdnType").trim());

                                            String remarksTemp = getNodeString("remarks").trim();
                                            excludeList.setRemarks(remarksTemp.substring(0, Math.min(390, remarksTemp.length() - 1)));

                                            excludeList.setIdCountry(getNodeString("idCountry").trim());

                                            String idListTemp = getNodeString("idList").trim();
                                            excludeList.setIdList(idListTemp.substring(0, Math.min(490, idListTemp.length() - 1)));

                                            excludeList.setTitle(getNodeString("title").trim());

                                            addressTemp = getNodeString("addressList").trim();
                                            excludeList.setAddressList(addressTemp.substring(0, Math.min(3990, addressTemp.length() - 1)));
                                            //excludeList.setIdType(getNodeString("idType"));

                                            if(debugMode)System.out.println(" >>>>>>> ELEMENTO " + l + "SUB-Elemento: " + k + " = " + strRespuestaSubNodo);

                                            if (k == 3) {
                                                excludeList.setIdType(strRespuestaSubNodo);
                                            }
                                            if (k == 5) {
                                                excludeList.setIdValue(strRespuestaSubNodo);
                                            }
                                            if (k == 7) {
                                                excludeList.setIdCountry(strRespuestaSubNodo);
                                            }

                                        }

                                    }
                                }
                                excludeListData.saveExcludeList(excludeList);

                                Random rn;
                                int answer;

                                rn = new Random();
                                answer = rn.nextInt(10) + 1;

                                if (answer == 5) {
                                    if(debugMode) System.out.println("EXCLUDE \n\n" + excludeList.toString());
                                    if(debugMode) System.out.println("COUNT ==> " + temp);
                                }

                                //System.out.println("eee.item(l).getChildNodes() nodos count" + eee.item(l).getChildNodes());
                                //System.out.println("eee.item(l).getTextContent()" + eee.item(l).getLocalName());
                                //System.out.println("eee.item(l).getFirstChild().getNodeValue()" + eee.item(l).getFirstChild().getNodeValue());
                                //System.out.println("eee.item(l).getNodeName()" + eee.item(l).getFirstChild().getNodeName());
                                //System.out.println("eee.item(l).getFirstChild().getNodeName()" + eee.item(l).getFirstChild().getNodeName());
                                if(debugMode) System.out.println("COUNT####### " + eee.getLength());
                                Node node = eee.item(0);
                                if (node != null) {
                                    String respString = node.getTextContent();
                                }
                            }
                        }

                        //              }
                    }
                }
                if(debugMode) System.out.println("FINAL COUNT:" + nList.getLength());

            } catch (org.xml.sax.SAXParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public List<ExcludeList> loadExcludeListList(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        
        List<ExcludeList> lista = null;
        lista = (List<ExcludeList>) listEntities(ExcludeList.class, request, logger, getMethodName());
        return lista;
    }
    
    public ExcludeList loadExcludeList(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {

        ExcludeList excludeList = null;
        try {
            excludeList = (ExcludeList) loadEntity(ExcludeList.class, request, logger, getMethodName());
        } catch (RegisterNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExcludeListData.class.getName()).log(Level.SEVERE, null, ex);
        }

        return excludeList;
    } 

    public ExcludeList saveExcludeList(ExcludeList excludeList)  {
         
        try {
            excludeList = (ExcludeList) saveEntity(excludeList);
            //return (ExcludeList) saveEntity(request, logger, getMethodName());
        } catch (GeneralException ex) {
            java.util.logging.Logger.getLogger(ExcludeList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            java.util.logging.Logger.getLogger(ExcludeList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return excludeList;
    }   
    
    private static final Logger logger = Logger.getLogger(ExcludeList.class);
 
//    private void makeRoolback(EntityTransaction transaction) {
//        try {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//        } catch (Exception ex1) {
//            ex1.printStackTrace();
//        }
//    }
    
    
    
    public void deleteOFACList() {
//              String queryString2 = "truncate table ExcludeList";
//              Query query = entityManager.createNativeQuery(queryString2);
//              List<Float> result = query.getResultList();
        //entityManager.createQuery("DELETE FROM ExcludeList e").executeUpdate();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            
            //entityTransaction.isActive();
            entityTransaction.begin();
            entityManager.createNativeQuery("TRUNCATE TABLE ExcludeList").executeUpdate();
            entityTransaction.commit();
            if(debugMode) System.out.println("#### BD OFAC BORRADA EXITOSAMETE");

        } catch (Exception e) {
            entityTransaction.rollback();
            System.out.println("exception" + e.getMessage());
        }
    }
    
    
    public Float queryMatchingPercent(  String lastName, 
                                        String firstName, 
                                        String country, 
                                        String dateOfBirth, 
                                        String idValue,
                                        String addressList,
                                        Float threshold) throws NullParameterException {
        try{
        if(lastName   == null ||  lastName   == "") {
            throw new NullParameterException("Lastname cannot be null");
        }
        
        if (threshold == null || threshold == 0.0F) {
            throw new NullParameterException("Threshold field cannot be Null or Zero");
        }
        } catch (NullParameterException e) {
            e.printStackTrace();
        }
        
        if(debugMode) System.out.println("########### Checking SDN LIST 1");
        
        String queryString2;    // where idcountry like '%"+country+"%'
        
        
        Query query;
        

        
        
        queryString2 = "SELECT ";
        queryString2 += "((jws(lastName, '" + lastName + "') ";
        
        int divisor = 5;
        if (firstName   == null ||  firstName   == "") {
            divisor--;
        } else {    
            queryString2 += "+ jws(firstName, '"      + firstName + "') ";
        }
        if (idValue     == null || idValue      == "") {
            divisor--;
        } else {
            queryString2 += "+ jws(idValue, '"        + idValue + "') ";    
        }
        if (dateOfBirth     == null || dateOfBirth  == "") {
            divisor--;
        } else {
            queryString2 += "+ jws(dateOfBirth, '"    + dateOfBirth + "') ";
        }
        if (addressList     == null || addressList  == "") {
            divisor--;
        } else {
            //queryString2 += "+ jws(addressList, '"    + addressList + "') ";
            queryString2 += "+ (addressList like '%" + addressList + "%') ";
        }

       
        queryString2    += ") / " + divisor + ")";
        queryString2    +=  " s ";
        queryString2    += "FROM ExcludeList s ";
//                + "where idcountry like '%"+country+"%' "
//                + "AND dateOfBirth like '%"+dateOfBirth+"%' "
//                + "AND idValue like '%"+idValue + "%' "
        queryString2 += "having s > " + threshold;
        queryString2 += " ORDER BY s desc";
        
       // queryString2 = "SELECT COUNT(s) FROM ExcludeList s";
        
         query = entityManager.createNativeQuery(queryString2);
        try {
            List<Float> result = query.getResultList();// getSingleResult();
            if( result.size() > 0) {
                    if(debugMode) System.out.println("# # # # # # # MAX RESULT: " + result.get(0));
            return (Float.parseFloat(""+result.get(0)));
            } else {
                if(debugMode) System.out.println("# # # # # # # NO RESULTS");
                return 0.0F;
            }
            
        } 

        catch (Exception e) {
            System.out.println("# # # # # # # ERROR");
            e.printStackTrace();
            return 0.0F;
        }
        //int rrr = (Integer)result;

    }
    
//    public ExcludeList salvaUno(){
//        
//        ExcludeList excludeList = new ExcludeList();
//        excludeList.setFirstName("YUUPIII");
//        return saveExcludeList(excludeList);
//    }
    
//    public void salvaQuery(String queryString) {
//
//try {
//    String queryString2 = "INSERT INTO ExcludeList (sdnType,lastName,firstName) VALUES ('444', '123','123');";
//    Query query = entityManager.createNativeQuery(queryString2);
////    query.setParameter("1", new Date(todaysMidnite.getTimeInMillis()));
////    query.setParameter("2", new Date(tomorrowsMidnite.getTimeInMillis()));
////    query.setParameter("3", storeId);
//
//    int result = query.executeUpdate();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        
//    }
    
    
    
    
}
