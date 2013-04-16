package cei.code.domains;

public class Code {
	private String group;
	private String code;
	private String parent;
	private String value;
	private int level;
	private int order;
	private boolean use = true;
	private String description;
	private String annex;
	
	public String toString() {
		StringBuffer sb = new StringBuffer( "--- Code ---" );

		sb.append( "\ngroup\tcode\tparent\n" )
		.append( group + "\t" )
		.append( code + "\t" )
		.append( parent + "\t" );

		return sb.toString();
	}

	public String getGroup() {
		return group;
	}
	public void setGroup( String group ) {
		this.group = group;
	}
	public String getCode() {
		return code;
	}
	public void setCode( String code ) {
		this.code = code;
	}
	public String getParent() {
		return parent;
	}
	public void setParent( String parent ) {
		this.parent = parent;
	}
	public String getValue() {
		return value;
	}
	public void setValue( String value ) {
		this.value = value;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel( int level ) {
		this.level = level;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder( int order ) {
		this.order = order;
	}
	public boolean isUse() {
		return use;
	}
	public void setUse( boolean use ) {
		this.use = use;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription( String description ) {
		this.description = description;
	}
	public String getAnnex() {
		return annex;
	}
	public void setAnnex( String annex ) {
		this.annex = annex;
	}
}