package report;


public enum ReportTypes {

	INVENTORY("Inventory Report"),
	QUOTE("Quote Report"),
	BLUEPRINT("Blueprint Report");
	
	private final String type;
	
	ReportTypes(String type) {
		this.type = type;
	}
	
	public String toString() {
		return this.type;
	}
}
