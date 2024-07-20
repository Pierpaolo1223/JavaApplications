package com.example.demo.command;

public class UserCommand {
	String password;
	String username;
	String surname;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public UserCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserCommand(String password, String username, String surname) {
		super();
		this.password = password;
		this.username = username;
		this.surname = surname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "UserCommand [password=" + password + ", username=" + username + ", surname=" + surname + "]";
	}

}
