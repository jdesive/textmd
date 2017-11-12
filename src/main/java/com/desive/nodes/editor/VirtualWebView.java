/*
 * Copyright (C) 2017  TextMd
 *
 * This file is part of TextMd.
 *
 * TextMd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.nodes.editor;

import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.fxmisc.flowless.Virtualized;
import org.reactfx.value.Val;
import org.reactfx.value.Var;

/*
 Created by Jack DeSive on 11/8/2017 at 1:40 PM
*/
public class VirtualWebView extends StackPane implements Virtualized{

    private WebView webView;
    private Val<Double> totalWidth, totalHeight;
    private Var<Double> estimateScrollX, estimateScrollY;

    VirtualWebView(WebView webView) {
        this.webView = webView;
        getChildren().add(webView);

        totalWidth = Val.create(() -> Double.parseDouble(String.valueOf(webView.getEngine().executeScript("document.body.scrollWidth"))));
        totalHeight = Val.create(() -> Double.parseDouble(String.valueOf(webView.getEngine().executeScript("document.body.scrollHeight"))));
        estimateScrollX = Var.newSimpleVar(Double.parseDouble(String.valueOf(webView.getEngine().executeScript("window.pageXOffset || document.documentElement.scrollLeft"))));
        estimateScrollY = Var.newSimpleVar(Double.parseDouble(String.valueOf(webView.getEngine().executeScript("window.pageYOffset || document.documentElement.scrollTop"))));
    }

    public WebEngine getEngine() {
        return webView.getEngine();
    }

    public void setScrollXValue(double x) {
        webView.getEngine().executeScript(String.format("document.documentElement.scrollLeft = document.body.scrollLeft = %s;", String.valueOf(x)));
    }

    public void setScrollYValue(double y) {
        webView.getEngine().executeScript(String.format("document.documentElement.scrollTop = document.body.scrollTop = %s;", String.valueOf(y)));
    }

    @Override
    public Val<Double> totalWidthEstimateProperty() {
        return totalWidth;
    }

    @Override
    public Val<Double> totalHeightEstimateProperty() {
        return totalHeight;
    }

    @Override
    public Var<Double> estimatedScrollXProperty() {
        return estimateScrollX;
    }

    @Override
    public Var<Double> estimatedScrollYProperty() {
        return estimateScrollY;
    }
}
