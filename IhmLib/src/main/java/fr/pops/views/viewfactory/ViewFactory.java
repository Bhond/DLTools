package fr.pops.views.viewfactory;

import fr.pops.cst.EnumCst;
import fr.pops.views.base.BaseView;
import fr.pops.views.serverinfo.NetworkInfoView;
import fr.pops.views.stock.StockView;
import javafx.stage.Stage;

public abstract class ViewFactory {

    /**
     * Get view from stage and a type in String format
     * @param stage The stage needed by the constructor
     * @param typeStr The type of the in a string format
     * @return The view created corresponding to the input type
     */
    public static BaseView<?> get(Stage stage, String typeStr){
        EnumCst.Views type = EnumCst.Views.valueOf(typeStr);
        return ViewFactory.get(stage, type);
    }

    /**
     * Get view from stage and a type
     * @param stage The stage needed by the constructor
     * @param type The type of the view
     * @return The view created corresponding to the input type
     */
    public static BaseView<?> get(Stage stage, EnumCst.Views type){
        switch(type){
            case NETWORK_INFO:
                return new NetworkInfoView(stage);
            case STOCK:
                return new StockView(stage);
            default:
                return null;
        }
    }

}