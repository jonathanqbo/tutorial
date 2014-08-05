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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.strategy.HystrixPlugins;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 11:34:18 AM Aug 5, 2014
 *
 */

public class NotifyTest {
	
	@BeforeClass
	public void registNotifer(){
		// here to register Notifier
		HystrixPlugins.getInstance().registerEventNotifier(new ShortCircuitEventNotifier());
	}

	/**
	 * more test thread pool size than command thread pool, to cause many rejected error
	 * Note: service rejected will not cause shortcircuit
	 */
	@Test(invocationCount=200, threadPoolSize=10)
	public void testStatusRejected(){
		CommandStatus commandRejectedTest = new CommandStatus(0.8f, 0.8f, 90);
		commandRejectedTest.execute();
		
		// Note: get metrics by instance
		HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory.asKey("CommandStatus"));
		long totalRequests = commandRejectedTest.getMetrics().getHealthCounts().getTotalRequests();
		long errorCount = commandRejectedTest.getMetrics().getHealthCounts().getErrorCount();
		int errorPercentage = commandRejectedTest.getMetrics().getHealthCounts().getErrorPercentage();
		System.out.println("testStatusRejected:   Total:" + totalRequests + "/Error:" + errorCount + "/Percentage:" + errorPercentage);
	}
	
	/**
	 * less test thread pool size, to avoid service rejected error and make shortcircuit error
	 */
	@Test(invocationCount=200, threadPoolSize=5)
	public void testStatusCircuited(){
		CommandStatus commandShortCircuitedTest = new CommandStatus(0.8f, 0.8f, 50);
		commandShortCircuitedTest.execute();
		// Note: get metrics by instance
		long totalRequests = commandShortCircuitedTest.getMetrics().getHealthCounts().getTotalRequests();
		long errorCount = commandShortCircuitedTest.getMetrics().getHealthCounts().getErrorCount();
		int errorPercentage = commandShortCircuitedTest.getMetrics().getHealthCounts().getErrorPercentage();
		System.out.println("testStatusCircuited : Total:" + totalRequests + "/Error:" + errorCount + "/Percentage:" + errorPercentage);
	}
	
}
