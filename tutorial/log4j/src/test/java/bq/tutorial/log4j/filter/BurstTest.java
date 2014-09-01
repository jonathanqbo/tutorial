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

package bq.tutorial.log4j.filter;

import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 4:33:59 PM Aug 31, 2014
 *
 */

public class BurstTest {
	
	@BeforeClass
	public static void init(){
		URL url = BurstTest.class.getResource("log4j_burst.xml");
		System.setProperty("log4j.configurationFile", url.toString());
	}
	
	@Test
	public void test(){
		Logger logger = LoggerFactory.getLogger("brustfilter");
		
		for(int i = 0; i < 5000; i++){
			logger.info("message" + i);
			try {
				Thread.currentThread().sleep((long) (Math.random()*10));
			} catch (InterruptedException e) {
			}
		}
	}
	
	@Test
	public void test2(){
		Logger logger = LoggerFactory.getLogger("timefilter");
		
		for(int i = 0; i < 5000; i++){
			logger.info("message" + i);
			try {
				Thread.currentThread().sleep((long) (Math.random()*10));
			} catch (InterruptedException e) {
			}
		}
	}
	
}
