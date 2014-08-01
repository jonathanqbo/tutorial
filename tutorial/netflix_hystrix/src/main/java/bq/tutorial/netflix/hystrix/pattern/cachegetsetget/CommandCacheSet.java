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

/**
 * <b> Cache Invalidation </b>
 *
 * <p> invalidate other cache from this class, to make CommandCacheGet re-retrieve value again </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 5:02:28 PM Jul 31, 2014
 *
 */
public class CommandCacheSet extends HystrixCommand<Void>{

	private String key;
	
	public CommandCacheSet(String key) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupCacheInvalidation"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("CommandCacheSet")));
		this.key = key;
	}
	
	@Override
	protected Void run() throws Exception {
		// invalidate cache of key
		CommandCacheGet.flushCache(key);
		System.out.println("cache of key:" + key + " has been invalided");
		return null;
	}

}
