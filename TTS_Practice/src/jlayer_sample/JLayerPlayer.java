package jlayer_sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import naver_clova_sample.NaverClovaTTS;
import naver_clova_sample.TTSSpeaker;

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
		play();
	}
	
	public static void main(String[] args) {
		NaverClovaTTS ncTTS = new NaverClovaTTS();
		Map<String, TTSSpeaker> speakers = ncTTS.getSpeakers();
	
		JLayerPlayer player = new JLayerPlayer();
		
//		player.play("./resources/voiceSample/sample_test_nara.mp3");
		
		for (String key : speakers.keySet()) {
			TTSSpeaker speaker = speakers.get(key);
			
//			System.out.println(speaker.getName());
			
			String fileName = "./resources/voiceSample/sample_test_" + speaker.getName() + ".mp3";
			
//			System.out.println(fileName);
			
			player.play(fileName);
//			break;
		}
	}
}
