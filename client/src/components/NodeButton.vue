<template>
    <div class="NodeButton">
        <div class="buttonbox">
            <el-tooltip content="切换文档" placement="right">
                <el-button icon="el-icon-news" circle class="buttonitem" @click="setDocument"></el-button>
            </el-tooltip>
            <el-tooltip content="导入节点" placement="right">
                <el-button icon="el-icon-plus" circle style="margin-left: 0px" @click="importNode"></el-button>
            </el-tooltip>
            <el-tooltip content="批量导入" placement="right">
                <el-button icon="el-icon-arrow-right" circle style="margin-left: 0px" class="buttonitem" @click="importAll"></el-button>
            </el-tooltip>
            <el-tooltip content="统计信息" placement="right">
                <el-button icon="el-icon-data-analysis" circle style="margin-left: 0px" class="buttonitem" @click="showStaGraph"></el-button>
            </el-tooltip>
            <el-tooltip content="文本布局" placement="right">
                <el-button icon="el-icon-guide" circle style="margin-left: 0px" class="buttonitem" @click="layout"></el-button>
            </el-tooltip>
            <el-tooltip content="展示文本" placement="right">
                <el-button icon="el-icon-document" circle style="margin-left: 0px" class="buttonitem" @click="showText"></el-button>
            </el-tooltip>
            <el-tooltip content="删除节点" placement="right">
                <el-button icon="el-icon-delete" circle style="margin-left: 0px" class="buttonitem" @click="deleteNode"></el-button>
            </el-tooltip>
            <el-tooltip content="清空全图" placement="right">
                <el-button icon="el-icon-refresh" circle style="margin-left: 0px" class="buttonitem" @click="clearAll"></el-button>
            </el-tooltip>
            <el-tooltip content="转到时间线" placement="right">
                <router-link to="/analyse">
                    <el-button icon="el-icon-right" circle style="margin-left: 0px" class="buttonitem" @click="gotoAnalyse"></el-button>
                </router-link>
            </el-tooltip>
        </div>
        <el-dialog title="导入节点"
        :visible.sync="dialogVisible"
        width="40%">
            <el-tabs v-model="activeTab" type="card">
                <el-tab-pane label="Event" name="Event">
                    <Search :searchType="activeTab" v-on:select="updateSelect($event)" v-on:addChange="updateaddDisable"></Search>
                </el-tab-pane>
                <el-tab-pane label="Location" name="Location">
                    <Search :searchType="activeTab" v-on:select="updateSelect" v-on:addChange="updateaddDisable"></Search>
                </el-tab-pane>
                <el-tab-pane label="Object" name="Object">
                    <Search :searchType="activeTab" v-on:select="updateSelect" v-on:addChange="updateaddDisable"></Search>
                </el-tab-pane>
                <el-tab-pane label="Organization" name="Organization">
                    <Search :searchType="activeTab" v-on:select="updateSelect" v-on:addChange="updateaddDisable"></Search>
                </el-tab-pane>
                <el-tab-pane label="Other" name="Other">
                    <Search :searchType="activeTab" v-on:select="updateSelect" v-on:addChange="updateaddDisable"></Search>
                </el-tab-pane>
                <el-tab-pane label="Person" name="Person">
                    <Search :searchType="activeTab" v-on:select="updateSelect" v-on:addChange="updateaddDisable"></Search>
                </el-tab-pane>
                <el-tab-pane label="Time" name="Time">
                    <Search :searchType="activeTab" v-on:select="updateSelect" v-on:addChange="updateaddDisable"></Search>
                </el-tab-pane>
            </el-tabs>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="add" :disabled="addDisable">添 加</el-button>
            </span>
        </el-dialog>
        <el-dialog title="文本内容展示" :visible.sync="textVisible" width="70%" class="text_dialog" top="50px">
            <el-scrollbar>
                <pre v-html="text" class="text"></pre>
            </el-scrollbar>
        </el-dialog>
        <el-dialog title="切换文档" :visible.sync="documentVisible" width="70%">
            <el-table :data="documentData">
                <el-table-column prop="id" label="文档编号" width="150"></el-table-column>
                <el-table-column prop="title" label="文档标题" width="700"></el-table-column>
                <el-table-column prop="time" label="文档日期" width="200"></el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button @click="changeDocument(scope.row)" type="text">切换到该文档</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
import Search from './Search'

export default {
    data() {
        return {
            dialogVisible: false,
            textVisible: false,
            documentVisible: false,
            activeTab: 'Event',
            selectInput: '',
            addDisable: true,
            text: "",
            chosenNode: {},
            documentData: [],
        };
    },
    components: {
        Search
    },
    methods: {
        add() {
            this.dialogVisible = false;
            this.addDisable = true;
            this.$emit("addNode", this.chosenNode);
        },
        updateSelect(node) {
            console.log(node);
            this.selectInput = node.label;
            this.chosenNode = node;    
            this.addDisable = false;
        },
        updateaddDisable() {
            this.addDisable = true;
        },
        setDocument() {
            this.$axios.post(
                "http://localhost:8088/api/getDocumentList"
            )
            .then((response) => {
                this.documentData = response.data
            })
            .catch((error) => {
                console.log(error);
            })
            this.documentVisible = true;
        },
        changeDocument(row) {
            this.$axios.post(
                "http://localhost:8088/api/setDocument",
                { document: row.id }
            )
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            })
            this.documentVisible = false;
        },
        importNode() {
            this.dialogVisible = true;
        },
        deleteNode() {
            this.$emit("deleteNode");
        },
        importAll() {
            this.$emit("importAll");
        },
        showText() {
            this.$axios.post(
                "http://localhost:8088/api/getOriginalText"
            )
            .then((response) => {
                this.text = response.data.text;
            })
            .catch((error) => {
                console.log(error);
            })
            this.textVisible = true;
        },
        showStaGraph() {
            this.$emit("showStaGraph");
        },
        layout() {
            this.$emit("layout");
        },
        clearAll() {
            this.$emit("clearAll");
        },
        gotoAnalyse() {
            this.$emit("gotoAnalyse");
        }
    }
}
</script>

<style>
.buttonbox {
    width: 50px;
}
@keyframes rotate {
    0% {transform: rotateY(0deg);}
    100% {transform: rotateY(360deg);}
}
.buttonitem {
    margin-top: 1px;
    margin-bottom: 2px;
    background-color: rgb(254,253,251);
}
/*
.buttonitem:hover{
    animation-name: rotate;
    animation-duration: 2s;
    animation-iteration-count: 1;
}
*/
.text_dialog .el-dialog__header {
    text-align: center;
    border-bottom: 1px solid #e8eaec;
}
.text_dialog .el-dialog__body {
    padding: 0px 20px;
    font-size: 16px;
}
p {
    display: inline;
}
.c0 {
    background: #FFFF00;
    border: 1px solid black;
}
.c1 {
    background:#FB7E81;
    border: 1px solid black;
}
.c2 {
    background: #AD85E4;
    border: 1px solid black;
}
.c3 {
    background: #FFA807;
    border: 1px solid black;
}
.c4 {
    background: #EB7DF4;
    border: 1px solid black;
}
.c5 {
    background:#97C2FC;
    border: 1px solid black;
}
.c6 {
    background: #7BE141;
    border: 1px solid black;
}
/*
.text_dialog {
    display: flex;
    justify-content: center;
    align-items: center;
}
*/
/*
.text_dialog .el-dialog {
    margin: 0 auto !important;
    height: 90%;
    overflow: hidden;
}
.text_dialog .el-dialog__body {
    position: absolute;
    left: 0;
    top: 54px;
    bottom: 0;
    right: 0;
    padding: 0;
    z-index: 1;
    overflow: hidden;
    overflow-y: auto;
}
.text_dialog {
    overflow: auto;
    overflow-y: auto;
}
*/
</style>