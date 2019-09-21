package com.foo;

import static org.junit.Assert.*;
import org.junit.*;
import org.hibernate.*;
import static org.mockito.Mockito.*;

public class TestGeneratedFiles {
	@Test
	public void testToStringMethods() {
		assertEquals(new A().toString(), "generated A from a-codegen");
		assertEquals(new B().toString(), "generated B from a-codegen");
		assertEquals(new C().toString(), "generated C from b-codegen");
		assertEquals(new D().toString(), "generated D from b-codegen");
	}
	
	@Test
	public void testCompileAndRuntimeClasspathsAreOk() {
		Session session = mock(Session.class);
		new ProjectANonGeneratedJavaFile();
		new ProjectBNonGeneratedJavaFile();
	}
}