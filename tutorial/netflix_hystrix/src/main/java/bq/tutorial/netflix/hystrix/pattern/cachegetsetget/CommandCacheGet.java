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

package bq.tutorial.netflix.hystrix.pattern.cachegetsetget;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

/**
 * <b> Cache get </b>
 *
 * <p> retrieve remote service to get value and cache it </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 5:01:59 PM Jul 31, 2014
 *
 */

public class CommandCacheGet extends HystrixCommand<String>{
	
	// indicator simply represent the service invocation times
	private static int count = 0;

	private String key;
	
	public CommandCacheGet(String key) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupCacheInvalidation"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("CommandCacheGet")));
		
		this.key = key;
	}
	
	@Override
	protected String run() throws Exception {
		return "value of " + key + ",  the " + (++count) + " times retrieved from service";
	}

	@Override
	protected String getCacheKey() {
		return key;
	}
	
	/**
	 * cache can be managed by static way
	 * @param key
	 */
	public static void flushCache(String key){
		HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("CommandCacheGet"), HystrixConcurrencyStrategyDefault.getInstance())
		.clear(key);;
	}

}
