package org.codice.ddf.spatial.ogc.wfs.v110.catalog.source;

import ddf.catalog.data.Metacard;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import ogc.schema.opengis.wfs_capabilities.v_1_0_0.FeatureTypeType;
import org.codice.ddf.spatial.ogc.wfs.catalog.common.WfsFeatureCollection;
import org.codice.ddf.spatial.ogc.wfs.catalog.message.api.FeatureTransformationService;
import org.codice.ddf.spatial.ogc.wfs.catalog.message.api.WfsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WfsMessageBodyReader implements MessageBodyReader<WfsFeatureCollection> {
  private WfsMetadata<FeatureTypeType> wfsMetadata;

  private FeatureTransformationService featureTransformationService;

  private static final Logger LOGGER = LoggerFactory.getLogger(WfsMessageBodyReader.class);

  public WfsMessageBodyReader(
      FeatureTransformationService featureTransformationService,
      WfsMetadata<FeatureTypeType> wfsMetadata) {
    this.featureTransformationService = featureTransformationService;
    this.wfsMetadata = wfsMetadata;
  }

  @Override
  public boolean isReadable(
      Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
    if (!WfsFeatureCollection.class.isAssignableFrom(clazz)) {
      LOGGER.debug("{} class is not readable", clazz);
      return false;
    }

    return true;
  }

  @Override
  public WfsFeatureCollection readFrom(
      Class<WfsFeatureCollection> aClass,
      Type type,
      Annotation[] annotations,
      MediaType mediaType,
      MultivaluedMap<String, String> multivaluedMap,
      InputStream inputStream) {
    List<Metacard> featureMembers =
        featureTransformationService.apply(inputStream, wfsMetadata).orElse(new ArrayList<>());

    WfsFeatureCollection result = new WfsFeatureCollection();
    result.setFeatureMembers(featureMembers);
    return result;
  }
}
