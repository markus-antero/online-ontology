package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

/** 
 * Servlet implementation class RDGResponseServlet
 * @author markus
 */
@WebServlet("/RDGResponseServlet")
public class RDGResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RDGResponseServlet() {
        super();
    }
	
	public String filePath = "/home/markus/git/everythingtoeverything/Ties437_group2/WebContent/resources/RDG.json";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.filePath = "";
		String m = request.getParameter("model");
		
		OutputStream os = response.getOutputStream();
		
		Model model = ModelFactory.createDefaultModel();
		if (m.contains("ontology")){
			FileManager.get().readModel(model, "http://localhost/everything/groups/Group0/booking_v2rdf_final.owl");
			model.write(os, "TURTLE");
		
		}else if (m.contains("rdg")){
			FileManager.get().readModel(model, "http://localhost/everything/groups/Group0/cottage.pdg");
			model.write(os, "TURTLE");
		
		}else if (m.contains("rig")){
			FileManager.get().readModel(model, "http://localhost/everything/groups/Group0/serviceDescription.rdg");
			model.write(os, "TURTLE");
		}		
	}
}
