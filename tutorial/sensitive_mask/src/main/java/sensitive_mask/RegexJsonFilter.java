/*
Copyright (c) 2014 (Jonathan Q. Bo) 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package sensitive_mask;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 10:52:40 AM Aug 23, 2014
 *
 */

public class RegexJsonFilter {
	
	private List<Pattern> patterns = new ArrayList<Pattern>();

	public RegexJsonFilter(String... fields) {
		for (String field : fields) {
			String regex = "(\"" + field + "\")\\s*:\\s*(\"[^\"]*\")";
			Pattern pattern = Pattern.compile(regex);
			patterns.add(pattern);
		}
	}
	
	public String filter(String json){
		String afterFilter = json;
		for(Pattern pattern : patterns){
			Matcher matcher = pattern.matcher(afterFilter);
			afterFilter = matcher.replaceAll("$1:\"*******\"");
		}
		
		return afterFilter;
	}
	
}
