/*
 * JenSoft API - Charting Framework
 * http://www.jensoftapi.com
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package org.jensoft.catalog.views.function.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

import org.jensoft.core.catalog.nature.JenSoftView;
import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.GlyphMetricsNature;
import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.glyphmetrics.painter.marker.RoundMarker;
import org.jensoft.core.graphics.Shader;
import org.jensoft.core.palette.color.PetalPalette;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.background.DeviceGradientBackgroundPlugin;
import org.jensoft.core.plugin.background.DeviceNightBackground;
import org.jensoft.core.plugin.function.FunctionPlugin.CurveFunction;
import org.jensoft.core.plugin.function.FunctionToolkit;
import org.jensoft.core.plugin.function.curve.Curve;
import org.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import org.jensoft.core.plugin.function.source.UserSourceFunction;
import org.jensoft.core.plugin.function.tools.peaktracker.PeakTrackerDeviceContext;
import org.jensoft.core.plugin.function.tools.peaktracker.PeakTrackerPlugin;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerDeviceContext;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerEvent;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerListener;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerPlugin;
import org.jensoft.core.plugin.grid.Grid.GridOrientation;
import org.jensoft.core.plugin.grid.GridPlugin.MultiplierGrid;
import org.jensoft.core.plugin.message.Message;
import org.jensoft.core.plugin.message.MessagePlugin;
import org.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import org.jensoft.core.plugin.stripe.StripePlugin.MultiplierStripe;
import org.jensoft.core.plugin.stripe.painter.StripePalette;
import org.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import org.jensoft.core.plugin.translate.TranslatePlugin;
import org.jensoft.core.plugin.zoom.box.ZoomBoxDefaultDeviceContext;
import org.jensoft.core.plugin.zoom.box.ZoomBoxPlugin;
import org.jensoft.core.plugin.zoom.lens.LensDefaultDeviceContext;
import org.jensoft.core.plugin.zoom.lens.LensX;
import org.jensoft.core.plugin.zoom.lens.LensY;
import org.jensoft.core.plugin.zoom.lens.ZoomLensPlugin;
import org.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.Portfolio;
import org.jensoft.core.view.View;
import org.jensoft.core.view.background.ViewDarkBackground;
import org.jensoft.core.view.background.ViewDefaultBackground;
import org.jensoft.core.plugin.stripe.StripePlugin;
import org.jensoft.core.plugin.grid.GridPlugin;
@JenSoftView(background=ViewDarkBackground.class,description="Show ho to use function peak tracker")
public class FunctionPeakTrackerSample extends View {

	private static final long serialVersionUID = 1449242736824739154L;

	@Portfolio(name = "PeakTrackerDemo", width = 800, height = 600)
	public static View getPortofolio() {
		FunctionPeakTrackerSample demo = new FunctionPeakTrackerSample();

		ViewDefaultBackground viewBackground = new ViewDefaultBackground();
		Shader s = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
		viewBackground.setShader(s);
		viewBackground.setOutlineStroke(new BasicStroke(2.5f));

		demo.setBackgroundPainter(viewBackground);
		return demo;
	}

	public FunctionPeakTrackerSample() {

		super();
		setPlaceHolder(40);

		// proj projection
		Projection proj = new Projection.Linear(0, 10, 0, 18);
		registerProjection(proj);

		Font font = new Font("lucida console", Font.PLAIN, 10);

		// create modeled axis plug-in in south part
		AxisMetricsPlugin.ModeledMetrics southMetrics = new AxisMetricsPlugin.ModeledMetrics.S();
		proj.registerPlugin(southMetrics);
		southMetrics.setTextFont(font);
		

		// create modeled axis plug-in in west part
		AxisMetricsPlugin.ModeledMetrics westMetrics = new AxisMetricsPlugin.ModeledMetrics.W();
		proj.registerPlugin(westMetrics);
		westMetrics.setTextFont(font);
		

		CurveFunction curveFunctions = new CurveFunction();
		proj.registerPlugin(curveFunctions);

		double[] xValues1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double[] yValues1 = { 6, 1.8, 15, 1.9, 3.4, 6.1, 4.2, 8.5, 9.9, 12, 8 };

		double[] xValues2 = { 0, 1, 2, 3, 4, 5.2, 6, 7, 8, 9, 10 };
		double[] yValues2 = { 3, 1, 5, 4, 4.8, 7.3, 2, 3, 7, 10, 6 };

		double[] xValues3 = { 0, 1.4, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double[] yValues3 = { 0.4, 7.5, 5, 1, 2.8, 5, 7, 9, 2, 4, 1 };

		// source function
		UserSourceFunction source1 = new UserSourceFunction.SplineSource(xValues1, yValues1, 0.1);
		UserSourceFunction source2 = new UserSourceFunction.SplineSource(xValues2, yValues2, 0.1);
		UserSourceFunction source3 = new UserSourceFunction.SplineSource(xValues3, yValues3, 0.1);

		PeakTrackerPlugin peakTracker = new PeakTrackerPlugin();
		peakTracker.registerContext(new PeakTrackerDeviceContext());
		peakTracker.registerSource(source1);
		peakTracker.registerSource(source2);
		peakTracker.registerSource(source3);

		proj.registerPlugin(peakTracker);

		final SourceTrackerPlugin curveTrackerPlugin = new SourceTrackerPlugin();
		curveTrackerPlugin.registerSourceFunction(source1);
		curveTrackerPlugin.registerSourceFunction(source2);
		curveTrackerPlugin.registerSourceFunction(source3);

		curveTrackerPlugin.addSourceTrackerListener(new SourceTrackerListener() {

			@Override
			public void sourceTracked(SourceTrackerEvent event) {
				System.out.println("serieTracked ::" + event.getSourceFunction().getName());
			}

			@Override
			public void sourceRegistered(SourceTrackerEvent event) {
				System.out.println("serieRegistered ::" + event.getSourceFunction().getName());
			}

			@Override
			public void currentTrack(SourceTrackerEvent event) {
				System.out.println("serieTracked ::" + event.getSourceFunction().getName());
				Point2D devicePoint = curveTrackerPlugin.getCurrentTrackDevice();
				Point2D userPoint = curveTrackerPlugin.getCurrentTrackUser();
				System.out.println("serieTracked device::" + devicePoint);
				System.out.println("serieTracked user::" + userPoint);
			}
		});

		curveTrackerPlugin.registerContext(new SourceTrackerDeviceContext());
		proj.registerPlugin(curveTrackerPlugin);

		// create curves
		Curve curve1 = FunctionToolkit.createCurveFunction(source1, PetalPalette.PETAL1_HC, new CurveDefaultDraw(new BasicStroke(2f)));
		Curve curve2 = FunctionToolkit.createCurveFunction(source2, PetalPalette.PETAL2_HC, new CurveDefaultDraw(new BasicStroke(2f)));
		Curve curve3 = FunctionToolkit.createCurveFunction(source3, PetalPalette.PETAL3_HC, new CurveDefaultDraw(new BasicStroke(2f)));

		// add curves in curve view
		curveFunctions.addFunction(curve1);
		curveFunctions.addFunction(curve2);
		curveFunctions.addFunction(curve3);

		Font f =  new Font("Dialog", Font.PLAIN, 14);
		
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(2.5);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel("2.5");
		metric.setDivergence(10);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, PetalPalette.PETAL1_HC));
		metric.setGlyphMetricMarkerPainter(new RoundMarker(PetalPalette.PETAL1_HC, Color.white));
		metric.setFont(f);
		curve1.addMetricsLabel(metric);

		metric = new GlyphMetric();
		metric.setValue(1.5);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel("1.5");
		metric.setDivergence(10);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, PetalPalette.PETAL2_HC));
		metric.setGlyphMetricMarkerPainter(new RoundMarker(PetalPalette.PETAL2_HC, Color.white));
		metric.setFont(f);
		curve2.addMetricsLabel(metric);

		metric = new GlyphMetric();
		metric.setValue(5.6);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel("5.6");
		metric.setDivergence(10);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, PetalPalette.PETAL3_HC));
		metric.setGlyphMetricMarkerPainter(new RoundMarker(PetalPalette.PETAL3_HC, Color.white));
		metric.setFont(f);
		curve3.addMetricsLabel(metric);

		metric = new GlyphMetric();
		metric.setValue(8.2);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("8.2");
		metric.setDivergence(10);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, PetalPalette.PETAL2_HC));
		metric.setGlyphMetricMarkerPainter(new RoundMarker(PetalPalette.PETAL2_HC, Color.white));
		metric.setFont(f);
		curve2.addMetricsLabel(metric);

		// zoom and translate tool plugin
		ZoomBoxPlugin zoomPlugin = new ZoomBoxPlugin();
		zoomPlugin.registerContext(new ZoomBoxDefaultDeviceContext());
		proj.registerPlugin(zoomPlugin);

		TranslatePlugin translatePlugin = new TranslatePlugin();
		translatePlugin.registerContext(new TranslateDefaultDeviceContext());
		proj.registerPlugin(translatePlugin);

		ZoomWheelPlugin wheelPlugin = new ZoomWheelPlugin();
		proj.registerPlugin(wheelPlugin);

		ZoomLensPlugin objectif = new ZoomLensPlugin();
		objectif.registerContext(new LensDefaultDeviceContext());
		objectif.registerWidget(new LensX());
		objectif.registerWidget(new LensY());
		proj.registerPlugin(objectif);

		// decoration a night gradient (dark blue to black)
		DeviceGradientBackgroundPlugin night = new DeviceNightBackground();
		night.setAlpha(1f);
		// view.registerPlugin(night);

		final MessagePlugin messagePlugin = new MessagePlugin();
		proj.registerPlugin(messagePlugin);

		// messagePlugin.registerWidget(new MessageSelecterWidget());
		// messagePlugin.registerContext(new MessageDefaultDeviceContext());
		final Message msg = new Message("Peak tracker", "This demo shows the peak tracker plug in.\n" + "The peak tracker allows to track peak within a serie. \n" + "Right click on device and lock selected the peak tool.\nWhen the peak tracker is selected, return in \ndevice context menu and select a serie to track.");
		messagePlugin.registerMessage(msg);
		Font f12 =  new Font("Dialog", Font.PLAIN, 12);
		msg.setMessageFont(f12);
		msg.setMessageDimension(320, 180);
		msg.setMessageForeground(Color.WHITE);
		msg.setScrollerColor(RosePalette.MELON.brighter());
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					// messagePlugin.lockSelected();
					messagePlugin.showVolatileMessage(msg);
				} catch (InterruptedException e) {
				}
			}
		};
		t.start();

		MultiplierStripe stripePlugin = new StripePlugin.MultiplierStripe.H(0, 2.5);
		StripePalette bp = new StripePalette();
		bp.addPaint(new Color(255, 255, 255, 40));
		bp.addPaint(new Color(40, 40, 40, 40));
		stripePlugin.setStripePalette(bp);
		stripePlugin.setAlpha(0.3f);
		proj.registerPlugin(stripePlugin);

		MultiplierGrid grids = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
		grids.setGridColor(new Color(59, 89, 152, 100));
		proj.registerPlugin(grids);

		MultiplierGrid grids2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
		grids2.setGridColor(new Color(59, 89, 152, 100));
		proj.registerPlugin(grids2);
	}
}
