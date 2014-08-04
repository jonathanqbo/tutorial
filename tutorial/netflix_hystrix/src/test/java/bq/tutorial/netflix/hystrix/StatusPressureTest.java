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

import org.springframework.web.client.RestTemplate;
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
	 */
	@Test(invocationCount=2000, threadPoolSize=20)
	public void testStatusRejected(){
		RestTemplate client = new RestTemplate();
		String result = client.getForObject("http://localhost:8080/netflix_hystrix/mvc/pressure", String.class);
		System.out.println(result);
	}
	
	/**
	 * less test thread pool size, to avoid service rejected error and make shortcircuit error
	 */
	@Test(invocationCount=2000, threadPoolSize=5)
	public void testStatusCircuited(){
		RestTemplate client = new RestTemplate();
		String result = client.getForObject("http://localhost:8080/netflix_hystrix/mvc/pressure", String.class);
		System.out.println(result);
	}
	
}
