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

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 4:42:53 PM Aug 1, 2014
 *
 */

public class StatusPressureTest {
	
	/**
	 * more test thread pool size than command thread pool, to cause many rejected error
	 * Note: service rejected will not cause shortcircuit
	 */
	@Test(invocationCount=2000, threadPoolSize=10)
	public void testStatusRejected(){
		CommandStatus command = new CommandStatus(0.8f, 0.8f, 90, 1);
		System.out.println(command.execute());
	}
	
	@AfterClass
	public void satistic(){
		// You will find the total times is more than 2000
		// that is because the command will keep run when timeout, but hystrix will force fallback, so timeout times will be calculated twice
		System.out.println("success:" + CommandStatus.getSuccessTimes() + "; fallback:" + CommandStatus.getFallbackTimes() + "; rejected: " + CommandStatus.getRejectedTimes() + "; timeout" + CommandStatus.getTimeoutTimes() + "; shortcircuit:" + CommandStatus.getShortCircuitedTimes());
	}
	
	/**
	 * less test thread pool size, to avoid service rejected error and make shortcircuit error
	 */
	@Test(invocationCount=2000, threadPoolSize=5)
	public void testStatusCircuited(){
		System.out.println(new CommandStatus(0.8f, 0.8f, 50, 1).execute());
	}
	
}
