package tfdhs.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tfdhs.api.HttpMethod;

public class ClientModelTest {

    private ClientModel model;

    @Before
    public void setUp() {
	model = new ClientModel();
    }

    @Test
    public void testNewDefaults() {

//	assertNotNull("Header table model null", model.getHeaders());
	assertEquals("Default method", HttpMethod.GET, model.getMethod());
	assertNull("Url not null", model.getUrl());
	assertNull("Body not null", model.getBody());
	assertFalse("Body set", model.isBodySet());
	assertFalse("FollowRedirects", model.isFollowRedirects());

    }

    @Test
    public void testHeaderColumns() {

//	assertEquals("Colum 0 name", "Header Name", model.getHeaders()
//		.getColumnName(0));
//	assertEquals("Colum 1 name", "Header Value", model.getHeaders()
//		.getColumnName(1));
//
//	assertEquals("Column class", String.class, model.getHeaders()
//		.getColumnClass(0));
//	assertEquals("Column class", String.class, model.getHeaders()
//		.getColumnClass(1));

    }
}
