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


@RunWith(value = Parameterized.class)
public class MyJCSLightLoadUnitTest {
	
	private int items;
	private String expected;
	private JCS jcs;
	
	
	@Before
	public void setUp() { 
		configure();
	}
	
	public MyJCSLightLoadUnitTest(int items, String expected) {
		this.items = items;
		this.expected = expected;
		
	}
	
	
	private void configure(){
	
        JCS.setConfigFilename("/TestSimpleLoad.ccf");
        try {
        	jcs = JCS.getInstance( "testCache1" );
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{999, null},
		});
	}
	
	@Test
	public void testSimpleLoad()
	        throws Exception
	    {
	        //JCS jcs = JCS.getInstance( "testCache1" );
	        //        ICompositeCacheAttributes cattr = jcs.getCacheAttributes();
	        //        cattr.setMaxObjects( 20002 );
	        //        jcs.setCacheAttributes( cattr );
	        
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
