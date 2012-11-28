package appnexus.api;

public class CampaignResponse extends Response {

  private static final long serialVersionUID = 1L;

  private Campaign campaign;

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }
  
}
