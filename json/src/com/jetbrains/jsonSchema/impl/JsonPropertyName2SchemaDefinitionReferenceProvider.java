/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.jsonSchema.impl;

import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.jsonSchema.ide.JsonSchemaService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Irina.Chernushina on 4/15/2016.
 */
public class JsonPropertyName2SchemaDefinitionReferenceProvider extends PsiReferenceProvider {
  @NotNull
  @Override
  public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
    return new PsiReference[] {new JsonPropertyName2SchemaRefReference((JsonStringLiteral)element)};
  }

  private static class JsonPropertyName2SchemaRefReference extends JsonSchemaBaseReference<JsonStringLiteral> {
    public JsonPropertyName2SchemaRefReference(JsonStringLiteral element) {
      super(element, ElementManipulators.getValueTextRange(element));
    }

    @Nullable
    @Override
    public PsiElement resolveInner() {
      final JsonSchemaService service = JsonSchemaService.Impl.get(myElement.getProject());
      final VirtualFile file = myElement.getContainingFile().getVirtualFile();
      if (file == null) return null;
      final List<JsonSchemaVariantsTreeBuilder.Step> steps = JsonOriginalPsiWalker.INSTANCE.findPosition(getElement(), true, true);
      if (steps == null) return null;
      final JsonSchemaObject schemaObject = service.getSchemaObject(file);
      if (schemaObject != null) {
        final JsonProperty parentProperty = PsiTreeUtil.getParentOfType(myElement, JsonProperty.class);
        return new JsonSchemaResolver(schemaObject, true, steps)
          .findNavigationTarget(false, parentProperty == null ? null : parentProperty.getValue(),
                                JsonSchemaService.isSchemaFile(myElement.getContainingFile()));
      }
      return null;
    }
  }
}
