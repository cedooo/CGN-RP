package cn.com.dhcc.rp.persistence.po;

public class SqlAdapter {
	private String sql = null;
	public SqlAdapter(String sql){
		this.sql = sql;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	@Override
	public String toString() {
		return "[sql=" + sql + "]";
	}
	
}
