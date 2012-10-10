package cei.support.spring.message;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class MessageSupport extends ReloadableResourceBundleMessageSource {
	private Locale locale = Locale.KOREA;
	private String inCharset;
	private String outCharset;
	
	public MessageSupport() {
		super();
	}

	public void setInCharset(String charset) {
		this.inCharset = charset;
	}

	public void setOutCharset(String charset) {
		this.outCharset = charset;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public String get(String keyValue, Object ... args)
	{
		String message = super.getMessage(keyValue, args, locale);
		
		if(inCharset != null && outCharset != null) {
			try {
				message = new String(message.getBytes(inCharset), outCharset);
			}
			catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		else if(inCharset != null) {
			try {
				message = new String(message.getBytes(inCharset));
			}
			catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		else if(outCharset != null) {
			try {
				message = new String(message.getBytes(), outCharset);
			}
			catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return message;
	}
}