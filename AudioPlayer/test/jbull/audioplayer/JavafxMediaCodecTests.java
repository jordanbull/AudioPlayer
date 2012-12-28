/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.net.MalformedURLException;
import java.net.URL;
import jbull.audioplayer.codec.JavafxMediaCodec;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jordan
 */
public class JavafxMediaCodecTests {
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void test() throws MalformedURLException {
        Codec c = new JavafxMediaCodec();
        c.load(new URL("/home/jordan/Desktop")) ;
    }
}
