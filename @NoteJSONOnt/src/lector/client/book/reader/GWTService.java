/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/gwtservice")
public interface GWTService extends RemoteService {

	
	String GetJason();
	
	String GetJason2();
}
