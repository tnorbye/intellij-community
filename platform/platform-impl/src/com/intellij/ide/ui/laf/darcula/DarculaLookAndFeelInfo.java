/*
 * Copyright 2000-2014 JetBrains s.r.o.
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
package com.intellij.ide.ui.laf.darcula;

import com.intellij.ide.IdeBundle;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

/**
 * @author Konstantin Bulenkov
 */
public class DarculaLookAndFeelInfo extends UIManager.LookAndFeelInfo {
    @NonNls public static final String CLASS_NAME = DarculaLaf.class.getName();
    public DarculaLookAndFeelInfo(){
      super(IdeBundle.message("idea.dark.look.and.feel"), CLASS_NAME);
    }

    public boolean equals(Object obj){
      return (obj instanceof DarculaLookAndFeelInfo);
    }

    public int hashCode(){
      return getName().hashCode();
    }
}
