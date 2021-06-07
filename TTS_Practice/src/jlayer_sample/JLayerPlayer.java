package jlayer_sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class JLayerPlayer {

	private String fileName;
	
	public JLayerPlayer() {
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void play() {
		FileInputStream fileInputStream;
		
		try {
			fileInputStream = new FileInputStream(fileName);
			Player player = new Player(fileInputStream);
			player.play();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public void play(String fileName) {
		this.fileName = fileName;
	}
}
