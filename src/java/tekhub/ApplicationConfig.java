/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekhub;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author kulartist
 */
@javax.ws.rs.ApplicationPath("webcall")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(tekhub.AdminResource.class);
        resources.add(tekhub.DatabaseConnection.class);
        resources.add(tekhub.FeedbackResource.class);
        resources.add(tekhub.ItemResource.class);
        resources.add(tekhub.OrdersResource.class);
        resources.add(tekhub.UserResource.class);
        resources.add(tekhub.WaitingItemResource.class);
    }
    
}
