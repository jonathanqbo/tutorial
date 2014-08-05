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

package bq.tutorial.netflix.hystrix.notify;

import java.util.concurrent.atomic.AtomicInteger;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * <b>  </b>
 *
 * <p>  </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 4:17:53 PM Aug 1, 2014
 *
 */

public class CommandStatus extends HystrixCommand<String>{

	private float failPercent;
	
	private float timeoutPercent;
	
	private static AtomicInteger rejectedTimes = new AtomicInteger();
	private static AtomicInteger shortCircuitedTimes = new AtomicInteger();
	private static AtomicInteger timeoutTimes = new AtomicInteger();
	private static AtomicInteger fallbackTimes = new AtomicInteger();
	private static AtomicInteger successTimes = new AtomicInteger();
	
	public CommandStatus(float failPercent, float timeoutPercent, int percentToBreakCircuit) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupStatusTest"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("CommandStatus"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withCircuitBreakerEnabled(true)
						.withCircuitBreakerErrorThresholdPercentage(percentToBreakCircuit)
						// the time from circuit break to circuit close, here make it shorter to avoid so many shortcircuit error
						.withCircuitBreakerSleepWindowInMilliseconds(10)
						.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
						.withExecutionIsolationThreadTimeoutInMilliseconds(500)
						// set metrics static data refresh interval (decide how long to statistic)
						.withMetricsRollingStatisticalWindowInMilliseconds(60000)
						// set metrics health snapshot refresh interval
						.withMetricsHealthSnapshotIntervalInMilliseconds(10)
						)
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPoolStatus"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
						.withCoreSize(5)
						.withQueueSizeRejectionThreshold(5))
			);
		
		this.failPercent = failPercent;
		this.timeoutPercent = timeoutPercent;
	}
	
	@Override
	protected String run() throws Exception {
		/* simulate performing network call to retrieve user information */
        try {
            Thread.sleep((int) (Math.random() * 300) + 2);
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
        
        successTimes.incrementAndGet();
		return "success_value";
	}

	@Override
	protected String getFallback() {
		if(isResponseRejected()){
			rejectedTimes.incrementAndGet();
			return "rejected_value";
		}
		
		if(isResponseTimedOut()){
			timeoutTimes.incrementAndGet();
			return "timeout_value";
		}
		
		if(isResponseShortCircuited()){
			shortCircuitedTimes.incrementAndGet();
			return "shortcircuited_value";
		}
		
		fallbackTimes.incrementAndGet();
		return "failed_value";
	}

	public static AtomicInteger getRejectedTimes() {
		return rejectedTimes;
	}

	public static AtomicInteger getShortCircuitedTimes() {
		return shortCircuitedTimes;
	}

	public static AtomicInteger getTimeoutTimes() {
		return timeoutTimes;
	}

	public static AtomicInteger getSuccessTimes() {
		return successTimes;
	}

	public static AtomicInteger getFallbackTimes() {
		return fallbackTimes;
	}

}
