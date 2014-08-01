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

package bq.tutorial.netflix.hystrix.basic;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 4:28:26 PM Jul 30, 2014
 *
 */

public class CommandHelloWorldThreadPool extends HystrixCommand<String>{

	private String name;
	
	public CommandHelloWorldThreadPool(String name){
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleCommandHelloWorldThreadPool"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("CommandHelloWorldThreadPool"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CommandHelloWorldThreadPool")));
		
		this.name = name;
	}
	
	@Override
	protected String run() throws Exception {
		return "Hello " + name;
	}

	
	
}
