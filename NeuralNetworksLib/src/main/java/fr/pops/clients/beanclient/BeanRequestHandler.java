package fr.pops.clients.beanclient;

import fr.pops.beans.bean.Bean;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquest.beanrequests.CreateBeanRequest;
import fr.pops.sockets.resquest.beanrequests.DeleteBeanRequest;
import fr.pops.sockets.resquesthandler.RequestHandler;

public class BeanRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public BeanRequestHandler(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Process the request
     * Not every request has to be processed depending on the client
     *
     * @param request The request to process
     */
    @Override
    protected void process(Request request) {

        switch (request.getType()){
            case CREATE_BEAN:
                this.createBeanHandling((CreateBeanRequest) request);
                break;
            case DELETE_BEAN:
                this.deleteBeanHandling((DeleteBeanRequest) request);
                break;
        }
    }

    /**
     * Select the next operation to perform with the request
     * @param request The request to handle
     * @return The operation to perform next:
     *         Either NONE, WRITE_BACK
     */
    public EnumCst.RequestOperations selectNextOperation(Request request){
        // Init
        EnumCst.RequestOperations operation;

        // Select next operation
        switch (request.getType()){
            case CREATE_BEAN:
                operation = EnumCst.RequestOperations.WRITE_BACK;
                break;
            case DELETE_BEAN:
                operation = EnumCst.RequestOperations.WRITE_BACK;
                break;
            default:
                operation = EnumCst.RequestOperations.NONE;
                break;
        }
        return operation;
    }

    /*****************************************
     *
     * Requests handling
     *
     *****************************************/
    /**
     * Handle the creation of a bean
     * @param request The request to handle
     */
    private void createBeanHandling(CreateBeanRequest request){
        Bean bean = Client.getInstance().onBeanCreationEvent(request.getBeanTypeId());
        if (bean != null){
            request.setBeanId(bean.getId());
        } else {
            System.out.println("Requested creation of a bean of type: " + request.getBeanTypeId() + "couldn't proceed.");
        }
    }

    /**
     * Handle the deletion of a bean
     * @param request The request to handle
     */
    private void deleteBeanHandling(DeleteBeanRequest request){
        boolean deleted = Client.getInstance().onBeanDeletionEvent(request.getBeanId());
        if (!deleted) System.out.println("Error occurred when trying to delete the bean: " + request.getBeanId());
        request.setBeanDeleted(deleted);
    }
}
