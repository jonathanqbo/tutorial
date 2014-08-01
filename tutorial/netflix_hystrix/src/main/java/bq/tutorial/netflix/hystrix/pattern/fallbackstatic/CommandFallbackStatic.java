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

package bq.tutorial.netflix.hystrix.pattern.fallbackstatic;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * <b> Fallback Static </b>
 *
 * <p> return default value when error occur, often doesn't cause service to be removed </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 3:41:40 PM Jul 31, 2014
 *
 */
public class CommandFallbackStatic extends HystrixCommand<String>{

	private boolean isFail;
	
	public CommandFallbackStatic(boolean isFail){
		super(HystrixCommandGroupKey.Factory.asKey("GroupFacllbackStatic"));
		this.isFail = isFail;
	}
	
	@Override
	protected String run() throws Exception {
		if(isFail)
			throw new RuntimeException("Command Failed");
		
		return "Command Success";
	}

	@Override
	protected String getFallback() {
		return "default value";
	}

}
