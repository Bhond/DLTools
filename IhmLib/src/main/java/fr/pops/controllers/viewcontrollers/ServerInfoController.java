package fr.pops.controllers.viewcontrollers;

import fr.pops.viewmodels.ServerInfoModel;
import fr.pops.views.ServerInfoView;

public class ServerInfoController extends BaseController<ServerInfoView, ServerInfoModel>{

    public ServerInfoController(ServerInfoView view){
        super(view);

        this.onInit(new ServerInfoModel(this));
    }

    public void setPingValue(double value){
        this.view.setPingValue(value);
    }

    @Override
    protected void onInit(ServerInfoModel model) {
        super.onInit(model);
    }
}
