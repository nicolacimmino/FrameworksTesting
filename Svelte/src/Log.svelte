<script>
    import {onDestroy} from "svelte";
    import Log from "./Log.js"

    var parsedLogContent = "";

    const unsubscribe = Log.logEntries.subscribe((logEntries) => {
        parsedLogContent = logEntries.reduceRight((reduced, logEntry) => {
            return reduced + logEntry.time + " - " + logEntry.message + "<br/>";
        }, "")
    });

    onDestroy(unsubscribe);
</script>

<div class="terminal">
    {@html parsedLogContent}
</div>
<button on:click={Log.clear}>Clear Log</button>

<style>
    .terminal {
        font-family: monospace;
        background-color: black;
        text-decoration: none;
        color: green;
        font-size: 1.5em;
        height: 200px;
        overflow: scroll;
        padding: 5px;
    }
</style>