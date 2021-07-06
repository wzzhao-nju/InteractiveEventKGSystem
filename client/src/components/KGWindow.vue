<template>
  <div class="KGWindow">
    <div id="window"></div>
  </div>
</template>

<script>
import NeoVis from 'neovis.js';

export default {
    name: 'KGWindow',
    methods: {
        draw:function(){
			var config = {
			container_id: "window",
			server_url: "bolt://localhost:7687",
			server_user: "neo4j",
			server_password: "zwz12345",
			labels: {
				//"Character": "name",
				"Character": {
					"caption": "name",
					"size": "pagerank",
					"community": "community",
					"title_properties": [
						"name",
						"pagerank"
					]
				}
			},
			relationships: {
				"INTERACTS": {
					"thickness": "weight",
					"caption": false
				}
			},
			initial_cypher: "MATCH (n)-[r:INTERACTS]->(m) RETURN n,r,m"
			};
			var window = new NeoVis(config);
			window.render();
			console.log(window);
		}
    },
    mounted(){
		this.draw();
	}
}
</script>

<style scoped>
#window{
  width: 900px;
  height: 700px;
  border: 1px solid lightgray;
  font: 22pt arial;
}
</style>