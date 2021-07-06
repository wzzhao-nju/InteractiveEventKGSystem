<template>
  <div class="Graph">
    <Nav v-on:gotoAnalyse="gotoAnalyse"></Nav>
    <!--
    <div class="background">
      <img src="../assets/background02.jpg">
    </div>
    -->
    <div class="buttonbox">
      <NodeButton v-on:addNode="addNode"
      v-on:deleteNode="deleteNode"
      v-on:importAll="importAll"
      v-on:showStaGraph="showStaGraph"
      v-on:layout="layout"
      v-on:clearAll="clearAll"
      v-on:gotoAnalyse="gotoAnalyse"></NodeButton>
    </div>
    <div class="window">
      <Window ref="Window"></Window>
    </div>
  </div>
</template>



<script>
import Nav from '../components/Nav'
import Window from '../components/Window';
import NodeButton from '../components/NodeButton';

export default {
  name: 'Graph',
  data() {
    return{
      background: {
        backgroundImage: "url(" + require("../assets/background02.jpg") + ")",
        backgroundRepeat: "no-repeat"
      }
    }
  },
  components: {
		Nav,
    NodeButton,
    Window,
  },
  methods: {
    addNode(node) {
      this.$refs.Window.addNode(node);
    },
    deleteNode() {
      this.$refs.Window.Delete();
    },
    importAll() {
      this.$refs.Window.importAll();
    },
    showStaGraph() {
      this.$refs.Window.showStaGraph();
    },
    layout() {
      this.$refs.Window.layout();
    },
    clearAll() {
      this.$refs.Window.clearAll();
    },
    gotoAnalyse() {
      this.$refs.Window.saveGraph();
    }
  }
}

</script>

<style scoped>
.background{
  width:100%;  
  height:100%;
  z-index:-1;
  position: absolute;
}
.buttonbox{
  float: left;
}
.window {
  position: absolute;
  top: 68px;
  left: 55px;
}
</style>