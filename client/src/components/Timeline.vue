<template>
    <div>
        <div>
            <h2>Event</h2>
            <div id="event-timeline"></div>
            <h2>Document</h2>
            <div id="document-timeline"></div>
        </div>
    </div>
</template>

<script>
import vis from 'vis'

export default {
    data() {
        return {
            items: [],
            eventTimeline: {},
            documentTImeline: {},
        }
    },
    methods: {
        drawEvent() {
            var container = document.getElementById('event-timeline');
            var items = new vis.DataSet([
                {id: 1, content: "die", start: "2010-05-06"},
                {id: 2, content: "riots", start: "2010-05-05"},
                {id: 3, content: "responded", start: "2010-04-06"},
                {id: 4, content: "protest", start: "2011-01-08"},
                {id: 5, content: "attempted", start: "2010-07-19"}
            ]);
            /*
            this.$axios.post(
                "http://localhost:8088/api/getEventTimeline"
            )
            .then((response) => {
                items.update(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
            */
            var options = {};
            this.eventTimeline = new vis.Timeline(container, items, options);
        },
        drawDocument() {
            var container = document.getElementById('document-timeline');
            //this.items = new vis.DataSet();
            this.items = new vis.DataSet([
                {id: 1, content: "Cheeks returns as Sixers' fixer Jim O'Brien was shown the door after a single tumultuous season", start: "2005-05-24"},
                {id: 2, content: "Wladimir Klitschko retains belts", start: "2012-07-07"},
                {id: 3, content: "Three die in bank during Greek riots", start: "2010-05-05"},
                {id: 4, content: "Navy foils Somali pirate attack off Gulf of Aden", start: "2011-11-11"},
                {id: 5, content: "Waitrose fire in Wellington town centre tackled", start: "2013-07-21"},
                {id: 6, content: "Brooklyn Neighborhood Is 'A Powder Keg' After Teen Killed By Police", start: "2013-05-14"}
            ]);
            //this.items.add([{id: 1, content: "item 1", start:"2021"}]);
            /*
            this.$axios.post(
                "http://localhost:8088/api/getDocumentTimeline"
            )
            .then((response) => {
                var temp = response.data;
                this.items.add(temp);
            })
            .catch((error) => {
                console.log(error);
            });
            */
            var options = {};
            this.documentTImeline = new vis.Timeline(container, this.items, options);
            //new vis.Timeline(container, items, options);

            this.documentTImeline.on('select', (items) => {
                console.log(items);
            })
        },
    },
    mounted() {
        this.drawEvent();
        this.drawDocument();
    } 
}
</script>

<style scoped>
@import './vis-timeline-graph2d.min.css';

body, html {
    font-family: sans-serif;
}
h2 {
    text-align: center;
}
#timeline {
    border: 1px solid lightgray;
    position: relative;
}
</style>