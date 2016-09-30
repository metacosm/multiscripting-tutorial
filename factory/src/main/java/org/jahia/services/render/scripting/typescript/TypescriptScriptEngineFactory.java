/**
 * This file is part of Jahia, next-generation open source CMS:
 * Jahia's next-generation, open source CMS stems from a widely acknowledged vision
 * of enterprise application convergence - web, search, document, social and portal -
 * unified by the simplicity of web content management.
 * <p>
 * For more information, please visit http://www.jahia.com.
 * <p>
 * Copyright (C) 2002-2013 Jahia Solutions Group SA. All rights reserved.
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * <p>
 * As a special exception to the terms and conditions of version 2.0 of
 * the GPL (or any later version), you may redistribute this Program in connection
 * with Free/Libre and Open Source Software ("FLOSS") applications as described
 * in Jahia's FLOSS exception. You should have received a copy of the text
 * describing the FLOSS exception, and it is also available here:
 * http://www.jahia.com/license
 * <p>
 * Commercial and Supported Versions of the program (dual licensing):
 * alternatively, commercial and supported versions of the program may be used
 * in accordance with the terms and conditions contained in a separate
 * written agreement between you and Jahia Solutions Group SA.
 * <p>
 * If you are unsure which license is appropriate for your use,
 * please contact the sales department at sales@jahia.com.
 */
package org.jahia.services.render.scripting.typescript;

import com.xafero.ts4j.TypeScriptCompiler;
import com.xafero.ts4j.TypeScriptEngine;
import com.xafero.ts4j.TypeScriptEngineFactory;
import org.jahia.services.render.scripting.bundle.Configurable;
import org.osgi.framework.Bundle;

import javax.script.ScriptEngine;
import java.util.Collections;
import java.util.List;

/**
 * @author Christophe Laprun
 */
public class TypescriptScriptEngineFactory extends TypeScriptEngineFactory implements Configurable {
    private ScriptEngine js;

    @Override
    public ScriptEngine getScriptEngine() {
        TypeScriptCompiler compiler = TypeScriptCompiler.create(getJSScriptEngine());
        return new TypeScriptEngine(this, getJSScriptEngine(), compiler);
    }

    @Override
    public Object getParameter(String key) {
        try {
            return super.getParameter(key);
        } catch (NullPointerException e) {
            // happens when super hasn't been able to find the JS factory due to class loading issues
            return getJSScriptEngine().getFactory().getParameter(key);
        }
    }

    @Override
    public List<String> getExtensions() {
        return Collections.singletonList("ts");
    }

    private ScriptEngine getJSScriptEngine() {
        configurePreScriptEngineCreation();
        return js;
    }

    @Override
    public void configurePreRegistration(Bundle bundle) {
        // nothing to do
    }

    @Override
    public void destroy(Bundle bundle) {
        // nothing to do
    }

    @Override
    public void configurePreScriptEngineCreation() {
        if (js == null) {
            js = ScriptEngineManagerProvider.getInstance().getDxProvidedManager().getEngineByExtension("js");
        }
    }
}
