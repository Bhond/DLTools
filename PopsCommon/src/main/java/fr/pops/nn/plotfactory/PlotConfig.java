package fr.pops.nn.plotfactory;

import fr.pops.nn.popscst.cst.IntCst;

public enum PlotConfig {

    // Defined plots
    LinePlot(IntCst.DEFAULT_PLOT_WINDOW_HEIGHT, IntCst.DEFAULT_PLOT_WINDOW_WIDTH);

    // Sizes
    private int m_mainWindowHeight;
    private int m_mainWindowWidth;

    // Ctor
    PlotConfig(int height, int width){
        this.m_mainWindowHeight = height;
        this.m_mainWindowWidth = width;
    }

    // Getter
    public int getWindowHeight(){return this.m_mainWindowHeight;}

    public int getWindowWidth(){return this.m_mainWindowWidth;}
}
