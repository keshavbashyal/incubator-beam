/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.cloud.dataflow.sdk.runners.inprocess;

import com.google.cloud.dataflow.sdk.coders.CannotProvideCoderException;
import com.google.cloud.dataflow.sdk.coders.Coder;
import com.google.cloud.dataflow.sdk.transforms.PTransform;
import com.google.cloud.dataflow.sdk.values.PInput;
import com.google.cloud.dataflow.sdk.values.POutput;
import com.google.cloud.dataflow.sdk.values.TypedPValue;

/**
 * A base class for implementing {@link PTransform} overrides, which behave identically to the
 * delegate transform but with overridden methods. Implementors are required to implement
 * {@link #delegate()}, which returns the object to forward calls to, and {@link #apply(PInput)}.
 */
public abstract class ForwardingPTransform<InputT extends PInput, OutputT extends POutput>
    extends PTransform<InputT, OutputT> {
  protected abstract PTransform<InputT, OutputT> delegate();

  @Override
  public OutputT apply(InputT input) {
    return delegate().apply(input);
  }

  @Override
  public void validate(InputT input) {
    delegate().validate(input);
  }

  @Override
  public String getName() {
    return delegate().getName();
  }

  @Override
  public <T> Coder<T> getDefaultOutputCoder(InputT input, @SuppressWarnings("unused")
      TypedPValue<T> output) throws CannotProvideCoderException {
    return delegate().getDefaultOutputCoder(input, output);
  }
}
