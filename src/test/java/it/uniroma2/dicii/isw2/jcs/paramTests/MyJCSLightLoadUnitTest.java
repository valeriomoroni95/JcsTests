import static org.junit.Assert.*;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.junit.Before;
import org.junit.Test;

public class MyJCSLightLoadUnitTest {
	
	private int items;
	private JCS jcs;
	
	
	@Before
	public void setUp() { 
		configure();
	}
	
	/*public void configure() throws CacheException {
		this.items = 20000;
		JCS.setConfigFilename( "/TestSimpleLoad.ccf" );
        JCS.getInstance( "testCache1" );
        this.jcs = JCS.getInstance( "testCache1" );
	}*/
	
	private void configure(){
		this.items=999;
        JCS.setConfigFilename("/TestSimpleLoad.ccf");
        try {
        	JCS.getInstance( "testCache1" );
        	this.jcs = JCS.getInstance( "testCache1" );
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }
	
	@Test
	public void testSimpleLoad()
	        throws Exception
	    {
	        //JCS jcs = JCS.getInstance( "testCache1" );
	        //        ICompositeCacheAttributes cattr = jcs.getCacheAttributes();
	        //        cattr.setMaxObjects( 20002 );
	        //        jcs.setCacheAttributes( cattr );
	        
	        System.out.println("Running with items: " + items);
	        
	        for ( int i = 1; i <= items; i++ )
	        {
	            jcs.put( i + ":key", "data" + i );
	        }

	        for ( int i = items; i > 0; i-- )
	        {
	            String res = (String) jcs.get( i + ":key" );
	            if ( res == null )
	            {
	                assertNotNull( "[" + i + ":key] should not be null", res );
	            }
	        }

	        // test removal
	        jcs.remove( "300:key" );
	        assertNull( jcs.get( "300:key" ) );

	    }

}
