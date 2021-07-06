<template>
    <div class="Search">
        <el-row>
            <el-col :span=12>
                <el-autocomplete class="input" 
                v-model="inputstr" 
                :fetch-suggestions="querySearch" 
                placeholder="请输入节点名称" 
                :trigger-on-focus="false"
                @select="handleSelect"
                @input="handleInput"
                :debounce=0
                clearable>
                </el-autocomplete>
            </el-col>
        </el-row>
        <div v-show="showDetails">
            <h3>Properties</h3>
                <el-row>
                    <el-badge  v-for="(value, name) in chosenNode.properties" :key="value"
                    :value="name" 
                    type="primary"
                    class="attribute">
                        <el-button size="small">{{ value }}</el-button>
                    </el-badge>
                </el-row>
            <h3>Relation</h3>
            <el-row>
                <el-badge  v-for="(value, name) in chosenNode.relation_num" :key="value"
                :value="name" 
                type="success"
                class="attribute">
                    <el-button size="small">{{ value }}</el-button>
                </el-badge>
            </el-row>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            nodelist: [],
            inputstr: '',
            showDetails: false,
            chosenNode: {},
            propertyData: [],
            relationData: []
        };
    },
    props: ['searchType'],
    methods: {
        querySearch(queryString, callback){
            this.$axios.post(
                "http://localhost:8088/api/query",
                {string: queryString, type: this.searchType})
            .then((response) => {
                this.nodelist = response.data
                callback(this.nodelist)
            })
            .catch((error) => {
                console.log(error)
            })
        },
        handleSelect(item){
            this.showDetails = true
            this.$axios.post(
                "http://localhost:8088/api/searchDetail",
                {string: item.value, type: this.searchType})
            .then((response) => {
                this.chosenNode = response.data;
            })
            .catch((error) => {
                console.log(error)
            })
            clearTimeout(this.timer);
            this.timer = setTimeout(() => { 
                this.$emit("select", this.chosenNode);
            }, 100);
        },
        handleInput(){
            this.showDetails = false
            this.$emit("addChange");
        }
    }
}
</script>

<style scoped>
.divider {
    margin-top: 5px;
    margin-bottom: 5px;
}
.attribute {
    margin-top: 5px;
    margin-right: 100px;
    float: left;
}
</style>