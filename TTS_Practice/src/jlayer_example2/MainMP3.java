package jlayer_example2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MainMP3 {

	public static void main(String[] args) {
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("1622630526371.mp3");
			Player player = new Player(fileInputStream);
			player.play();
			System.out.println("Song is Playing");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
		
	}

}
