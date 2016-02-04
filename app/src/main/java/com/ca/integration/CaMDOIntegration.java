/**
 * Copyright (C) 2015, CA.  All rights reserved.
 */
package com.ca.integration;

import android.content.BroadcastReceiver;
import android.webkit.JavascriptInterface;

import java.util.Map;

/**
 * Created by Suman Cherukuri on 3/25/15.
 * Chaitanya Kannali - Updated 6/26/15 - 15.2.
 */

/**
 * Integrate this class in your project to send custom events and transactions.  When the app is wrapped
 * in MAA container, these methods will re-directed to corresponding methods in MAA Wrapper
 *
 * @author  Suman Cherukuri
 * @version 1.0, 25 March 20015
 */
public class CaMDOIntegration {
    public static final String CAMAA_HEADER_ZIPCODE = "zp";
    public static final String CAMAA_HEADER_COUNTRY = "ct";
    public static final String CAMAA_CUSTOMER_ID = "customerId";
    public static final String CAMAA_CRASH_OCCURRED = "crashOccured";
    public static final String CAMAA_DOUBLE= "double";
    public static final String CAMAA_STRING = "string";
    public static final String CAMAA_CUSTOM = "custom";


    /**
     * Set the location of the device
     * @Deprecated use setCustomerLocation(String zipCode,String countryCode )
     */
    public static void setCustomerLocation(Map<String, String> customerLocation){
    }


    /***
     * Set the locaiton of device
     * @param zipCode
     * @param countryCode
     */
    public static void setCustomerLocation(String zipCode,String countryCode ){

    }
    /**
     * Set whether to opt put of data transmission from the device to the MAA Servers
     * @param optOut <code>true</code> to opt out.  <code>false</code> to keep sending the data
     */
    public static void setOptOut(boolean optOut) {
    }

    /**
     * Send a new session event
     * @param type event type valid values are CAMAA_DOUBLE , CAMAA_STRING
     * @param key event key , name of event.
     * @param value event value
     */
    public static void addSessionEvent(String type, String key, String value) {
    }

    /**
     * Send a new session event with additional attributes
     * @param type event type valid values are CAMAA_DOUBLE , CAMAA_STRING
     * @param key event key , name of event.
     * @param value event value
     * @param attributes <code>java.util.Map</code> of key-value pairs
     */
    public static void addSessionEvent(String type, String key, String value,
                                       Map<String, String> attributes) {
    }



    /**
     * Set session information with the value.  If type is CAMAA_CUSTOMER_ID, deviceID is replaced
     * by the 'value' provided
     * @param type Session info type.  Usually CAMAA_CUSTOMER_ID, CAMAA_DOUBLE , CAMAA_STRING.
     * @param key event key :Ignored if type is CAMAA_CUSTOMER_ID
     * @param value value to use for the type
     */
    public static void setSessionInfo(String type, String key, String value) {
    }

    /**
     * Starts a new application transaction that bounds all the subsequent events.  Application name
     * is used as the service name
     * @param transactionName name of the transaction
     * @return returns <code>boolean</code>.  <code>true</code> for success and <code>false</code> for failure
     */
    public static boolean startApplicationTransaction(String transactionName) {
        return true;
    }

    /**
     * Starts a new application transaction that bounds all the subsequent events
     * @param transactionName name of the transaction
     * @param serviceName name of the service
     * @return returns <code>boolean</code>.  <code>true</code> for success and <code>false</code> for failure
     */
    public static boolean startApplicationTransaction(String transactionName, String serviceName) {
        return true;
    }

    /**
     * @deprecated
     */
    public static boolean stopApplicationTransaction() {
        return true;
    }

    /**
     * Stops the application transaction.  Subsequent events will be part of the previous transaction
     * if there is one
     * @param transactionName name of the transaction
     * @pram serviceName name of the service
     *                pass a brief description about the failure
     * @return
     */
    public static boolean stopApplicationTransaction(String transactionName, String serviceName) {
        return true;
    }

    /**
     * Stops the application transaction.  Subsequent events will be part of the previous transaction
     * if there is one
     * @param transactionName name of the transaction
     * @pram serviceName name of the service
     * @param failure pass <code>null</code> for a successful transaction.  If it is a failed transaction
     *                pass a brief description about the failure
     * @return
     */
    public static boolean stopApplicationTransaction(String transactionName,
                                                     String serviceName, String failure) {
        return true;
    }

    /**
     * receive crash notifications through the reeiver
     * @param messageReceiver <code>android.content.BroadcastReceiver</code> to receive the callbacks
     */
    public static void registerAppFeedBack(BroadcastReceiver messageReceiver) {
    }

    /**
     * sets the feedback from the user about a crash
     * @param feedback
     */
    public static void setCustomerFeedback(String feedback) {
    }


    /***
     * Enable SDK if its not enabled.
     *
     * When SDK is enabled, sdk will collect data for analytics.
     *
     */
    public static void enableSDK(){
    }

    /**
     * Disables SDK if its enabled.
     * When SDK is disabled, SDK will not intercept any calls and wont collect any data from App.
     */
    public static void disableSDK(){
    }

    /**
     * Checks if SDK is enabled or not
     */
    public static boolean isSDKEnabled(){
        return true;
    }

    /***
     * In Private Zone screenshots and other sensitive information will not be recorded
     */
    public static void enterPrivateZone(){

    }

    /***
     * Exiting private zone
     */
    public static void exitPrivateZone(){

    }

    /***
     * Checks if app is in private zone state.
     * @return
     */
    public static boolean isInPrivateZone(){
        return false;
    }

    /***
     * Takes screenshot of current screen and adds an event to analytics.
     *
     */
    public static void takeScreenshot(){
    }
}
