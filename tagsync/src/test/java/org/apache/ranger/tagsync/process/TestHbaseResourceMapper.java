/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ranger.tagsync.process;

import org.apache.atlas.typesystem.IReferenceableInstance;
import org.apache.ranger.plugin.model.RangerServiceResource;
import org.apache.ranger.tagsync.source.atlas.AtlasHbaseResourceMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class TestHbaseResourceMapper {
    private static final String TABLE_QUALIFIED_NAME          = "table@cl1";
    private static final String COLUMN_FAMILY_QUALIFIED_NAME  = "table.family@cl1";
    private static final String COLUMN_QUALIFIED_NAME         = "table.family.column@cl1";

    private static final String DOTTED_TABLE_QUALIFIED_NAME   = "table.prefix.1@cl1";
    private static final String DOTTED_COLUMN_FAMILY_QUALIFIED_NAME  = "table.prefix.1.family@cl1";
    private static final String DOTTED_COLUMN_QUALIFIED_NAME         = "table.prefix.1.family.column@cl1";

    private static final String SERVICE_NAME            = "cl1_hbase";
    private static final String RANGER_TABLE            = "table";
    private static final String RANGER_COLUMN_FAMILY    = "family";
    private static final String RANGER_COLUMN           = "column";

    private static final String DOTTED_RANGER_TABLE     = "table.prefix.1";

    AtlasHbaseResourceMapper resourceMapper = new AtlasHbaseResourceMapper();

    @Test
    public void testHbaseTable() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, TABLE_QUALIFIED_NAME);

        IReferenceableInstance entity   = getHbaseTableEntity(entAttribs);
        RangerServiceResource  resource = resourceMapper.buildResource(entity);

        assertTableResource(resource, false);
    }

    @Test
    public void testHbaseColumnFamily() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, COLUMN_FAMILY_QUALIFIED_NAME);

        IReferenceableInstance entity   = getHbaseColumnFamilyEntity(entAttribs);
        RangerServiceResource  resource = resourceMapper.buildResource(entity);

        assertColumnFamilyResource(resource, false);
    }

    @Test
    public void testHbaseColumn() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, COLUMN_QUALIFIED_NAME);

        IReferenceableInstance entity   = getHbaseColumnEntity(entAttribs);
        RangerServiceResource  resource = resourceMapper.buildResource(entity);

        assertColumnResource(resource, false);
    }

    @Test
    public void testHbaseResourceFromMissingAttribs() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        IReferenceableInstance entity = getHbaseTableEntity(entAttribs);

        try {
            RangerServiceResource resource = resourceMapper.buildResource(entity);

            Assert.fail("expected exception. Found " + resource);
        } catch(Exception excp) {
            // ignore
        }
    }

    @Test
    public void testHbaseResourceFromMissingColumnFamilyName() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, TABLE_QUALIFIED_NAME);

        IReferenceableInstance entity = getHbaseColumnFamilyEntity(entAttribs);

        try {
            RangerServiceResource resource = resourceMapper.buildResource(entity);

            Assert.fail("expected exception. Found " + resource);
        } catch(Exception excp) {
            // ignore
        }
    }

    @Test
    public void testHbaseResourceFromMissingColumnName() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, COLUMN_FAMILY_QUALIFIED_NAME);

        IReferenceableInstance entity = getHbaseColumnEntity(entAttribs);

        try {
            RangerServiceResource resource = resourceMapper.buildResource(entity);

            Assert.fail("expected exception. Found " + resource);
        } catch(Exception excp) {
            // ignore
        }
    }

    @Test
    public void testHbaseDottedTable() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, DOTTED_TABLE_QUALIFIED_NAME);

        IReferenceableInstance entity   = getHbaseTableEntity(entAttribs);
        RangerServiceResource  resource = resourceMapper.buildResource(entity);

        assertTableResource(resource, true);
    }

    @Test
    public void testHbaseDottedColumnFamily() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, DOTTED_COLUMN_FAMILY_QUALIFIED_NAME);

        IReferenceableInstance entity   = getHbaseColumnFamilyEntity(entAttribs);
        RangerServiceResource  resource = resourceMapper.buildResource(entity);

        assertColumnFamilyResource(resource, true);
    }

    @Test
    public void testHbaseDottedColumn() throws Exception {
        Map<String, Object> entAttribs = new HashMap<String, Object>();

        entAttribs.put(AtlasHbaseResourceMapper.ENTITY_ATTRIBUTE_QUALIFIED_NAME, DOTTED_COLUMN_QUALIFIED_NAME);

        IReferenceableInstance entity   = getHbaseColumnEntity(entAttribs);
        RangerServiceResource  resource = resourceMapper.buildResource(entity);

        assertColumnResource(resource, true);
    }

    private IReferenceableInstance getHbaseTableEntity(Map<String, Object> entAttribs) throws Exception {
        IReferenceableInstance entity = Mockito.mock(IReferenceableInstance.class);

        Mockito.when(entity.getTypeName()).thenReturn(AtlasHbaseResourceMapper.ENTITY_TYPE_HBASE_TABLE);
        Mockito.when(entity.getValuesMap()).thenReturn(entAttribs);

        return entity;
    }

    private IReferenceableInstance getHbaseColumnFamilyEntity(Map<String, Object> entAttribs) throws Exception {
        IReferenceableInstance entity = Mockito.mock(IReferenceableInstance.class);

        Mockito.when(entity.getTypeName()).thenReturn(AtlasHbaseResourceMapper.ENTITY_TYPE_HBASE_COLUMN_FAMILY);
        Mockito.when(entity.getValuesMap()).thenReturn(entAttribs);

        return entity;
    }

    private IReferenceableInstance getHbaseColumnEntity(Map<String, Object> entAttribs) throws Exception {
        IReferenceableInstance entity = Mockito.mock(IReferenceableInstance.class);

        Mockito.when(entity.getTypeName()).thenReturn(AtlasHbaseResourceMapper.ENTITY_TYPE_HBASE_COLUMN);
        Mockito.when(entity.getValuesMap()).thenReturn(entAttribs);

        return entity;
    }

    private void assertServiceResource(RangerServiceResource resource) {
        Assert.assertNotNull(resource);
        Assert.assertEquals(SERVICE_NAME, resource.getServiceName());
        Assert.assertNotNull(resource.getResourceElements());
    }

    private void assertTableResource(RangerServiceResource resource, boolean isDottedTable) {
        assertServiceResource(resource);

        Assert.assertEquals(1, resource.getResourceElements().size());

        Assert.assertTrue(resource.getResourceElements().containsKey(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE));
        Assert.assertNotNull(resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues());
        Assert.assertEquals(1, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues().size());
        Assert.assertEquals(isDottedTable ? DOTTED_RANGER_TABLE : RANGER_TABLE, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues().get(0));
    }

    private void assertColumnFamilyResource(RangerServiceResource resource, boolean isDottedTable) {
        assertServiceResource(resource);

        Assert.assertEquals(2, resource.getResourceElements().size());

        Assert.assertTrue(resource.getResourceElements().containsKey(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE));
        Assert.assertNotNull(resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues());
        Assert.assertEquals(1, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues().size());
        Assert.assertEquals(isDottedTable ? DOTTED_RANGER_TABLE : RANGER_TABLE, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues().get(0));

        Assert.assertTrue(resource.getResourceElements().containsKey(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY));
        Assert.assertNotNull(resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY).getValues());
        Assert.assertEquals(1, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY).getValues().size());
        Assert.assertEquals(RANGER_COLUMN_FAMILY, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY).getValues().get(0));
    }

    private void assertColumnResource(RangerServiceResource resource, boolean isDottedTable) {
        assertServiceResource(resource);

        Assert.assertEquals(3, resource.getResourceElements().size());

        Assert.assertTrue(resource.getResourceElements().containsKey(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE));
        Assert.assertNotNull(resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues());
        Assert.assertEquals(1, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues().size());
        Assert.assertEquals(isDottedTable ? DOTTED_RANGER_TABLE : RANGER_TABLE, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_TABLE).getValues().get(0));

        Assert.assertTrue(resource.getResourceElements().containsKey(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY));
        Assert.assertNotNull(resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY).getValues());
        Assert.assertEquals(1, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY).getValues().size());
        Assert.assertEquals(RANGER_COLUMN_FAMILY, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN_FAMILY).getValues().get(0));

        Assert.assertTrue(resource.getResourceElements().containsKey(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN));
        Assert.assertNotNull(resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN).getValues());
        Assert.assertEquals(1, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN).getValues().size());
        Assert.assertEquals(RANGER_COLUMN, resource.getResourceElements().get(AtlasHbaseResourceMapper.RANGER_TYPE_HBASE_COLUMN).getValues().get(0));
    }
}
