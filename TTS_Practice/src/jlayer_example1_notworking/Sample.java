package jlayer_example1_notworking;

public class Sample {
	private short[] buffer;
	private int size;
	
	public Sample(short[] buffer, int size) {
		this.buffer = buffer;
		this.size = size;
	}
	
	public short[] getBuffer() {
		return buffer;
	}
	
	public int getSize() {
		return size;
	}
}
