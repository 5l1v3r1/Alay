package utils;

public class PythonResult {
	private String imgPlate;
	private String imgSuccesful;
	private String numPlate;
	private long execTime;

	public long getExecTime() {
		return execTime;
	}

	public void setExecTime(long execTime) {
		this.execTime = execTime;
	}

	public String getImgPlate() {
		return imgPlate;
	}

	public void setImgPlate(String imgPlate) {
		this.imgPlate = imgPlate;
	}

	public String getImgSuccesful() {
		return imgSuccesful;
	}

	public void setImgSuccesful(String imgSuccesful) {
		this.imgSuccesful = imgSuccesful;
	}

	public String getNumPlate() {
		return numPlate;
	}

	public void setNumPlate(String numPlate) {
		this.numPlate = numPlate;
	}

	public PythonResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PythonResult(String imgPlate, String imgSuccesful, String numPLate) {
		super();
		this.imgPlate = imgPlate;
		this.imgSuccesful = imgSuccesful;
		this.setNumPlate(numPLate);
	}

	@Override
	public String toString() {
		return "PythonResult [imgPlate=" + imgPlate + ", imgSuccesful=" + imgSuccesful + ", numPlate=" + numPlate + "]";
	}

	public void setExectime(long l) {
		this.execTime = l;

	}

	public long getExectime() {
		return this.execTime;
	}
}
