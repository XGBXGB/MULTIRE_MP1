package model;

public class ResultImageData {
	private String fileName;
	private double value;
	
	public ResultImageData(String fileName, double value) {
		super();
		this.fileName = fileName;
		this.value = value;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
