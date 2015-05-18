package cn.com.dhcc.rp.st.def;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.xml.sax.SAXException;

public class LoadDef {
	private static StUpsDef stups = null;
	static{
		DigesterLoader  digesterLoader = DigesterLoader.newLoader(new FromXmlRulesModule() {
            @Override
            protected void loadRules() {
        		InputStream input = LoadDef.class.getClassLoader().getResourceAsStream("cn/com/dhcc/rp/st/def/st-ups-def-rules.xml");
                this.loadXMLRules(input);
            }
        });
		
		Digester digester = digesterLoader.newDigester();
		InputStream input = LoadDef.class.getClassLoader().getResourceAsStream("cn/com/dhcc/rp/st/def/st-ups-def.xml");
		try {
			stups = digester.parse(input);
			//System.out.println(stups);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	public static StUpsDef getStups() {
		return stups;
	}
}
