package jdo;

import java.util.Date;

import javax.mail.Address;

public class Mail {

	private Date date;
	private Address from;
	private String Subject;
	private String Content;
	private String Message;

	public Mail() {

	}

	public Mail(Date date, Address from, String subject, String content, String message) {
		super();
		this.date = date;
		this.from = from;
		Subject = subject;
		Content = content;
		Message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date2) {
		this.date = date2;
	}

	public Address getFrom() {
		return from;
	}

	public void setFrom(Address from2) {
		this.from = from2;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
