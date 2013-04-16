package cei.code;

import java.util.List;

import cei.code.domains.Code;

public interface ICode {
	public static final String COMMON_CODE = "COMMON_CODE";

	void save(Code code);
	int garbage(Code code);
	int recycle(Code code);
	int remove(Code code);
	int garbage(List<Code> codes);
	int recycle(List<Code> codes);
	int remove(List<Code> codes);
	
	Code getCode(String code);
	Code getCode(String code, boolean use);
	Code getCode(String group, String code);
	Code getCode(String group, String code, boolean use);

	List<Code> getCodes(String code);
	List<Code> getCodes(String code, boolean use);
	List<Code> getCodes(String group, String code);
	List<Code> getCodes(String group, String code, boolean use);
}
