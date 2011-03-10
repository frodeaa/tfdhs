package tfdhs.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

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

	assertNotNull("Header table model null", model.getHeaderFields());
	assertEquals("Default method", HttpMethod.GET, model.getMethod());
	assertNull("Url not null", model.getUrl());
	assertNull("Body not null", model.getBody());
	assertFalse("Body set", model.isBodySet());
	assertFalse("FollowRedirects", model.isFollowRedirects());

    }

    @Test
    public void testHashCode() {
	int hash = model.hashCode();
	int newHash = model.hashCode();
	assertTrue("HAshCode", hash == newHash);

	hash = model.hashCode();
	model.setBody("body");
	newHash = model.hashCode();
	assertFalse("HAshCode", hash == newHash);

	hash = model.hashCode();
	model.setBodySet(true);
	newHash = model.hashCode();
	assertFalse("HAshCode", hash == newHash);

	hash = model.hashCode();
	model.setFollowRedirects(true);
	newHash = model.hashCode();
	assertFalse("HAshCode", hash == newHash);

	hash = model.hashCode();
	model.getHeaderFields().put("value", new ArrayList<String>());
	newHash = model.hashCode();
	assertFalse("HAshCode", hash == newHash);

	hash = model.hashCode();
	model.setMethod(HttpMethod.POST);
	newHash = model.hashCode();
	assertFalse("HAshCode", hash == newHash);

	hash = model.hashCode();
	model.setUrl("someurl");
	newHash = model.hashCode();
	assertFalse("HAshCode", hash == newHash);

    }

    @Test
    public void testHashCodeFieldsNull() {

	int hash = model.hashCode();
	model.setBody(null);
	model.setBodySet(false);
	model.setFollowRedirects(false);
	model.setHeaderFields(null);
	model.setMethod(null);
	model.setUrl(null);
	int newHash = model.hashCode();
	assertFalse("HAshCode", hash == newHash);

    }

    @Test
    public void testEquals() {

	assertFalse(model.equals(null));
	assertFalse(model.equals(""));
	assertTrue(model.equals(model));

	ClientModel other = new ClientModel();
	assertTrue(model.equals(other));

	model.setBody("");
	other.setBody(null);
	assertFalse(model.equals(other));

	other.setBody("");
	assertTrue(model.equals(other));

	model.setBody(null);
	assertFalse(model.equals(other));

	other.setBody(null);
	model.setBody(null);
	assertTrue(model.equals(other));

	model.setBodySet(true);
	assertFalse(model.equals(other));

	other.setBodySet(true);
	assertTrue(model.equals(other));

	model.setFollowRedirects(true);
	assertFalse(model.equals(other));

	other.setFollowRedirects(true);
	assertTrue(model.equals(other));

	model.getHeaderFields().put("value", new ArrayList<String>());
	assertFalse(model.equals(other));

	other.getHeaderFields().put("value", new ArrayList<String>());
	assertTrue(model.equals(other));

	model.setHeaderFields(null);
	assertFalse(model.equals(other));

	other.setHeaderFields(null);
	assertTrue(model.equals(other));

	model.setMethod(HttpMethod.POST);
	assertFalse(model.equals(other));

	other.setMethod(HttpMethod.POST);
	assertTrue(model.equals(other));

	model.setUrl("");
	other.setUrl(null);
	assertFalse(model.equals(other));

	other.setUrl("");
	assertTrue(model.equals(other));

	model.setUrl(null);
	assertFalse(model.equals(other));

	other.setUrl(null);
	model.setUrl(null);
	assertTrue(model.equals(other));

    }

    @Test
    public void toStringContainerUrl() {

	model.setUrl("http://theurl");

	assertTrue("Missing url", model.toString().contains("http://theurl"));

    }
}
