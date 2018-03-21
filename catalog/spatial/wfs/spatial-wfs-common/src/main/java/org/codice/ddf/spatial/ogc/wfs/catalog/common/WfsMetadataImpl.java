package org.codice.ddf.spatial.ogc.wfs.catalog.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.codice.ddf.spatial.ogc.wfs.catalog.message.api.WfsMetadata;

public final class WfsMetadataImpl<T> implements WfsMetadata {
  private final Supplier<String> idSupplier;

  private final Supplier<String> coordinateOrderSupplier;

  private final List<T> descriptors;

  private final Class<T> descriptorClass;

  public WfsMetadataImpl(
      Supplier<String> idSupplier,
      Supplier<String> coordinateOrderSupplier,
      Class<T> descriptorClass) {
    this.idSupplier = idSupplier;
    this.coordinateOrderSupplier = coordinateOrderSupplier;
    this.descriptorClass = descriptorClass;
    this.descriptors = new ArrayList<>();
  }

  @Override
  public String getId() {
    return this.idSupplier.get();
  }

  @Override
  public String getCoordinateOrder() {
    return this.coordinateOrderSupplier.get();
  }

  @Override
  public List<T> getDescriptors() {
    return Collections.unmodifiableList(this.descriptors);
  }

  public void addEntry(T featureDescription) {
    this.descriptors.add(featureDescription);
  }
}
