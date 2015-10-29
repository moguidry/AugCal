/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aug.site.cms.ServiceImpls;

import java.util.List;
import java.util.Map;
import org.markdown4j.Plugin;

/**
 *
 * @author chris
 */
public class RawHtmlPlugin extends Plugin {

	public RawHtmlPlugin() {
		super("raw");
	}

	@Override
	public void emit(StringBuilder out, List<String> lines, Map<String, String> params) {
		for (String line : lines) {
			out.append(line);
		}
	}

}
