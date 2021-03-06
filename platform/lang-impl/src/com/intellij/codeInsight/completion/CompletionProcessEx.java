// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.codeInsight.completion;

import com.intellij.openapi.Disposable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * @author yole
 */
interface CompletionProcessEx extends CompletionProcess {
  OffsetsInFile getHostOffsets();
  void registerChildDisposable(@NotNull Supplier<Disposable> child);
}
