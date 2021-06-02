package naver_clova_sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NaverClovaTTS {

	private String clientId;// 애플리케이션 클라이언트 아이디값";
	private String clientSecret;// 애플리케이션 클라이언트 시크릿값";

	ArrayList<TTSSpeaker> speakers;
	
	// voice info
	private String inputText;
	String speaker;
	int volume;
	int speed;
	int pitch;
	String musicFormat;

	private String fileName; // save file name

	public NaverClovaTTS() {
		clientId = "53edoaucxq";
		clientSecret = "";
		
		inputText = "만나서 반갑습니다.";
		speaker = "nara";
		volume = 0;
		speed = 0;
		pitch = 0;
		musicFormat = "mp3";

		fileName = "sample";
	}

	public NaverClovaTTS(String clientId, String clientSecret) {
		this();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	private void initSpeakers() {
		speakers = new ArrayList<TTSSpeaker>();
		
		// kor eng jpn(일본) chn(중국) twn(대만,타이완) esp(스페인,에스파냐)
		
		speakers.add(new TTSSpeaker("mijin", "미진", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("jinho", "진호", "kor", "male", "adult"));
		
		speakers.add(new TTSSpeaker("nara", "나라", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("nminsang", "민상", "kor", "male", "adult"));
		
		speakers.add(new TTSSpeaker("nhajun", "하준", "kor", "male", "child"));
		speakers.add(new TTSSpeaker("ndain", "다인", "kor", "female", "child"));
		
		speakers.add(new TTSSpeaker("njiyun", "지윤", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("nsujin", "수진", "kor", "female", "adult"));

		speakers.add(new TTSSpeaker("nsinu", "신우", "kor", "male", "adult"));
		speakers.add(new TTSSpeaker("njihun", "지훈", "kor", "male", "adult"));
		
		speakers.add(new TTSSpeaker("ngoeun", "고은", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("neunyoung", "은영", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("nsunkyung", "선경", "kor", "female", "adult"));
		
		speakers.add(new TTSSpeaker("nyujin", "유진", "kor", "female", "adult"));
	
		speakers.add(new TTSSpeaker("ntaejin", "태진", "kor", "male", "adult"));
		speakers.add(new TTSSpeaker("nyoungil", "영일", "kor", "male", "adult"));
		speakers.add(new TTSSpeaker("nseungpyo", "승표", "kor", "male", "adult"));
		speakers.add(new TTSSpeaker("nwontak", "원탁", "kor", "male", "adult"));
		
		speakers.add(new TTSSpeaker("dara_ang", "아라(화남)", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("nsunhee", "선희", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("nminseo", "민서", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("njiwwon", "지원", "kor", "female", "adult"));
		speakers.add(new TTSSpeaker("nbora", "보라", "kor", "female", "adult"));

		speakers.add(new TTSSpeaker("njonghyun", "종현", "kor", "male", "adult"));
		speakers.add(new TTSSpeaker("njoonyoung", "준영", "kor", "male", "adult"));
		speakers.add(new TTSSpeaker("njaewook", "재욱", "kor", "male", "adult"));
		
		///////////////////////////////////////////////////////
		
		speakers.add(new TTSSpeaker("clara", "클라라", "eng", "female", "adult"));
		speakers.add(new TTSSpeaker("matt", "매트", "eng", "male", "adult"));
		
		speakers.add(new TTSSpeaker("danna", "안나", "eng", "female", "adult"));
		speakers.add(new TTSSpeaker("djoey", "조이", "eng", "female", "child"));
		
		///////////////////////////////////////////////////////
		
		speakers.add(new TTSSpeaker("shinji", "신지", "jpn", "male", "adult"));

		speakers.add(new TTSSpeaker("ntomoko", "토모코", "jpn", "female", "adult"));
		speakers.add(new TTSSpeaker("nnaomi", "나오미", "jpn", "female", "adult"));
		speakers.add(new TTSSpeaker("nsayuri", "사유리", "jpn", "female", "adult"));
		
		speakers.add(new TTSSpeaker("dhajime", "하지메", "jpn", "male", "adult"));
		speakers.add(new TTSSpeaker("ddaiki", "다이키", "jpn", "male", "adult"));
		
		speakers.add(new TTSSpeaker("dayumu", "아유무", "jpn", "male", "child"));
		speakers.add(new TTSSpeaker("dmio", "미오", "jpn", "female", "child"));
		
		///////////////////////////////////////////////////////
		
		speakers.add(new TTSSpeaker("meimei", "메이메이", "chn", "female", "adult"));
		speakers.add(new TTSSpeaker("liangliang", "리앙리앙", "chn", "male", "adult"));
		
		///////////////////////////////////////////////////////
		
		speakers.add(new TTSSpeaker("jose", "호세", "esp", "male", "adult"));
		speakers.add(new TTSSpeaker("carmen", "카르멘", "esp", "female", "adult"));
		
		///////////////////////////////////////////////////////
		
		speakers.add(new TTSSpeaker("chiahua", "차화", "twn", "female", "adult"));
		speakers.add(new TTSSpeaker("kuanlin", "관린", "twn", "male", "adult"));
		
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public String getInputText() {
		return inputText;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void TTS() {
		try {
			String text = URLEncoder.encode(inputText, "UTF-8"); // 13자

			String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
			URL url = new URL(apiURL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
			// post request
//            String postParams = "speaker=nara&volume=0&speed=0&pitch=0&format=mp3&text=" + text;
			String postParams = String.format("speaker=%s&volume=%d&speed=%d&pitch=%d&format=%s&text=%s", speaker,
					volume, speed, pitch, musicFormat, text);
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				InputStream is = con.getInputStream();
				int read = 0;
				byte[] bytes = new byte[1024];

				// 랜덤한 이름으로 mp3 파일 생성
//                String tempname = Long.valueOf(new Date().getTime()).toString();
				String tempname = fileName + "_" + speaker;

				File f = new File(tempname + ".mp3");
				f.createNewFile();
				OutputStream outputStream = new FileOutputStream(f);
				while ((read = is.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				is.close();
				
			} else { // 오류 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				System.out.println(response.toString());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void TTS(String inputText) {
		this.inputText = inputText;
		TTS();
	}
	
	public void TTS(String inputText, String fileName) {
		this.fileName = fileName;
		TTS(inputText);
	}

}
