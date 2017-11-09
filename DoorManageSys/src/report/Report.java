package report;

import java.io.IOException;

public interface Report {

	public void setUpFirstPage () throws IOException;
	public void setUpNextPage () throws IOException;
	public void populateReport () throws IOException;
	
}
