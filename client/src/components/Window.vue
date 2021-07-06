<template>
    <div class="Window">
        <div id="canvas"></div>
        <div id="detailbox">
            <el-card class="detailcard" shadow="hover" v-show="showNodecard">
                <div slot="header">
                    <span>详细信息</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="Delete">隐藏节点</el-button>
                </div>
                <h3>Type</h3>
                <el-row>
                    <el-button size="small" type="primary" round plain>{{ allNodesType[clickedNode.group] }}</el-button>
                </el-row>
                <el-divider class="divider"></el-divider>
                <h3>Properties</h3>
                <el-row>
                    <el-badge v-for="(value, name) in clickedNode.properties" 
                    :key="value" 
                    :value="name" 
                    type="primary"
                    class="property">
                        <el-button size="small">{{ value }}</el-button>
                    </el-badge>
                </el-row>
                <el-divider class="divider"></el-divider>
                <h3>Relations</h3>
                <el-checkbox v-model="checkall" :indeterminate="checkAllIndeterminate" @change="handleCheckAllChange">全选</el-checkbox>
                <div style="margin: 15px 0;"></div>
                <el-checkbox-group v-model="selectedRelations" @change="handleCheckChange">
                    <el-checkbox 
                    v-for="(value, name) in clickedNode.relation_num" 
                    :key="value" 
                    :label="name"
                    :indeterminate="indeterminateRelations[name]">
                    {{ name }}
                    </el-checkbox>
                </el-checkbox-group>
            </el-card>
            <el-card class="detailcard" shadow="hover" v-show="showEdgecard">
                <div slot="header">
                    <span>详细信息</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="Delete">隐藏关系</el-button>
                </div>
                <h3>Type</h3>
                <el-row>
                    <el-button size="small" type="primary" round plain>{{ clickedEdge.label }}</el-button>
                </el-row>
                <el-divider class="divider"></el-divider>
                <h3>Properties</h3>
                <el-row>
                    <el-badge v-for="(value, name) in clickedEdge.properties" 
                    :key="value" 
                    :value="name" 
                    type="primary"
                    class="property">
                        <el-button size="small">{{ value }}</el-button>
                    </el-badge>
                    <p v-show="Object.keys(clickedEdge.properties).length == 0">None</p>
                </el-row>
            </el-card>
            <el-card class="detailcard" shadow="hover" v-show="showStacard">
                <div slot="header">
                    <span>全图统计信息</span>
                    <div style="float:right;">
                        <el-radio v-model="radio" label="his" @change="radioChange">柱状图</el-radio>
                        <el-radio v-model="radio" label="pie" @change="radioChange">饼状图</el-radio>
                    </div>
                </div>
                <div id="nodechart" style="width:400px;height:400px;"></div>
                <div id="edgechart" style="width:400px;height:400px;"></div>
            </el-card>
        </div>
    </div>
</template>

<script>
import vis from 'vis';
import * as echarts from 'echarts';

export default {
    data() {
        return{
            nodesArray: [],
            edgesArray: [],
            options: {},
            nodes: [],
            edges: [],
            data: {},
            network: {},
            allNodesType: ['Event', 'Location', 'Object', 'Organization', 'Other', 'Person', 'Time'],
            allEdgesType: ['COREF', 'INTRA_CAUSE', 'INTER_CAUSE'],
            showNodecard: false,
            showEdgecard: false,
            showStacard: false,
            clickedNode: {},
            clickedEdge: { properties: {} },
            clicked: "",
            relations: [],
            selectedRelations: [],
            indeterminateRelations: [],
            checkAllIndeterminate: false,
            checkall: false,
            radio: "his",
            nodechart: {},
            edgechart: {},
            currentDocument: "8_3",
        }
    },
    methods: {
        draw() {
            var container = document.getElementById("canvas");
            this.nodes = new vis.DataSet();
            this.edges = new vis.DataSet();

            /*
            this.nodes.add([
                {id: 1, label: '1', color: "FFFF00"},
                {id: 2, label: '2', color: "00FFFF"},
                {id: 3, label: '3', color: "FF00FF"}
            ]);
            */
            /*
            // add some edges
            this.edges.add([
                {id: 1, from: 1, to: 2, smooth: {type: 'curvedCW', roundness: 0.2}, color: "#ffffff"},
                {id: 2, from: 1, to: 2, smooth: {type: 'curvedCW', roundness: -2.2}},
                {id: 5, from: 1, to: 2, label: 5,  smooth: {type: 'curvedCW', roundness: 0.4}},
                {id: 6, from: 1, to: 2, label: 6, smooth: {type: 'curvedCW', roundness: -2.4}},

                {id: 3, from: 1, to: 3, smooth: {type: 'curvedCW', roundness: -2.1}},
                {id: 4, from: 1, to: 3, smooth: {type: 'curvedCW', roundness: 0.1}}
            ]);*/
            
            // create a network
            this.data = {
                nodes: this.nodes,
                edges: this.edges,
            };

            this.options = {
                nodes: {
                    shape: "dot",
                    size: 16,
                    mass: 4,
                    shadow: { enabled: true },
                },
                edges: {
                    arrows: { 
                        to: {
                            enabled: true,
                            type: 'arrow',
                            scaleFactor: 0.5
                        }
                    },
                    smooth: {
                        type: 'continuous',
                        roundness: 0.25,
                    },
                    font: {
                        size: 12,
                        strokeWidth: 0,
                    },
                    width: 1.5,
                    selectionWidth: 2,
                },
                interaction: { 
                    hover: true,
                    selectConnectedEdges: true,
                },
                physics: {
                    enabled: false,
                },
            };
            
            //this.network = new vis.Network(container, this.data, this.options);
            //this code will cause some bugs, dont know why :(
            var network = new vis.Network(container, this.data, this.options);

            network.on("click", (params) => {
                //console.log(params);
                if(params.nodes.length > 0){
                    this.clickedNode = this.nodes.get(params.nodes[0]);
                    this.clicked = "node";
                    // get all edges of clickedNode
                    /*
                    this.$axios.post(
                        "http://localhost:8088/api/getEdges",
                        { type: this.allNodesType[this.clickedNode.group], string: this.clickedNode.label }
                    )
                    .then((response) => {
                        this.nodesArray.update(response.data.nodes);
                        this.edgesArray.update(response.data.edges);
                    })
                    .catch((error) => {
                        console.log(error);
                    })
                    */

                    // update the states of checkbox
                    this.updateCheckboxState();
                    
                    // show detail card
                    this.showEdgecard = false;
                    this.showStacard = false;
                    this.showNodecard = true;
                }else if(params.edges.length > 0){
                    this.clickedEdge = this.edges.get({
                        filter: (edge) => { return edge.id == params.edges[0]; }
                    })[0];
                    this.clicked = "edge";
                    this.showNodecard = false;
                    this.showStacard = false;
                    this.showEdgecard = true;
                }else{
                    this.showNodecard = false;
                    this.showEdgecard = false;
                    this.clicked = "none";
                }
            });

            network.on("doubleClick", (params) => {
                if(params.nodes.length == 0 && params.edges.length == 0){
                    network.setOptions({
                        physics: {
                            solver: "forceAtlas2Based"
                        }
                    });
                    network.startSimulation();
                    clearTimeout(this.timer);
                    this.timer = setTimeout(() => { 
                        network.setOptions({
                            physics: {
                                enabled: false
                            }
                        }); 
                    }, 1000);
                }
            });
        },
        updateCheckboxState() {
            //todo checkall something wrong!
            this.checkAllIndeterminate = false;
            this.relations = [];
            this.selectedRelations = [];
            for(var relation in this.clickedNode.relation_num){
                this.relations.push(relation);
                var items = this.edges.get({
                    filter: (item) => {
                        return (item.from == this.clickedNode.id || item.to == this.clickedNode.id) && item.label == relation;
                    }
                });
                //console.log(items);
                //console.log(relation);
                if(items.length > 0 && items.length < this.clickedNode.relation_num[relation]){
                    this.indeterminateRelations[relation] = true;
                    //this.selectedRelations.remove([relation]);
                }else{
                    this.indeterminateRelations[relation] = false;
                    /*
                    if(items.length == 0){
                        this.selectedRelations.remove([relation]);
                    }else 
                    */
                    if(items.length == this.clickedNode.relation_num[relation]){
                        this.selectedRelations.push(relation);
                    }
                }
            }
            //console.log(this.relations);
            //console.log(this.selectedRelations);
            this.checkall = this.relations.length === this.selectedRelations.length;
            this.checkAllIndeterminate = this.selectedRelations.length > 0 && this.selectedRelations.length < this.relations.length;
        },
        addNode(node) {
            //console.log(node);
            this.nodes.update(node);
            this.getDocumentDataReady();
        },
        addNodeFromDatabase(nodename) {
            var newnode = {};
            this.$axios.post(
                "http://localhost:8088/api/getOneNode",
                { querystr: nodename }
            )
            .then((response) => {
                newnode = response.data
                if(this.nodesArray.indexOf(newnode) == -1){
                    this.nodesArray.update(newnode);
                    this.nodes.update(newnode);
                }
            })
            .catch((error) => {
                console.log(error);
            })
        },
        handleCheckAllChange(value) {
            this.selectedRelations = value? this.relations: [];
            this.checkAllIndeterminate = false;
            for(var relation in this.indeterminateRelations){ this.indeterminateRelations[relation] = false; }
            if(value == true){
                this.edges.update(this.edgesArray.filter((edge) => {
                    if(edge.from == this.clickedNode.id){
                        this.nodes.update(this.nodesArray.filter((node) => { return node.id == edge.to; }));
                        return true;
                    }else if(edge.to == this.clickedNode.id){
                        this.nodes.update(this.nodesArray.filter((node) => { return node.id == edge.from; }));
                        return true;
                    }else{
                        return false;
                    }
                }));
            }else{
                this.edges.remove(this.edges.get({
                    filter: (edge) => {
                        return edge.from == this.clickedNode.id || edge.to == this.clickedNode.id;
                    }
                }));
            }
        },
        handleCheckChange(value) {
            let count = value.length;
            this.checkall = count === this.relations.length;
            this.checkAllIndeterminate = count > 0 && count < this.relations.length;
            this.edges.remove(this.edges.get({
                filter: (edge) => {
                    return (edge.from == this.clickedNode.id || edge.to == this.clickedNode.id) 
                    && value.indexOf(edge.label) == -1 
                    && this.indeterminateRelations[edge.label] == false;
                }
            }));
            this.edges.update(this.edgesArray.filter((edge) => {
                if(edge.from == this.clickedNode.id){
                    this.nodes.update(this.nodesArray.filter((node) => { return node.id == edge.to && value.indexOf(edge.label) != -1; }));
                    return value.indexOf(edge.label) != -1;
                }else if(edge.to == this.clickedNode.id){
                    this.nodes.update(this.nodesArray.filter((node) => { return node.id == edge.from && value.indexOf(edge.label) != -1; }));
                    return value.indexOf(edge.label) != -1;
                }else{
                    return false;
                }
            }));
            this.updateCheckboxState();
        },
        clearRedundantNode() {

        },
        Delete() {
            if(this.clicked == "node"){
                this.nodes.remove(this.clickedNode);
                this.edges.remove(this.edges.get({
                    filter: (edge) => {
                        return edge.from == this.clickedNode.id || edge.to == this.clickedNode.id;
                    }
                }));
            }else if(this.clicked == "edge"){
                this.edges.remove(this.clickedEdge);
            }
            this.showNodecard = false;
            this.showEdgecard = false;
        },
        clearAll() {
            this.nodes.clear();
            this.edges.clear();
        },
        statistic() {
            var allnode = this.nodes.get();
            var alledge = this.edges.get();
            var data = {
                node: {
                    name: this.allNodesType,
                    value: new Array(this.allNodesType.length).fill(0),
                },
                edge: {
                    name: this.allEdgesType,
                    value: new Array(this.allEdgesType.length).fill(0),
                }
            };
            for(let i = 0; i < allnode.length; i++){
                data.node.value[allnode[i].group]++;
            }
            for(let i = 0; i < alledge.length; i++){
                data.edge.value[this.allEdgesType.indexOf(alledge[i].label)]++;
            }
            return data;
        },
        showStaGraph() {
            this.showNodecard = false;
            this.showEdgecard = false;
            var data = this.statistic();
            this.nodechart = echarts.init(document.getElementById("nodechart"));
            this.edgechart = echarts.init(document.getElementById("edgechart"));
            this.nodechart.setOption({
                title: {
                    text: 'node',
                    left: 'center',
                },
                tooltip: {
                    trigger: 'item',
                },
            });
            this.edgechart.setOption({
                title: {
                    text: 'edge',
                    left: 'center',
                },
                tooltip: {
                    trigger: 'item',
                }
            })
            if(this.radio == "his"){
                this.nodechart.setOption({
                    grid: {
                        bottom: '38%',
                    },
                    yAxis: {
                        type: 'value'
                    },
                    xAxis: {
                        type: 'category',
                        axisLabel: { interval: 0, rotate: 90, margin: 10},
                        data: this.allNodesType
                    },
                    series: [{
                        data: data.node.value,
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                color: (params) => {
                                    var colorlist = ['#FFFF00', '#FB7E81', '#EB7DF4', '#AD85E4', '#FFA807', '#97C2FC', '#7BE141'];
                                    return colorlist[params.dataIndex];
                                }
                            }
                        }
                    }]
                });
                this.edgechart.setOption({
                    grid: {
                        bottom: '28%',
                    },
                    yAxis: {
                        type: 'value'
                    },
                    xAxis: {
                        type: 'category',
                        axisLabel: { interval: 0, rotate: 90, margin: 10},
                        data: this.allEdgesType
                    },
                    series: [{
                        data: data.edge.value,
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                color: (params) => {
                                    var colorlist = ['#72BFDD', '#90CC74', '#F9C757'];
                                    return colorlist[params.dataIndex];
                                }
                            }
                        }
                    }]
                })
            }else{
                var nodedata = [];
                var edgedata = [];
                for(let i = 0; i < this.allNodesType.length; i++){
                    nodedata.push({value: data.node.value[i], name: data.node.name[i]});
                }
                for(let i = 0; i < this.allEdgesType.length; i++){
                    edgedata.push({value: data.edge.value[i], name: data.edge.name[i]});
                }
                this.nodechart.setOption({
                    series: [{
                        type: 'pie',
                        radius: '50%',
                        data: nodedata,
                        itemStyle: {
                            normal: {
                                color: (params) => {
                                    var colorlist = ['#FFFF00', '#FB7E81', '#EB7DF4', '#AD85E4', '#FFA807', '#97C2FC', '#7BE141'];
                                    return colorlist[params.dataIndex];
                                }
                            }
                        }
                    }],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }}
                });
                this.edgechart.setOption({
                    series: [{
                        type: 'pie',
                        radius: '50%',
                        data: edgedata,
                        itemStyle: {
                            normal: {
                                color: (params) => {
                                    var colorlist = ['#72BFDD', '#90CC74', '#F9C757'];
                                    return colorlist[params.dataIndex];
                                }
                            }
                        }
                    }],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }}
                })
            }
            this.showStacard = true;
        },
        radioChange() {
            this.nodechart.dispose();
            this.edgechart.dispose();
            this.showStaGraph();
        },
        importAll() {
            this.nodes.clear();
            this.edges.clear();
            this.nodes.update(this.nodesArray);
            this.edges.update(this.edgesArray);
        },
        layout() {
            this.$axios.post(
                "http://localhost:8088/api/getDocumentWithPosition"
            )
            .then((response) => {
                this.nodes.clear();
                this.edges.clear();
                this.nodes.update(response.data.nodes)
                this.edges.update(response.data.edges)
            })
            .catch((error) => {
                console.log(error);
            })
        },
        getDocumentDataReady() {
            this.$axios.post(
                "http://localhost:8088/api/getDocument"
            )
            .then((response) => {
                this.nodesArray = response.data.nodes;
                this.edgesArray = response.data.edges;
            })
            .catch((error) => {
                console.log(error);
            });
        },
        saveGraph() {
            var nodes = this.nodes.get();
            var edges = this.edges.get();
            this.$axios.post(
                "http://localhost:8088/api/saveGraph",
                {nodes: nodes, edges: edges}
            )
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
        },
        getGraph() {
            this.$axios.post(
                "http://localhost:8088/api/getGraph"
            )
            .then((response) => {
                this.nodes.update(response.data.nodes);
                this.edges.update(response.data.edges);
            })
            .catch((error) => {
                console.log(error);
            });
        }
    },
    mounted() {
        this.draw();
        this.$axios.post(
            "http://localhost:8088/api/setDocument",
            { document: "8_3" }
        )
        .then((response) => {
            console.log(response);
        })
        .catch((error) => {
            console.log(error);
        });
        this.getDocumentDataReady();
        this.getGraph();
    }
}
</script>

<style scoped>
#canvas {
    width: 1385px;
    height: 870px;
    border: 1px solid lightgray;
    overflow: visible;
    float: left;
    background-color: rgb(247,246,243, 0.5);
}
#detailbox {
    margin-left: 5px;
    float: left;
    width: 450px;
    height: 870px;
}
/*
@keyframes gradientBG {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}
*/
.detailcard {
    height: 870px;
    background-color: rgb(254,253,251);
    /*background-color: rgb(49,51,59);*/
	/*background-size: 200% 200%;
	animation: gradientBG 15s ease infinite;*/
}
.property {
    margin-top: 5px;
    margin-right: 50px;
    float: left;
    white-space: normal;
    word-break: break-all;
}
.el-button--small {
    white-space: normal;
    word-break: break-all;
}
.vis-network {
    overflow: visible;
}
h3 {
    font-family: "Georgia";
    /*color:white;*/
}
/*
.divider {
    background-color: rgb(66,70,80);
}*/
</style>