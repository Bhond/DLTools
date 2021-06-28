/*******************************************************************************
 *
 *                         PPPP     OOOO     PPPP    SSSS
 *                        PP  PP   OO  OO   PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP   SSSS
 *                        PPPP    OO    OO  PPPP        SS
 *                        PP       OO  OO   PP          SS
 *                        PP        OOOO    PP       SSSS
 *
 * Name: EnumCst.java
 *
 * Description: Common enums shared by sockets.
 *
 * Author: Charles MERINO
 *
 * Date: 02/11/2019
 *
 ******************************************************************************/
package fr.pops.sockets.cst;

public abstract class EnumCst {

    /*****************************************
     *
     * Available client types
     *
     *****************************************/
    public enum ClientTypes {
        // Client types
        DEFAULT(0L),
        IHM(  7596087841016572405L),
        STOCK(390208358890556977L),
        BEAN(32482398725739523L);

        // Attributes
        private long id;

        /**
         * Ctor
         * Convenient to store the client's id
         * inside the enum
         * @param id
         */
        private ClientTypes(long id){
            this.id = id;
        }

        /**
         * @return The client's ID
         */
        public long getId() {
            return id;
        }

        /**
         * Find out witch type is associated with the input id
         * @param id The id of the client to look for
         * @return The type of the client corresponding to the given id
         */
        public static ClientTypes getType(long id){
            ClientTypes type = null;
            for (ClientTypes t : ClientTypes.values()){
                if (t.id == id){
                    type = t;
                    break;
                }
            }
            return type;
        }
    }

    /*****************************************
     *
     * Request types
     *
     *****************************************/
    public enum RequestTypes { ERROR,
        AUTHENTICATE,
        DISCONNECTION,
        PING,
        GET_NETWORK_INFO,
        GET_CURRENT_STOCK_INFO,
        GET_MNIST_IMAGE,
        GET_MNIST_CONFIGURATION,
        CREATE_BEAN,
        DELETE_BEAN,
        UPDATE_BEAN_PROPERTY}

    /*****************************************
     *
     * Request operations
     *
     *****************************************/
    public enum RequestOperations { NONE, WRITE_BACK, TRANSFER }

}
