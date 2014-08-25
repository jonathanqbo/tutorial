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

package sensitive_mask;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 9:23:50 PM Aug 24, 2014
 *
 */

public class JsonPathJunitTest {

	@Test
	public void test1() throws JsonProcessingException, IOException{
		String json="{"+
				"\"payload\" :{ "+
					"\"order\" :{ "+
			            "\"customerName\" : {\"firstName\":\"Neha\", \"lastName\":\"Ga\"}, "+
						"\"email\" : \"sarnab123@gmail.com\","+
						"\"cartItems\" : [{\"skuCode\":\"91100249\", \"qty\":\"1\"}],"+
						"\"billAddress\" :{\"firstName\":\"Sanrba\",\"lastName\":\"Saba\",\"addr1\":\"1854 northwest circle\", \"city\":\"San Jose\",\"state\":\"CA\",\"postalCode\":\"95131\", \"phoneNumber\":\"2628392748\"},"+
						"\"isBillAddressEqualtoShipAddress\":\"true\","+
						"\"shippingMethod\":\"USNDY\","+
						"\"paymentTypes\" :{"+
										"\"creditCards\" : [{\"nameOnCard\":\"John Smith\", \"cardNum\":\"4445222299990007\",\"type\":\"Visa\",\"expDate\":\"12/2014\",\"securityCode\":\"412\"}]"+
									"}"+
								"}"+
							"}"+
						"}";
		
		long from = System.currentTimeMillis();
		JsonPathFilter filter = new JsonPathFilter("email");
		for(int i = 0; i < 10000; i++){
			String afterFilter = filter.filter(json);
			System.out.println(afterFilter);
		}
		System.out.println("time cost : " + (System.currentTimeMillis()-from));
	}
	
}
