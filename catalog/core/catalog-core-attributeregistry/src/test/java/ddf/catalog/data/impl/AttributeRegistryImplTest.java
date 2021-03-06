/**
 * Copyright (c) Codice Foundation
 * <p>
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package ddf.catalog.data.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ddf.catalog.data.AttributeDescriptor;
import ddf.catalog.data.AttributeRegistry;

public class AttributeRegistryImplTest {
    private AttributeRegistry registry;

    @Before
    public void setup() {
        registry = new AttributeRegistryImpl();
    }

    @Test
    public void testAddAttribute() {
        final String attributeName = "test";
        final AttributeDescriptor descriptor = new AttributeDescriptorImpl(attributeName,
                true,
                false,
                true,
                false,
                BasicTypes.STRING_TYPE);
        assertThat(registry.register(descriptor), is(true));

        final Optional<AttributeDescriptor> descriptorOptional = registry.lookup(attributeName);
        assertThat(descriptorOptional.isPresent(), is(true));
        assertThat(descriptorOptional.get(), is(descriptor));
    }

    @Test
    public void testRemoveAttribute() {
        final String attributeName = "attribute";
        final AttributeDescriptor descriptor = new AttributeDescriptorImpl(attributeName,
                true,
                false,
                true,
                false,
                BasicTypes.STRING_TYPE);
        assertThat(registry.register(descriptor), is(true));

        Optional<AttributeDescriptor> descriptorOptional = registry.lookup(attributeName);
        assertThat(descriptorOptional.isPresent(), is(true));

        registry.deregister(attributeName);
        descriptorOptional = registry.lookup(attributeName);
        assertThat(descriptorOptional.isPresent(), is(false));
    }

    @Test
    public void testAddAttributeWithSameName() {
        final String attributeName = "foo";
        final AttributeDescriptor descriptor1 = new AttributeDescriptorImpl(attributeName,
                true,
                true,
                true,
                true,
                BasicTypes.STRING_TYPE);
        assertThat(registry.register(descriptor1), is(true));

        final AttributeDescriptor descriptor2 = new AttributeDescriptorImpl(attributeName,
                false,
                false,
                false,
                false,
                BasicTypes.BINARY_TYPE);
        assertThat(registry.register(descriptor2), is(false));

        final Optional<AttributeDescriptor> descriptorOptional = registry.lookup(attributeName);
        assertThat(descriptorOptional.isPresent(), is(true));
        assertThat(descriptorOptional.get(), is(descriptor1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAttributeDescriptor() {
        registry.register(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAttributeDescriptorName() {
        final AttributeDescriptor descriptor = new AttributeDescriptorImpl(null,
                true,
                false,
                true,
                false,
                BasicTypes.STRING_TYPE);
        registry.register(descriptor);
    }
}
