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

package bq.tutorial.netflix.hystrix.pattern.dualservice;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * <b> Primary Secondary </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 4:40:10 PM Jul 31, 2014
 *
 */

public class CommandFacade extends HystrixCommand<String>{

	private final static DynamicBooleanProperty usePrimary = DynamicPropertyFactory.getInstance().getBooleanProperty("primarySecondary.usePrimary", true);
	
	public CommandFacade() {
		// The facade HystrixCommand can use semaphore-isolation since all of
		// the work it is doing is going through 2 other HystrixCommands that
		// are already thread-isolated. It is unnecessary to have yet another
		// layer of threading as long as the run() method of the facade is not
		// doing any other network calls, retry logic or other "error prone"
		// things.
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupPrimarySecondary"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("CommandPrimarySecondary"))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter()
						.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
				));
	}
		
	@Override
	protected String run() throws Exception {
		// If both primary and secondary fail then the facade command would have a fallback.
		if(usePrimary.get())
			return new CommandPrimary().execute();
		else
			return new CommandSecondary().execute();
	}

}
