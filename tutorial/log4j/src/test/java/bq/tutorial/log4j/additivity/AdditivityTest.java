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

package bq.tutorial.log4j.additivity;

import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bq.tutorial.log4j.helloworld.HelloWorldTest;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 12:56:05 PM Aug 20, 2014
 *
 */

public class AdditivityTest {

	@BeforeClass
	public static void init(){
		URL url = AdditivityTest.class.getResource("log4j_additivity.xml");
		System.setProperty("log4j.configurationFile", url.toString());
	}
	
	@Test
	public void test(){
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("general info from normal logger");
		logger.debug("debug info from normal logger");
		logger.error("error info from normal logger");
	}
	
	@Test
	public void test1(){
		Logger logger = LoggerFactory.getLogger("UnAdditivityLogger");
		logger.info("general info from UnAdditivityLogger");
		logger.debug("debug info from UnAdditivityLogger");
		logger.error("error info from UnAdditivityLogger logger");
	}
	
	@Test
	public void test2(){
		Logger logger = LoggerFactory.getLogger("AdditivityLogger");
		logger.info("general info from AdditivityLogger");
		logger.debug("debug info from AdditivityLogger");
		logger.error("error info from AdditivityLogger");
	}
	
}
