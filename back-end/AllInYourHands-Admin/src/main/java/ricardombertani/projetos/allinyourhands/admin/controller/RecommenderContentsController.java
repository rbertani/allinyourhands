package ricardombertani.projetos.allinyourhands.admin.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import ricardombertani.projetos.allinyourhands.admin.locator.ServiceLocator;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;



@ManagedBean(name = "recommenderContentsController")
public class RecommenderContentsController {
		     
	    private List<SuggestedContent> recommendedContentsList;
	    private List<SuggestedContent> selectedRecommendedContentsList;
	   
	    @PostConstruct
	    public void init() {
	       
	    	recommendedContentsList = ServiceLocator.getInstance().getCoreFacade().getSuggestedContents();
	    
	         
	    }
	    
	        		
		public List<SuggestedContent> getRecommendedContentsList() {
			return recommendedContentsList;
		}

		public void setRecommendedContentsList(List<SuggestedContent> recommendedContentsList) {
			this.recommendedContentsList = recommendedContentsList;
		}

			
		public List<SuggestedContent> getSelectedRecommendedContentsList() {
			return selectedRecommendedContentsList;
		}


		public void setSelectedRecommendedContentsList(List<SuggestedContent> selectedRecommendedContentsList) {
			this.selectedRecommendedContentsList = selectedRecommendedContentsList;
		}


		public void deleteContents(){
			
			List<String> recommendedContentIds = new LinkedList<String>();
			
			for(SuggestedContent suggestedContent : selectedRecommendedContentsList){
				recommendedContentIds.add(suggestedContent.getId());
			}
			
			ServiceLocator.getInstance().getCoreFacade().deleteSuggestedContents(recommendedContentIds);
			
		}


		/*public void onTransfer(TransferEvent event) {
	        StringBuilder builder = new StringBuilder();
	        for(Object item : event.getItems()) {
	            builder.append( ((SuggestedContent) item).getName() ).append("<br />");
	        }
	         
	        FacesMessage msg = new FacesMessage();
	        msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        msg.setSummary("Items Transferred");
	        msg.setDetail(builder.toString());
	         
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    } 
	 
	    public void onSelect(SelectEvent event) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	    }
	     
	    public void onUnselect(UnselectEvent event) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	    }
	     
	    public void onReorder() {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	    } */

}
