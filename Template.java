/**
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nitin
 *
 */
public class Template {

	private String template;
	private Map<String, String> variables; 
	
	private static Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]*\\}");
	
	public Template() {		
		variables = new HashMap<String, String>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Template t = new Template();
		t.setTemplate("${${name}} has an appointment on ${day}");
		t.addVariable("name", "Nitin");
		t.addVariable("day", "Sunday");
		t.addVariable("Nitin", "Nitin Maheshwari");
		t.getUserTemplate();
		
		//t.setVariables(variables);
		System.out.println("Result: " + t.evaluate());
	}


	/**
	 * 
	 */
	private String evaluate() {
		String output = template;
		while (true) {
			Matcher m = pattern.matcher(template);
			System.out.println(m.groupCount());	
			
	        int count = 0;
			while (m.find()) {
				count++;
		        String var = template.substring(m.start()+2, m.end()-1);
		        String replacement;
				try {
					replacement = getVariable(var);
				} catch (Exception e) {
					System.err.println(e.getMessage());
					return output;
				}
		        String regex = "\\$\\{"+var+"\\}";
		        output = output.replaceAll(regex, replacement);
			}
			template = output;
			if (count == 0)
				break;
		}
		
	     return output;
	}
	
	private void getUserTemplate() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Do you want to use the default input (Y/N) :- ");
        try {
			String s = br.readLine();
			if (s.equalsIgnoreCase("Y"))
				return;
		} catch (IOException e) {
            System.err.println("Some exception, will procede with user input!");
		}
        System.out.println("\nEnter new template:");
        try {
			template = br.readLine();
		} catch (IOException e) {
            System.err.println("Some exception, will procede with default inputs!");
            return;
		}
        
        String key = "";
        String value = "";
        System.out.println("\nEnter key and value. To stop entering variables enter -1 as key.");
        while (true) {
			try {
	            System.out.print("\nEnter key:");
				key = br.readLine();
				if (key.equals("-1"))
					return;
	            System.out.print("\nEnter Value:");
				value = br.readLine();
				addVariable(key, value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
	}


	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}


	/**
	 * @return the variables
	 * @throws Exception 
	 */
	public String getVariable(String key) throws Exception {
		String value = "";
		if (variables.containsKey(key) )
			value = variables.get(key);
		else
			throw new Exception("Key not found");
		return value;
	}


	/**
	 * @param variables the variables to set
	 */
	public void addVariable(String key, String value) {
		variables.put(key, value);
	}

}
