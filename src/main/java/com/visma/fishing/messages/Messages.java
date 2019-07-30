package com.visma.fishing.messages;

import org.slf4j.helpers.MessageFormatter;

public class Messages {
    public static final String LOGBOOK_SAVE_SUCCESS_MSG = "Successfully saved logbook with id {} to database";
    public static final String LOGBOOK_SAVE_FILESYSTEM_SUCCESS_MSG = "Successfully saved logbook to filesystem.";
    public static final String LOGBOOK_UPDATE_SUCCESS_MSG = "Successfully updated logbook with id {}.";
    public static final String LOGBOOK_REMOVED_SUCCESS_MSG = "Removed logbook with id {}.";
    public static final String LOGBOOK_FIND_FAILED_MSG = "Could not find logbook with id {}.";
    public static final String LOGBOOK_CONCURRENT_CHANGES_MSG = "Failed to update Logbook with id {} due to concurrent modification.";

    public static final String END_OF_FISHING_UPDATE_SUCCESS_MSG = "Successfully updated endOfFishing with id {}";
    public static final String END_OF_FISHING_REMOVED_SUCCESS_MSG = "Removed endOfFishing with id {}";
    public static final String END_OF_FISHING_SAVE_SUCCESS_MSG = "Successfully saved endOfFishing {}";
    public static final String END_OF_FISHING_FIND_FAILED_MSG = "Could not find endOfFishing with id {}";

    public static final String DEPARTURE_SAVE_SUCCESS_MSG = "Successfully saved departure with id {}";
    public static final String DEPARTURE_UPDATE_SUCCESS_MSG = "Successfully updated departure with id {}";
    public static final String DEPARTURE_REMOVED_SUCCESS_MSG = "Removed departure with id {}";
    public static final String DEPARTURE_FIND_FAILED_MSG = "Could not find departure with id {}";

    public static final String CATCH_SAVE_SUCCESS_MSG = "Successfully saved catch with id {}";
    public static final String CATCH_UPDATE_SUCCESS_MSG = "Successfully updated catch with id {}";
    public static final String CATCH_REMOVED_SUCCESS_MSG = "Removed catch with id {}";
    public static final String CATCH_FIND_FAILED_MSG = "Could not find catch with id {}";

    public static final String ARRIVAL_SAVE_SUCCESS_MSG = "Successfully saved arrival with id {}";
    public static final String ARRIVAL_UPDATE_SUCCESS_MSG = "Successfully updated arrival with id {}";
    public static final String ARRIVAL_REMOVED_SUCCESS_MSG = "Removed arrival with id {}";
    public static final String ARRIVAL_FIND_FAILED_MSG = "Could not find arrival with id {}";

    public static final String CONFIGURATION_SAVE_SUCCESS_MSG = "Successfully saved configuration with key {}";
    public static final String CONFIGURATION_UPDATE_SUCCESS_MSG = "Successfully updated configuration with key {}";
    public static final String CONFIGURATION_REMOVED_SUCCESS_MSG = "Removed configuration with key {}";
    public static final String CONFIGURATION_FIND_FAILED_MSG = "Could not find configuration with key {}";

    private Messages() {

    }

    public static String format(String msg, Object... objs) {
        return MessageFormatter.arrayFormat(msg, objs).getMessage();
    }

}
