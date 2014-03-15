package lector.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lector.client.book.reader.GWTService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import lector.share.model.Catalogo;
import lector.share.model.FileDB;
import lector.share.model.FolderDB;

public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

	@PersistenceContext(name = "BookReader11Abr01PU")
	private EntityManager entityManager;
	public String GetJason() {
		List<FileDB> lista = loadEntityFileDB();
		List<FolderDB> lista2 = loadEntityFolderDB();
		Catalogo Catalog=loadCatalog();
		JSONObject responseObject = new JSONObject();
		HashSet<Long> Annotaciones=new HashSet<Long>();
	    List<JSONObject> authorList = new LinkedList<JSONObject>();
	    try {
	        for (FileDB author : lista) {
	            JSONObject jsonAuthor = new JSONObject();
	            
	            jsonAuthor.put("Id", author.getId());
	            
	            jsonAuthor.put("Name", author.getName());
	            
	            List<JSONObject> padresList = new LinkedList<JSONObject>();
	            for (Long padres : author.getFathers()) {
	            	JSONObject padre = new JSONObject();
	            	padre.append("Id", padres);
	            	padresList.add(padre);
				}
	            jsonAuthor.put("Fathers", padresList);   
	            
	            for (Long jsonObject : author.getAnnotationsIds()) {
					if (!Annotaciones.contains(jsonObject))
						Annotaciones.add(jsonObject);
				}
	            
	            jsonAuthor.put("Number of Annotations", author.getAnnotationsIds().size());
	            
	            jsonAuthor.put("Sons", new LinkedList<JSONObject>());  
	            
	            authorList.add(jsonAuthor);
	        }
	        
	        for (FolderDB author : lista2) {
	            JSONObject jsonAuthor = new JSONObject();
	            
	            jsonAuthor.put("Id", author.getId());
	            
	            jsonAuthor.put("Name", author.getName());
	            
	            List<JSONObject> padresList = new LinkedList<JSONObject>();
	            for (Long padres : author.getFathers()) {
	            	JSONObject padre = new JSONObject();
	            	padre.append("Id", padres);
	            	padresList.add(padre);
				}
	            jsonAuthor.put("Fathers", padresList);  
	            
	            List<JSONObject> HijosList = new LinkedList<JSONObject>();
	            for (Long hijo : author.getEntryIds()) {
	            	JSONObject Hijos = new JSONObject();
	            	Hijos.append("Id", hijo);
	            	HijosList.add(Hijos);
				}
	            
	            jsonAuthor.put("Number of Annotations", 0);
	            
	            jsonAuthor.put("Sons", HijosList);  
	            
	            
	            authorList.add(jsonAuthor);
	        }	        
	        
	        responseObject.put("Id", 237012);
	        responseObject.put("Name", Catalog.getCatalogName());
	        
	        List<JSONObject> HijosList = new LinkedList<JSONObject>();
            for (Long hijo : Catalog.getEntryIds()) {
            	JSONObject Hijos = new JSONObject();
            	Hijos.append("Id", hijo);
            	HijosList.add(Hijos);
			}
	        responseObject.put("Sons", HijosList);
	        
	        responseObject.put("Number of Annotations", Annotaciones.size());
	        
	        responseObject.put("Elements", authorList);
	        
	    } catch (JSONException ex) {
	        ex.printStackTrace();
	    }
	    return responseObject.toString();
	}

	
	public List<FileDB> loadEntityFileDB() {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		String sql = "SELECT r FROM FileDB r" +
				" Where r.catalogId=237012" +
				"";
		list = entityManager.createQuery(sql).getResultList();
		return list;
	}
	
	
	public List<FolderDB> loadEntityFolderDB() {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FolderDB> list;
		String sql = "SELECT r FROM FolderDB r" +
				" Where r.catalogId=237012" +
				"";
		list = entityManager.createQuery(sql).getResultList();
		return list;
	}
	
	public Catalogo loadCatalog() {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Catalogo> list;
		String sql = "SELECT r FROM Catalogo r" +
				" Where r.id=237012" +
				"";
		list = entityManager.createQuery(sql).getResultList();
		return list.get(0);
	}
	
	
	public String GetJason2()
	{
	   
		JSONObject responseObject = new JSONObject();
		Catalogo Catalog=loadCatalog();
		
		 try {
		responseObject.put("Id", 237012);
        responseObject.put("Name", Catalog.getCatalogName());
        
        List<JSONObject> HijosList = new LinkedList<JSONObject>();
        for (Long hijo : Catalog.getEntryIds()) { 	
        	HijosList.add(JSONHIjo(hijo));
		}
        responseObject.put("Sons", HijosList);
        
        responseObject.put("Number of Annotations", loadTotalCatalog());
        
	    } catch (JSONException ex) {
	        ex.printStackTrace();
	    }
		return responseObject.toString();
	}


	private JSONObject JSONHIjo(Long elementoHijo) {
		JSONObject Hijos = new JSONObject();
    	try {
			Hijos.append("Id", elementoHijo);
			int Number=0;
			ArrayList<Long> FF=new ArrayList<Long>();;
			FileDB F=LoadFile(elementoHijo);
			if (F!=null)
				{
				Number=F.getAnnotationsIds().size();
				Hijos.put("Name", F.getName());
				}
			else 
				{
				FolderDB FO=LoadFolder(elementoHijo);
				FF=FO.getEntryIds();
				Hijos.put("Name", FO.getName());
				}
			Hijos.append("Number of Annotations", Number);
			
			List<JSONObject> HijosList = new LinkedList<JSONObject>();
	        for (Long hijonew : FF) { 	
	        	HijosList.add(JSONHIjo(hijonew));
			}
	        Hijos.put("Sons", HijosList);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Hijos;
	}


	private FolderDB LoadFolder(Long hijo) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FolderDB> list;
		String sql = "SELECT r FROM FolderDB r" +
				" Where r.id=" +hijo +
				"";
		list = entityManager.createQuery(sql).getResultList();
		if (list.isEmpty())
			return null;
		else return list.get(0);
	}


	private FileDB LoadFile(Long hijo) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		String sql = "SELECT r FROM FileDB r" +
				" Where r.id=" +hijo +
				"";
		list = entityManager.createQuery(sql).getResultList();
		if (list.isEmpty())
			return null;
		else return list.get(0);
	}


	private Integer loadTotalCatalog() {
		HashSet<Long> Annotaciones=new HashSet<Long>();
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		String sql = "SELECT r FROM FileDB r" +
				" Where r.catalogId=237012" +
				"";
		list = entityManager.createQuery(sql).getResultList();
		for (FileDB folderDB : list) {
			 for (Long jsonObject : folderDB.getAnnotationsIds()) {
					if (!Annotaciones.contains(jsonObject))
						Annotaciones.add(jsonObject);
				}
		}
		
		
		return Annotaciones.size();
	}
}
