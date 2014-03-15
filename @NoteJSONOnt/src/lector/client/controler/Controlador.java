package lector.client.controler;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Controlador implements EntryPoint {



	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private TextArea TA;
	private TextArea TA2;

    /**
     * @wbp.parser.entryPoint
     */
    public void onModuleLoad() {
    	RootPanel R=RootPanel.get();
    	VerticalPanel SP=new VerticalPanel();
    	SP.setSize("100%", "100%");
    	R.add(SP);
    	TA=new TextArea();
    	TA.setSize("100%", "100%");
    	SP.add(TA);
    	TA2=new TextArea();
    	TA2.setSize("100%", "100%");
    	SP.add(TA2);
    	bookReaderServiceHolder.GetJason(new AsyncCallback<String>() {
			
			public void onSuccess(String result) {
				TA.setText(result);
				System.err.println(result);
				
			}
			
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
    	
bookReaderServiceHolder.GetJason2(new AsyncCallback<String>() {
			
			public void onSuccess(String result) {
				TA2.setText(result);
				System.err.println(result);
				
			}
			
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
    }

   
}
