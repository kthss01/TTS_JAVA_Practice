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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class NaverClovaTTS {

	private String clientId;// 애플리케이션 클라이언트 아이디값";
	private String clientSecret;// 애플리케이션 클라이언트 시크릿값";

	Map<String, TTSSpeaker> speakers;

	// voice info
	private String inputText;
	String speaker;
	int volume;
	int speed;
	int pitch;
	String musicFormat;

	private String fileName; // save file name

	private ArrayList<File> fileList;
	
	public NaverClovaTTS() {
		initSpeakers();

		clientId = "53edoaucxq";
		clientSecret = null;

		inputClientSecretByConsole();
		
		inputText = "만나서 반갑습니다.";
		speaker = speakers.get("나라").getName();
		volume = 0;
		speed = 0;
		pitch = 0;
		musicFormat = "mp3";

		fileName = "sample";
		
		fileList = new ArrayList<File>();
	}

	public NaverClovaTTS(String clientId, String clientSecret) {
		this();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	private void initSpeakers() {
		speakers = new LinkedHashMap<>();

		// kor eng jpn(일본) chn(중국) twn(대만,타이완) esp(스페인,에스파냐)

		speakers.put("미진", new TTSSpeaker("mijin", "미진", "kor", "female", "adult"));
		speakers.put("진호", new TTSSpeaker("jinho", "진호", "kor", "male", "adult"));

		speakers.put("나라", new TTSSpeaker("nara", "나라", "kor", "female", "adult"));
		speakers.put("민상", new TTSSpeaker("nminsang", "민상", "kor", "male", "adult"));

		speakers.put("하준", new TTSSpeaker("nhajun", "하준", "kor", "male", "child"));
		speakers.put("다인", new TTSSpeaker("ndain", "다인", "kor", "female", "child"));

		speakers.put("지윤", new TTSSpeaker("njiyun", "지윤", "kor", "female", "adult"));
		speakers.put("수진", new TTSSpeaker("nsujin", "수진", "kor", "female", "adult"));

		speakers.put("신우", new TTSSpeaker("nsinu", "신우", "kor", "male", "adult"));
		speakers.put("지훈", new TTSSpeaker("njihun", "지훈", "kor", "male", "adult"));

		speakers.put("고은", new TTSSpeaker("ngoeun", "고은", "kor", "female", "adult"));
		speakers.put("은영", new TTSSpeaker("neunyoung", "은영", "kor", "female", "adult"));
		speakers.put("선경", new TTSSpeaker("nsunkyung", "선경", "kor", "female", "adult"));

		speakers.put("유진", new TTSSpeaker("nyujin", "유진", "kor", "female", "adult"));

		speakers.put("태진", new TTSSpeaker("ntaejin", "태진", "kor", "male", "adult"));
		speakers.put("영일", new TTSSpeaker("nyoungil", "영일", "kor", "male", "adult"));
		speakers.put("승표", new TTSSpeaker("nseungpyo", "승표", "kor", "male", "adult"));
		speakers.put("원탁", new TTSSpeaker("nwontak", "원탁", "kor", "male", "adult"));

		speakers.put("아라(화남)", new TTSSpeaker("dara_ang", "아라(화남)", "kor", "female", "adult"));
		speakers.put("선희", new TTSSpeaker("nsunhee", "선희", "kor", "female", "adult"));
		speakers.put("민서", new TTSSpeaker("nminseo", "민서", "kor", "female", "adult"));
		speakers.put("지원", new TTSSpeaker("njiwon", "지원", "kor", "female", "adult"));
		speakers.put("보라", new TTSSpeaker("nbora", "보라", "kor", "female", "adult"));

		speakers.put("종현", new TTSSpeaker("njonghyun", "종현", "kor", "male", "adult"));
		speakers.put("준영", new TTSSpeaker("njoonyoung", "준영", "kor", "male", "adult"));
		speakers.put("재욱", new TTSSpeaker("njaewook", "재욱", "kor", "male", "adult"));

		///////////////////////////////////////////////////////

		speakers.put("클라라", new TTSSpeaker("clara", "클라라", "eng", "female", "adult"));
		speakers.put("매트", new TTSSpeaker("matt", "매트", "eng", "male", "adult"));

		speakers.put("안나", new TTSSpeaker("danna", "안나", "eng", "female", "adult"));
		speakers.put("조이", new TTSSpeaker("djoey", "조이", "eng", "female", "child"));

		///////////////////////////////////////////////////////

		speakers.put("신지", new TTSSpeaker("shinji", "신지", "jpn", "male", "adult"));

		speakers.put("토모코", new TTSSpeaker("ntomoko", "토모코", "jpn", "female", "adult"));
		speakers.put("나오미", new TTSSpeaker("nnaomi", "나오미", "jpn", "female", "adult"));
		speakers.put("사유리", new TTSSpeaker("nsayuri", "사유리", "jpn", "female", "adult"));

		speakers.put("하지메", new TTSSpeaker("dhajime", "하지메", "jpn", "male", "adult"));
		speakers.put("다이키", new TTSSpeaker("ddaiki", "다이키", "jpn", "male", "adult"));

		speakers.put("아유무", new TTSSpeaker("dayumu", "아유무", "jpn", "male", "child"));
		speakers.put("미오", new TTSSpeaker("dmio", "미오", "jpn", "female", "child"));

		///////////////////////////////////////////////////////

		speakers.put("메이메이", new TTSSpeaker("meimei", "메이메이", "chn", "female", "adult"));
		speakers.put("리앙리앙", new TTSSpeaker("liangliang", "리앙리앙", "chn", "male", "adult"));

		///////////////////////////////////////////////////////

		speakers.put("호세", new TTSSpeaker("jose", "호세", "esp", "male", "adult"));
		speakers.put("카르멘", new TTSSpeaker("carmen", "카르멘", "esp", "female", "adult"));

		///////////////////////////////////////////////////////

		speakers.put("차화", new TTSSpeaker("chiahua", "차화", "twn", "female", "adult"));
		speakers.put("관린", new TTSSpeaker("kuanlin", "관린", "twn", "male", "adult"));

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

	public Map<String, TTSSpeaker> getSpeakers() {
		return speakers;
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
				System.out.println("response is 200");

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

				fileList.add(f);
				
				System.out.println(tempname + " is write");

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

	private void inputClientSecretByConsole() {
		if (clientSecret != null)
			return;

		Scanner sc = new Scanner(System.in);

		System.out.print("Client Secret : ");

		clientSecret = sc.nextLine();
		
		return;
	}

	public void TTS(String inputText) {
		this.inputText = inputText;
		TTS();
	}

	public void TTS(String inputText, String fileName) {
		this.fileName = fileName;
		TTS(inputText);
	}

	public void TTS(String inputText, String fileName, String speakerKorName) {
		this.speaker = speakers.get(speakerKorName).getName();
		TTS(inputText, fileName);
	}

	public void removeFiles() {
		for (File file : fileList) {
			if (file.exists()) {
				if(file.delete()) {
					System.out.println(file.getName() + " delete");
				} else {
					System.out.println(file.getName() + " delete failed");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		NaverClovaTTS ncTTS = new NaverClovaTTS();
//		ncTTS.TTS();

		Map<String, TTSSpeaker> speakers = ncTTS.getSpeakers();

		for (String key : speakers.keySet()) {
			String inputText = "이 음성은 Naver Clova Voice를 이용하여 김태훈이 준비한 샘플 목소리입니다.";
			String fileName = "./resources/voiceSample/sample_test";
			ncTTS.TTS(inputText, fileName, speakers.get(key).getKorName());
		}
	}
	

}
