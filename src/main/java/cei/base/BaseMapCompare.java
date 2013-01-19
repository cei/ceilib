package cei.base;

import java.util.Comparator;

import cei.base.BaseMap;

public class BaseMapCompare implements Comparator<BaseMap> {

	private String name;
	private boolean asc = true;

	public BaseMapCompare(String name) {
		this.name = name;
	}

	public int compare(BaseMap o1, BaseMap o2) {
		if(asc) {
			return o1.getString(name).compareTo(o2.getString(name));
		} else {
			return o2.getString(name).compareTo(o1.getString(name));
		}
	}
	
	public BaseMapCompare(String name, boolean asc) {
		this.name = name;
		this.asc = asc;
	}
}
