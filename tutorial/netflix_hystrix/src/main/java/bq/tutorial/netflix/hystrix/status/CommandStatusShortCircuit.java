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

package bq.tutorial.netflix.hystrix.status;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 9:52:20 AM Aug 4, 2014
 *
 */

public class CommandStatusShortCircuit extends HystrixCommand<String>{

	private float failPercent;
	
	private float timeoutPercent;
	
	public CommandStatusShortCircuit(float failPercent, float timeoutPercent) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupStatusTest"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withCircuitBreakerEnabled(true)
						.withCircuitBreakerErrorThresholdPercentage(95)
						.withCircuitBreakerSleepWindowInMilliseconds(5000)
						.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
						.withExecutionIsolationThreadTimeoutInMilliseconds(500))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
						.withCoreSize(5)
						.withQueueSizeRejectionThreshold(20))
			);
		
		this.failPercent = failPercent;
		this.timeoutPercent = timeoutPercent;
	}
	
	@Override
	protected String run() throws Exception {
		/* simulate performing network call to retrieve user information */
        try {
            Thread.sleep((int) (Math.random() * 10) + 2);
        } catch (InterruptedException e) {
            // do nothing
        }

        /* fail 5% of the time to show how fallback works */
        if (Math.random() > failPercent) {
            throw new RuntimeException("service failed");
        }

        /* latency spike 5% of the time so timeouts can be triggered occasionally */
        if (Math.random() > timeoutPercent) {
            // random latency spike
            try {
                Thread.sleep((int) (Math.random() * 10) + 500);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
        
		return "success_value";
	}

	@Override
	protected String getFallback() {
		if(isResponseRejected())
			return "rejected_value";
		
		if(isResponseTimedOut())
			return "timeout_value";
		
		if(isResponseShortCircuited())
			return "shortcircuited_value";
		
		return "failed_value";
	}

}