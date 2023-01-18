import {writable} from "svelte/store";
import moment from "moment"

export default class Log {

    // Initialise with values from the local storage, if any.
    static logEntries = writable(JSON.parse(localStorage.getItem("logEntries")) || []);

    static add(message) {
        Log.logEntries.update((entries) => {

            var logEntry = {
                "time": moment().toISOString(),
                "message": message
            };

            return [...entries, logEntry];
        });
    }

    static clear() {
        localStorage.setItem("logEntries", JSON.stringify([]))

        Log.logEntries.update(() => {
            return [];
        });
    }
}

// Persist the log in the local storage.
Log.logEntries.subscribe((entries) => localStorage.setItem("logEntries", JSON.stringify(entries)));

