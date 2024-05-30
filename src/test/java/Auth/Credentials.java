package Auth;

import java.util.Random;

public class Credentials {

	private String username;
	private String pan;
	private String email;
	private String phone;
	private String gst;
	private String gasSize;
	private String gasName;
	private String gasSymbol;
    private String updatedGodown;
	public String getUpdatedGodown() {
		return updatedGodown;
	}

	public void setUpdatedGodown(String updatedGodown) {
		this.updatedGodown = updatedGodown;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getGasSize() {
		return gasSize;
	}

	public void setGasSize(String gasSize) {
		this.gasSize = gasSize;
	}

	public String getGasName() {
		return gasName;
	}

	public void setGasName(String gasName) {
		this.gasName = gasName;
	}

	public String getGasSymbol() {
		return gasSymbol;
	}

	public void setGasSymbol(String gasSymbol) {
		this.gasSymbol = gasSymbol;
	}

	public Credentials() {
		Random random = new Random();

		setUsername(generateRandomString(5));
		setPan(generateRandomString(5).toUpperCase() + String.format("%04d", random.nextInt(10000)) + "D");
		setEmail(getUsername() + "@example.com");
		setPhone(String.format("%010d", Math.abs(random.nextLong() % 10000000000L)));
		setGst("22" + generateRandomString(5).toUpperCase() + String.format("%04d", random.nextInt(10000)) + "A1Z1");
		setGasSize(random.nextInt(100) + "L");
		setGasName("Gas_" + generateRandomString(2));
		setUpdatedGodown("Godown" +generateRandomString(2));
		setGasSymbol(generateRandomString(2).toUpperCase() + generateRandomString(2).toLowerCase());
	}

	// Method to generate random string of specified length
	private String generateRandomString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char ch = (char) ('A' + random.nextInt(26));
			sb.append(ch);
		}
		return sb.toString();
	}

}
