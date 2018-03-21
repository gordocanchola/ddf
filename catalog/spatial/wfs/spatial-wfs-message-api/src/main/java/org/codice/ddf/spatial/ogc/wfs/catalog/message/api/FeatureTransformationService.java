package org.codice.ddf.spatial.ogc.wfs.catalog.message.api;

import ddf.catalog.data.Metacard;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * The FeatureTransformationService aggregates the FeatureTransformer services. It splits the given
 * WFS response into individual FeatureMembers and passes those FeatureTransformer services. It
 * passes the FeatureMember to FeatureTransformers in some implementation-dependent order until one
 * of them returns something other than Optional.empty() or there are no FeatureTransformers left.
 */
public interface FeatureTransformationService
    extends BiFunction<InputStream, WfsMetadata, Optional<List<Metacard>>> {

  /**
   * @param featureCollection - the WFS response XML to be de-serialized.
   * @param metadata - describes the structure of the WFS response.
   * @return an Optional containing a java.util.List of Metacards (one for each FeatureMember in the
   *     WFS response XML) or Optional.empty().
   */
  Optional<List<Metacard>> apply(InputStream featureCollection, WfsMetadata metadata);
}
