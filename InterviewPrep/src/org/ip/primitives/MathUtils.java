package org.ip.primitives;

public class MathUtils {
	public static int log2( int bits )
	{
	    if( bits == 0 ) return 0;
	    return 32 - Integer.numberOfLeadingZeros( bits - 1 );
	}
}
