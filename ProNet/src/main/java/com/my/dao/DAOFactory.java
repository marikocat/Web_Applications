package com.my.dao;

/**
 * Abstract factory to get implementation of DAOFactory.
 * Switching to another DB is done with the help of this class.
 */
public abstract class DAOFactory {
    /**
     * Enclosed field that contains DAOFactory object.
     */
    private static DAOFactory instance;

    /**
     * Method of getting DAOFactory object dependent on factory configuration.
     * @return DAOFactory implementation instance,
     * name of which is in {@link #daoFactoryFCN}
     */
    public static synchronized DAOFactory getInstance() throws Exception {
        if (instance == null) {
            Class<?> clazz = Class.forName(DAOFactory.daoFactoryFCN);
            instance = (DAOFactory) clazz.getDeclaredConstructor().newInstance();
        }
        return instance;
    }

    /**
     * Constructor to have the possibility to inherit from this class.
     */
    protected DAOFactory() {

    }

    /**
     * Enclosed field that contains fully qualified name of class,
     * which object returns {@link #getInstance()}
     */
    private static String daoFactoryFCN;

    /**
     * Method to set DAOFactory configuration.
     */
    public static void setDAOFactoryFCN(String daoFactoryFCN) {
        instance = null;
        DAOFactory.daoFactoryFCN = daoFactoryFCN;
    }


    /**
     * Method of getting DAO for User entity
     * @return UserDAO implementation, which is determined by factory configuration
     * {@link DAOFactory}
     */
    public abstract UserDAO getUserDAO();

    /**
     * Method of getting DAO for Account entity
     * @return AccountDAO implementation, which is determined by factory configuration
     * {@link DAOFactory}
     */
    public abstract AccountDAO getAccountDAO();

    /**
     * Method of getting DAO for Service entity
     * @return ServiceDAO implementation, which is determined by factory configuration
     * {@link DAOFactory}
     */
    public abstract ServiceDAO getServiceDAO();
}
