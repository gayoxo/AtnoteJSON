/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Cesar y Gayoso.
 */
public interface GWTServiceAsync {


	void GetJason(AsyncCallback<String> callback);

	void GetJason2(AsyncCallback<String> callback);

}
