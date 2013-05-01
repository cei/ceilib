package cei.base;

import java.util.Comparator;

public class BaseMapCompare<T> implements Comparator<BaseMap<T>> {

	private String name;
	private boolean asc = true;

	public BaseMapCompare(String name) {
		this.name = name;
	}

	public int compare(BaseMap<T> o1, BaseMap<T> o2) {
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
