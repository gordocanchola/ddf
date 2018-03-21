package org.codice.ddf.spatial.ogc.wfs.catalog.common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

import org.codice.ddf.spatial.ogc.wfs.catalog.message.api.WfsMetadata;
import org.junit.Before;
import org.junit.Test;

public class WfsMetadataImplTest {

  public static final String TEST_ID = "TEST_ID";

  public static final String COORDINATE_ORDER = "LAT/LON";

  private WfsMetadata<FeatureMetacardType> testWfsMetadata;

  @Before
  public void setup() {
    WfsMetadataImpl<FeatureMetacardType> testWfsMetadataImpl =
        new WfsMetadataImpl<FeatureMetacardType>(
            () -> TEST_ID, () -> COORDINATE_ORDER, FeatureMetacardType.class);
    FeatureMetacardType mockFeatureMetacardType = mock(FeatureMetacardType.class);
    testWfsMetadataImpl.addEntry(mockFeatureMetacardType);
    this.testWfsMetadata = testWfsMetadataImpl;
  }

  @Test
  public void testWfsMetadataImpl() {
    assertThat(testWfsMetadata.getCoordinateOrder(), is(COORDINATE_ORDER));
    assertThat(testWfsMetadata.getId(), is(TEST_ID));
    assertThat(testWfsMetadata.getDescriptors().size(), is(1));
  }
}
