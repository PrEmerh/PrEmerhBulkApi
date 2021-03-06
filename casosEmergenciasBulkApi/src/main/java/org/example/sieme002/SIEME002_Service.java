package org.example.sieme002;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-10-31T14:33:39.808+01:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "SIEME002", 
                  wsdlLocation = "file:/C:/Users/DTUser/workspace/PrEmerHe/casosEmergencias/src/main/resources/webservices/wsdl/SIEME002.WSDL",
                  targetNamespace = "http://www.example.org/SIEME002/SIEME002") 
public class SIEME002_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/SIEME002/SIEME002", "SIEME002");
    public final static QName LDAPCHSOAPServiceSIEME002 = new QName("http://www.example.org/SIEME002/SIEME002", "LDAPCH_SOAPService_SIEME002");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/DTUser/workspace/PrEmerHe/casosEmergencias/src/main/resources/webservices/wsdl/SIEME002.WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SIEME002_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/DTUser/workspace/PrEmerHe/casosEmergencias/src/main/resources/webservices/wsdl/SIEME002.WSDL");
        }
        WSDL_LOCATION = url;
    }

    public SIEME002_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SIEME002_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SIEME002_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public SIEME002_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SIEME002_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SIEME002_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns SIEME002
     */
    @WebEndpoint(name = "LDAPCH_SOAPService_SIEME002")
    public SIEME002 getLDAPCHSOAPServiceSIEME002() {
        return super.getPort(LDAPCHSOAPServiceSIEME002, SIEME002.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SIEME002
     */
    @WebEndpoint(name = "LDAPCH_SOAPService_SIEME002")
    public SIEME002 getLDAPCHSOAPServiceSIEME002(WebServiceFeature... features) {
        return super.getPort(LDAPCHSOAPServiceSIEME002, SIEME002.class, features);
    }

}
