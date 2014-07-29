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

package bq.tutorial.netflix.hystrix;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

import rx.Observable;
import rx.functions.Action;
import rx.functions.Action1;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 3:20:25 PM Jul 29, 2014
 *
 */

public class HelloWorldTest {

	/**
	 * Synchronize execute command
	 */
	@Test
	public void testSynchronize(){
		String value = new CommandHelloWorld("Jonathan").execute();
		assertEquals("Hello Jonathan", value);
	}
	
	/**
	 * Asynchronous execute command
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testAsynchronize() throws InterruptedException, ExecutionException{
		Future<String> futureValue = new CommandHelloWorld("Jonathan").queue();
		assertEquals("Hello Jonathan", futureValue.get());
	}
	
	/**
	 * Observe execute command
	 */
	@Test
	public void testObserve(){
		Observable<String> observe = new CommandHelloWorld("Jonathan").observe();
		
		// non-blocking
		observe.subscribe(new Action1<String>() {

			@Override
			public void call(String arg0) {
				assertEquals("Hello Jonathan", arg0);
			}
			
		});
		
		// blocking
		String value = observe.toBlockingObservable().single();
		assertEquals("Hello Jonathan", value);
	}
	
}
