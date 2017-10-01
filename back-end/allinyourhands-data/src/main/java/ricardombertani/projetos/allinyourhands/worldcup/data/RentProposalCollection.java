package ricardombertani.projetos.allinyourhands.worldcup.data;

import java.util.List;

public class RentProposalCollection {

	private List<RentProposal> rentProposals;

	public RentProposalCollection(){
		
	}
	
	public List<RentProposal> getRentProposals() {
		return rentProposals;
	}

	public void setRentProposals(List<RentProposal> rentProposals) {
		this.rentProposals = rentProposals;
	}
	
	public void add(RentProposal rentProposal){
		rentProposals.add(rentProposal);
	}
	
	

}
