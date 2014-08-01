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

package bq.tutorial.netflix.hystrix.pattern.fallbackstub;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * <b> Fallback Stub </b>
 * 
 * <p> used for return compound object with multiple attributes when error occur</p>
 * 
 * <p> some fields come from default value or stubbed value, 
 * it is recommended to inject stubbed value when instantiate the command. </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 3:47:54 PM Jul 31, 2014
 *
 */
public class CommandFallbackStub extends HystrixCommand<User>{

	private String id;
	
	public CommandFallbackStub(String id) {
		super(HystrixCommandGroupKey.Factory.asKey("GroupFallbackStub"));
		this.id = id;
	}

	@Override
	protected User run() throws Exception {
		throw new RuntimeException("Command Failed");
	}

	@Override
	protected User getFallback() {
		/**
         * Return stubbed fallback with some static defaults, placeholders,
         * and an injected value 'id' that we'll use
         * instead of what we would have retrieved from the remote service.
         */
		return new User(id, null, true, true, true);
	}
	
}
