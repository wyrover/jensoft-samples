/*
 * JenSoft API - Charting Framework
 * http://www.jensoftapi.com
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package org.jensoft.catalog.views.radar;

import java.awt.Color;
import java.awt.Font;

import org.jensoft.core.catalog.nature.JenSoftView;
import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.glyphmetrics.painter.marker.RoundMarker;
import org.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.palette.color.PetalPalette;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.outline.OutlinePlugin;
import org.jensoft.core.plugin.radar.DimensionMetrics;
import org.jensoft.core.plugin.radar.Radar;
import org.jensoft.core.plugin.radar.RadarDimension;
import org.jensoft.core.plugin.radar.RadarSurface;
import org.jensoft.core.plugin.radar.RadarSurfaceAnchor;
import org.jensoft.core.plugin.radar.RadarToolkit;
import org.jensoft.core.plugin.radar.RadarView;
import org.jensoft.core.plugin.radar.painter.label.RadarDimensionDefaultLabel;
import org.jensoft.core.view.background.ViewDarkBackground;

@JenSoftView(background=ViewDarkBackground.class)
public class RadarSurface1Demo extends RadarView {

	public RadarSurface1Demo() {

		super();

		// **************************************
		// RADAR
		// **************************************
		// create radar center (0,0) in user nature coordinate with 200 pixel
		// radius and register radar in view
		// API : Radar radar = RadarFactory.createRadar(centerX, centerY,
		// radius)
		Radar radar = RadarToolkit.createRadar(0, 0, 200);
		addRadar(radar);

		// **************************************
		// RADAR DIMENSIONS
		// **************************************
		// create radar dimensions and dimension label
		// API : RadarDimension radarDim =
		// RadarFactory.createDimensionMetrics(label, value, stylePosition,
		// divergence, glyphFill, glyphMarker, metricsFont)
		RadarDimension d1 = RadarToolkit.createDimension("d1", 0, 0, 100);
		RadarDimension d2 = RadarToolkit.createDimension("d2", 60, 0, 100);
		RadarDimension d3 = RadarToolkit.createDimension("d3", 120, 0, 100);
		RadarDimension d4 = RadarToolkit.createDimension("d4", 180, 0, 100);
		RadarDimension d5 = RadarToolkit.createDimension("d5", 240, 0, 100);
		RadarDimension d6 = RadarToolkit.createDimension("d6", 300, 0, 100);
		RadarToolkit.pushDimensions(radar, d1, d2, d3, d4, d5, d6);

		// Create label dimension
		// LABELS
		float[] fractions = { 0f, 0.3f, 0.7f, 1f };
		Color[] c = { new Color(0, 0, 0, 20), new Color(0, 0, 0, 150), new Color(0, 0, 0, 150), new Color(0, 0, 0, 20) };

		// DIMENSION LABEL
		RadarDimensionDefaultLabel dimLabel1, dimLabel2, dimLabel3, dimLabel4, dimLabel5, dimLabel6;

		dimLabel1 = RadarToolkit.createDimensionDefaultLabel("Type 1", ColorPalette.WHITE, RosePalette.REDWOOD, fractions, c, 20);
		dimLabel2 = RadarToolkit.createDimensionDefaultLabel("Type 2", ColorPalette.WHITE, RosePalette.EMERALD, fractions, c, 20);
		dimLabel3 = RadarToolkit.createDimensionDefaultLabel("Type 3", ColorPalette.WHITE, RosePalette.MELON, fractions, c, 20);
		dimLabel4 = RadarToolkit.createDimensionDefaultLabel("Type 4", ColorPalette.WHITE, RosePalette.CORALRED, fractions, c, 20);
		dimLabel5 = RadarToolkit.createDimensionDefaultLabel("Type 5", ColorPalette.WHITE, RosePalette.AZALEA, fractions, c, 20);
		dimLabel6 = RadarToolkit.createDimensionDefaultLabel("Type 6", ColorPalette.WHITE, RosePalette.INDIGO, fractions, c, 20);

		d1.setDimensionLabel(dimLabel1);
		d2.setDimensionLabel(dimLabel2);
		d3.setDimensionLabel(dimLabel3);
		d4.setDimensionLabel(dimLabel4);
		d5.setDimensionLabel(dimLabel5);
		d6.setDimensionLabel(dimLabel6);
		// Create metrics dimensions max limit markers
		// API : DimensionMetrics dimMetrics =
		// RadarFactory.createDimensionMetrics(label, value, stylePosition,
		// divergence, glyphFill, glyphMarker, metricsFont)

		// label and marker painter
		GlyphFill metricsFill = new GlyphFill(RosePalette.MANDARIN, RosePalette.MELON);
		GlyphMetricMarkerPainter metricsMarker = new RoundMarker(PetalPalette.PETAL1_HC, Color.WHITE, 3);

		Font f =  new Font("Dialog", Font.PLAIN, 10);
		// metrics
		DimensionMetrics m1, m2, m3, m4, m5, m6;
		m1 = RadarToolkit.createDimensionMetrics("100", 100, StylePosition.Default, 20, metricsFill, metricsMarker, f);
		m2 = RadarToolkit.createDimensionMetrics("100", 100, StylePosition.Default, 20, metricsFill, metricsMarker, f);
		m3 = RadarToolkit.createDimensionMetrics("100", 100, StylePosition.Default, 20, metricsFill, metricsMarker, f);
		m4 = RadarToolkit.createDimensionMetrics("100", 100, StylePosition.Default, 20, metricsFill, metricsMarker, f);
		m5 = RadarToolkit.createDimensionMetrics("100", 100, StylePosition.Default, 20, metricsFill, metricsMarker, f);
		m6 = RadarToolkit.createDimensionMetrics("100", 100, StylePosition.Default, 20, metricsFill, metricsMarker, f);

		// register dimension metrics in dimensions
		d1.addMetrics(m1);
		d2.addMetrics(m2);
		d3.addMetrics(m3);
		d4.addMetrics(m4);
		d5.addMetrics(m5);
		d6.addMetrics(m6);

		// Create dimension metrics
		GlyphFill dim1Fill = new GlyphFill(ColorPalette.WHITE, RosePalette.REDWOOD);
		GlyphFill dim2Fill = new GlyphFill(ColorPalette.WHITE, RosePalette.AEGEANBLUE);

		GlyphMetricMarkerPainter dim1Marker = new TicTacMarker(RosePalette.REDWOOD, 2, 4);
		GlyphMetricMarkerPainter dim2Marker = new TicTacMarker(RosePalette.AEGEANBLUE, 2, 4);

		DimensionMetrics m1d1 = RadarToolkit.createDimensionMetrics("20", 20, StylePosition.Default, 10, dim1Fill, dim1Marker, f);
		DimensionMetrics m2d1 = RadarToolkit.createDimensionMetrics("40", 40, StylePosition.Default, 10, dim1Fill, dim1Marker, f);
		DimensionMetrics m3d1 = RadarToolkit.createDimensionMetrics("60", 60, StylePosition.Default, 10, dim1Fill, dim1Marker, f);
		DimensionMetrics m4d1 = RadarToolkit.createDimensionMetrics("80", 80, StylePosition.Default, 10, dim1Fill, dim1Marker, f);

		DimensionMetrics m1d2 = RadarToolkit.createDimensionMetrics("20", 20, StylePosition.Default, 10, dim2Fill, dim2Marker, f);
		DimensionMetrics m2d2 = RadarToolkit.createDimensionMetrics("40", 40, StylePosition.Default, 10, dim2Fill, dim2Marker, f);
		DimensionMetrics m3d2 = RadarToolkit.createDimensionMetrics("60", 60, StylePosition.Default, 10, dim2Fill, dim2Marker, f);
		DimensionMetrics m4d2 = RadarToolkit.createDimensionMetrics("80", 80, StylePosition.Default, 10, dim2Fill, dim2Marker, f);

		// push metrics in dimension
		RadarToolkit.pushMetricsDimensions(d1, m1d1, m2d1, m3d1, m4d1);
		RadarToolkit.pushMetricsDimensions(d2, m1d2, m2d2, m3d2, m4d2);

		// etc.

		// **************************************
		// SURFACE
		// **************************************

		// CREATE SURFACE
		// API : RadarFactory.createSurface(name, outlineColor, fillColor)
		RadarSurface surface = RadarToolkit.createSurface("surface1", Color.WHITE, ColorPalette.alpha(RosePalette.MANDARIN, 50));

		// register surface in radar
		radar.addSurface(surface);

		// CREATE SURFACE ANCHORS
		// API : RadarSurfaceAnchor rsa =
		// RadarToolkit.createSurfaceAnchor(dimension, label, value,
		// stylePosition, divergence, glyphFill, glyphMarker, metricsFont)
		// API : RadarSurfaceAnchor rsa =
		// RadarToolkit.createSurfaceAnchor(dimension, anchorMetrics)

		// create metrics filler and metrics marker

		metricsFill = new GlyphFill(Color.WHITE, NanoChromatique.GREEN);
		metricsMarker = new RoundMarker(NanoChromatique.GREEN, Color.WHITE, 3);

		// create surface anchors
		RadarSurfaceAnchor rsa1 = RadarToolkit.createSurfaceAnchor(d1, "A1", 67, StylePosition.Default, 10, metricsFill, metricsMarker, f);
		RadarSurfaceAnchor rsa2 = RadarToolkit.createSurfaceAnchor(d2, "A2", 70, StylePosition.Default, 10, metricsFill, null, f);
		RadarSurfaceAnchor rsa3 = RadarToolkit.createSurfaceAnchor(d3, "A3", 17, StylePosition.Default, 10, metricsFill, metricsMarker, f);
		RadarSurfaceAnchor rsa4 = RadarToolkit.createSurfaceAnchor(d4, "A4", 44, StylePosition.Default, 10, metricsFill, metricsMarker, f);
		RadarSurfaceAnchor rsa5 = RadarToolkit.createSurfaceAnchor(d5, "A5", 58, StylePosition.Default, 10, metricsFill, metricsMarker, f);
		RadarSurfaceAnchor rsa6 = RadarToolkit.createSurfaceAnchor(d6, "A6", 36, StylePosition.Default, 10, metricsFill, metricsMarker, f);

		// push anchor into surface
		RadarToolkit.pushAnchors(surface, rsa1, rsa2, rsa3, rsa4, rsa5, rsa6);

		// OUTLINE
		registerPlugin(new OutlinePlugin());

	}

}
