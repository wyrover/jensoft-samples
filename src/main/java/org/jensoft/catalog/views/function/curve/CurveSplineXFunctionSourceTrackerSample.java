/*
 * JenSoft API - Charting Framework
 * http://www.jensoftapi.com
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package org.jensoft.catalog.views.function.curve;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.jensoft.core.catalog.nature.JenSoftView;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.palette.color.FilPalette;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.function.FunctionPlugin.CurveFunction;
import org.jensoft.core.plugin.function.FunctionToolkit;
import org.jensoft.core.plugin.function.curve.Curve;
import org.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import org.jensoft.core.plugin.function.source.UserSourceFunction;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerDeviceContext;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerPlugin;
import org.jensoft.core.plugin.grid.Grid.GridOrientation;
import org.jensoft.core.plugin.grid.GridPlugin;
import org.jensoft.core.plugin.grid.GridPlugin.MultiplierGrid;
import org.jensoft.core.plugin.legend.title.TitleLegend;
import org.jensoft.core.plugin.legend.title.TitleLegendConstraints;
import org.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendAlignment;
import org.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendPosition;
import org.jensoft.core.plugin.legend.title.painter.fil.TitleLegendGradientFill;
import org.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import org.jensoft.core.plugin.outline.OutlinePlugin;
import org.jensoft.core.plugin.stripe.StripePlugin;
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
import org.jensoft.core.view.View;
import org.jensoft.core.view.background.ViewDarkBackground;
/**
 * <code>CurveSplineXFunctionSourceTrackerSample</code>
 * 
 * @author Sébastien Janaud
 */
@JenSoftView(background=ViewDarkBackground.class,description="Show how to use curve spline x function with source tracker")
public class CurveSplineXFunctionSourceTrackerSample extends View {

	private static final long serialVersionUID = -8232357329559371621L;

	/**
	 * create curve function sample view
	 */
	public CurveSplineXFunctionSourceTrackerSample() {
		super();
		setPlaceHolder(40);

		// proj projection
		Projection proj = new Projection.Linear(-1, 12.5, -8, 18.5);
		registerProjection(proj);
		proj.setThemeColor(RosePalette.LEMONPEEL);

		// device outline plug-in
		proj.registerPlugin(new OutlinePlugin());

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

		// sources
		double[] xValues1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double[] yValues1 = { 0, 1, 15, 1, 8, 1, 4, 1, 2, 1, 0 };

		double[] xValues2 = { 0, 1.2, 2.2, 3.2, 4.2, 5.2, 6.2, 7.2, 8.2, 9.2, 10.2 };
		double[] yValues2 = { 0, 1, 14.5, 1, 7.5, 1, 3.5, 1, 1.5, 1, 0 };

		double[] xValues3 = { 0, 1.4, 2.4, 3.4, 4.4, 5.4, 6.4, 7.4, 8.4, 9.4, 10.4 };
		double[] yValues3 = { 0, 1, 14, 1, 7, 1, 3, 1, 1, 1, 0 };

		UserSourceFunction source1 = new UserSourceFunction.SplineSource(xValues1, yValues1, 0.1);
		UserSourceFunction source2 = new UserSourceFunction.SplineSource(xValues2, yValues2, 0.1);
		UserSourceFunction source3 = new UserSourceFunction.LineSource(xValues3, yValues3);
		UserSourceFunction source4 = new UserSourceFunction.SplineSource(xValues3, yValues3, 0.1);
		
		// source tracker
		final SourceTrackerPlugin sourceTracker = new SourceTrackerPlugin();
		sourceTracker.registerSourceFunction(source1);
		sourceTracker.registerSourceFunction(source2);
		sourceTracker.registerSourceFunction(source3);
		sourceTracker.registerContext(new SourceTrackerDeviceContext());
		proj.registerPlugin(sourceTracker);
		sourceTracker.setGlyphMetricsFill(new GlyphFill(Color.WHITE, FilPalette.PINK1));

		// curve function
		Curve curve1 = FunctionToolkit.createCurveFunction(source1, RosePalette.CALYPSOBLUE.brighter(), new BasicStroke(1.5f));
		Curve curve2 = FunctionToolkit.createCurveFunction(source2, RosePalette.LEAFGREEN.brighter(), new CurveDefaultDraw());
		Curve curve3 = FunctionToolkit.createCurveFunction(source3, RosePalette.FOXGLOWE.brighter(), new CurveDefaultDraw());
		Curve curve4 = FunctionToolkit.createCurveFunction(source4, RosePalette.MANDARIN.brighter(), new CurveDefaultDraw());
		curveFunctions.addFunction(curve1);
		curveFunctions.addFunction(curve2);
		curveFunctions.addFunction(curve3);
		curveFunctions.addFunction(curve4);

		// legend
		Font f =  new Font("Dialog", Font.PLAIN, 12);
		TitleLegend legend = new TitleLegend("Multiple Curves");
		legend.setFont(f);
		legend.setLegendFill(new TitleLegendGradientFill(NanoChromatique.BLUE, RosePalette.AEGEANBLUE));
		legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f, LegendAlignment.Rigth));

		// zoom wheel
		ZoomWheelPlugin wheelPlugin = new ZoomWheelPlugin();
		proj.registerPlugin(wheelPlugin);

		// zooms box
		ZoomBoxPlugin zoomPlugin = new ZoomBoxPlugin();
		zoomPlugin.registerContext(new ZoomBoxDefaultDeviceContext());
		proj.registerPlugin(zoomPlugin);

		// zoom lens
		ZoomLensPlugin lensPlugin = new ZoomLensPlugin();
		lensPlugin.registerContext(new LensDefaultDeviceContext());
		// create two objectif for x and y dimension
		LensX ox = new LensX();
		LensY oy = new LensY();
		// register widget in zoom objectif plugin
		lensPlugin.registerWidget(ox);
		lensPlugin.registerWidget(oy);
		proj.registerPlugin(lensPlugin);

		ox.setOutlineColor(Color.BLACK);
		ox.setButton1DrawColor(RosePalette.CALYPSOBLUE);
		ox.setButton2DrawColor(RosePalette.CALYPSOBLUE);
		oy.setOutlineColor(Color.BLACK);
		oy.setButton1DrawColor(RosePalette.CALYPSOBLUE);
		oy.setButton2DrawColor(RosePalette.CALYPSOBLUE);

		// translate
		TranslatePlugin translatePlugin = new TranslatePlugin();
		translatePlugin.registerContext(new TranslateDefaultDeviceContext());
		proj.registerPlugin(translatePlugin);

		// stripe
		MultiplierStripe stripePlugin = new StripePlugin.MultiplierStripe.H(0, 2.5);
		StripePalette bp = new StripePalette();
		bp.addPaint(new Color(255, 255, 255, 5));
		bp.addPaint(ColorPalette.alpha(FilPalette.GREEN2, 20));
		stripePlugin.setStripePalette(bp);
		stripePlugin.setAlpha(0.3f);
		proj.registerPlugin(stripePlugin);

		// grid
		MultiplierGrid gridLayout = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
		gridLayout.setGridColor(new Color(255, 255, 255, 60));
		proj.registerPlugin(gridLayout);

		MultiplierGrid gridLayout2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
		gridLayout2.setGridColor(new Color(255, 255, 255, 60));
		proj.registerPlugin(gridLayout2);
	}
}
