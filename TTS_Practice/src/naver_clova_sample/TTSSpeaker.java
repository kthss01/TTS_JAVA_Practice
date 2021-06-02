package naver_clova_sample;

public class TTSSpeaker {

	private String name; // 이름
	private String korName; // 한글 이름
	private String nation; // 국가
	private String gender; // 성별
	private String age;

	public TTSSpeaker() {
	}

	public TTSSpeaker(String name, String korName, String nation, String gender, String age) {
		super();
		this.name = name;
		this.korName = korName;
		this.nation = nation;
		this.gender = gender;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKorName() {
		return korName;
	}
	
	public void setKorName(String korName) {
		this.korName = korName;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
