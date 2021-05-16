import static org.junit.Assert.*;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

@RunWith(value=Parameterized.class)
public class MyJCSRemovalSimpleConcurrentTest {
	
	private int count;
	private String expected;
	private static JCS jcs;
	
	
	public MyJCSRemovalSimpleConcurrentTest(int count, String expected) {
		this.count=count;
		this.expected=expected;
	}
	
	@Before
	public void setUp() { 
		configure();
	}
	
	
	private void configure(){
		
		JCS.setConfigFilename( "/TestRemoval.ccf" );
        try {
        	jcs= JCS.getInstance( "testCache1" );
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{500, null},
		});
	}
	

	@Test
	 public void testTwoDeepRemoval()
		        throws Exception
		    {

		        System.out.println( "------------------------------------------" );
		        System.out.println( "testTwoDeepRemoval" );

		        int count = 500;
		        JCS jcs = JCS.getInstance( "testCache1" );

		        for ( int i = 0; i <= count; i++ )
		        {
		            jcs.put( "key:" + i + ":anotherpart", "data" + i );
		        }

		        for ( int i = count; i >= 0; i-- )
		        {
		            String res = (String) jcs.get( "key:" + i + ":anotherpart" );
		            if ( res == null )
		            {
		                assertNotNull( "[key:" + i + ":anotherpart] should not be null, " + jcs.getStats(), res );
		            }
		        }
		        System.out.println( "Confirmed that " + count + " items could be found" );

		        for ( int i = 0; i <= count; i++ )
		        {
		            jcs.remove( "key:" + i + ":" );
		            assertNull( jcs.getStats(), jcs.get( "key:" + i + ":anotherpart" ) );
		        }
		        System.out.println( "Confirmed that " + count + " items were removed" );

		        System.out.println( jcs.getStats() );

		    }

		    /**
		     * Verify that 1 level deep hierchical removal works.
		     *
		     * @throws Exception
		     */
		    public void testSingleDepthRemoval()
		        throws Exception
		    {

		        System.out.println( "------------------------------------------" );
		        System.out.println( "testSingleDepthRemoval" );

		        int count = 500;
		        JCS jcs = JCS.getInstance( "testCache1" );

		        for ( int i = 0; i <= count; i++ )
		        {
		            jcs.put( i + ":key", "data" + i );
		        }

		        for ( int i = count; i >= 0; i-- )
		        {
		            String res = (String) jcs.get( i + ":key" );
		            if ( res == null )
		            {
		                assertNotNull( "[" + i + ":key] should not be null", res );
		            }
		        }
		        System.out.println( "Confirmed that " + count + " items could be found" );

		        for ( int i = 0; i <= count; i++ )
		        {
		            jcs.remove( i + ":" );
		            assertNull( jcs.get( i + ":key" ) );
		        }
		        System.out.println( "Confirmed that " + count + " items were removed" );

		        System.out.println( jcs.getStats() );

		    }

		    /**
		     * Verify that clear removes everyting as it should.
		     *
		     * @throws Exception
		     */
		    public void testClear()
		        throws Exception
		    {

		        System.out.println( "------------------------------------------" );
		        System.out.println( "testRemoveAll" );

		        int count = 500;
		        JCS jcs = JCS.getInstance( "testCache1" );

		        for ( int i = 0; i <= count; i++ )
		        {
		            jcs.put( i + ":key", "data" + i );
		        }

		        for ( int i = count; i >= 0; i-- )
		        {
		            String res = (String) jcs.get( i + ":key" );
		            if ( res == null )
		            {
		                assertNotNull( "[" + i + ":key] should not be null", res );
		            }
		        }
		        System.out.println( "Confirmed that " + count + " items could be found" );

		        System.out.println( jcs.getStats() );

		        jcs.clear();

		        for ( int i = count; i >= 0; i-- )
		        {
		            String res = (String) jcs.get( i + ":key" );
		            if ( res != null )
		            {
		                assertNull( "[" + i + ":key] should be null after remvoeall" + jcs.getStats(), res );
		            }
		        }
		        System.out.println( "Confirmed that all items were removed" );

		    }

		    /**
		     * Verify that we can clear repeatedly without error.
		     *
		     * @throws Exception
		     */
		    public void testClearRepeatedlyWithoutError()
		        throws Exception
		    {

		        System.out.println( "------------------------------------------" );
		        System.out.println( "testRemoveAll" );

		        int count = 500;
		        JCS jcs = JCS.getInstance( "testCache1" );

		        jcs.clear();

		        for ( int i = 0; i <= count; i++ )
		        {
		            jcs.put( i + ":key", "data" + i );
		        }

		        for ( int i = count; i >= 0; i-- )
		        {
		            String res = (String) jcs.get( i + ":key" );
		            if ( res == null )
		            {
		                assertNotNull( "[" + i + ":key] should not be null", res );
		            }
		        }
		        System.out.println( "Confirmed that " + count + " items could be found" );

		        System.out.println( jcs.getStats() );

		        for ( int i = count; i >= 0; i-- )
		        {
		            jcs.put( i + ":key", "data" + i );
		            jcs.clear();
		            String res = (String) jcs.get( i + ":key" );
		            if ( res != null )
		            {
		                assertNull( "[" + i + ":key] should be null after remvoeall" + jcs.getStats(), res );
		            }
		        }
		        System.out.println( "Confirmed that all items were removed" );

		    }

}
