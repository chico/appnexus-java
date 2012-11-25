package appnexus.api;

public interface Fixtures {
  
  public final static String DUMMY_TOKEN = "fiuho88jtuu5qef9ofmglbmcn7";

  public final static String AUTH_RESPONSE = "{\n" + 
  		"    \"response\": {\n" + 
  		"        \"status\": \"OK\", \n" + 
  		"        \"token\": \"" + DUMMY_TOKEN + "\"\n" + 
  		"    }\n" + 
  		"}";
  
  public final static String MEMBER_RESPONSE = "{\n" + 
  		"    \"response\": {\n" + 
  		"        \"member\": {\n" + 
  		"            \"developer_id\": null,\n" + 
  		"            \"id\": 2182, \n" + 
  		"            \"name\": \"HulkMedia\", \n" + 
  		"            \"state\": \"active\", \n" + 
  		"            \"timezone\": \"EST5EDT\"\n" + 
  		"        }, \n" + 
  		"        \"status\": \"OK\"\n" + 
  		"    }\n" + 
  		"}";
  
  public final static Member MEMBER = new Member.Builder().name("HulkMedia").id("2182").build();

}
