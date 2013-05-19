/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2013 Ratouit Thomas                                    *
 *                                                                           *
 * This program is free software; you can redistribute it and/or modify it   *
 * under the terms of the GNU Lesser General Public License as published by  *
 * the Free Software Foundation; either version 3 of the License, or (at     *
 * your option) any later version.                                           *
 *                                                                           *
 * This program is distributed in the hope that it will be useful, but       *
 * WITHOUT ANY WARRANTY; without even the implied warranty of                *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser   *
 * General Public License for more details.                                  *
 *                                                                           *
 * You should have received a copy of the GNU Lesser General Public License  *
 * along with this program; if not, write to the Free Software Foundation,   *
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA, or go to   *
 * http://www.gnu.org/copyleft/lesser.txt.                                   *
 *****************************************************************************/

package com.thoratou.exact.processors;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatterOptions;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import java.util.Map;
import java.util.logging.Logger;

public class IndentUtil {
    private static Logger logger = CustomLogger.getLogger();

    public String trim(String input){
        return input.trim();
    }

    public String javaCode(String input){
        //Remove all unneeded spaces and lines
        /*String[] lines = input.split(System.getProperty("line.separator"));
        StringBuffer buffer = new StringBuffer();
        for(String line : lines){
            buffer.append(line.trim());
            buffer.append("\n");
        }*/

        // take default Eclipse formatting options
        Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_7);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_7);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);

        options.put(
                DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR,
                JavaCore.SPACE);

        // change the option to wrap each enum constant on a new line
        options.put(
                DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS,
                DefaultCodeFormatterConstants.createAlignmentValue(
                        true,
                        DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
                        DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

        // change the option to wrap each enum constant on a new line
        options.put(
                DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ASSIGNMENT,
                DefaultCodeFormatterConstants.createAlignmentValue(
                        false,
                        DefaultCodeFormatterConstants.WRAP_NO_SPLIT,
                        DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

        // change the option to wrap each enum constant on a new line
        options.put(
                DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SELECTOR_IN_METHOD_INVOCATION,
                DefaultCodeFormatterConstants.createAlignmentValue(
                        false,
                        DefaultCodeFormatterConstants.WRAP_NO_SPLIT,
                        DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

        CodeFormatter cf = ToolFactory.createCodeFormatter(options);

        IDocument dc = new Document(input);
        TextEdit te = cf.format(CodeFormatter.K_COMPILATION_UNIT, dc.get(), 0,dc.get().length(),0,null);

        try {
            te.apply(dc);
        } catch (MalformedTreeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dc.get();
    }
}
