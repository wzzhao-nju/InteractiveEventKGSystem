<template>
<div class="header">
  <el-menu router :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <router-link to="/home">
      <img alt="Vue logo" src="../assets/icon.png" class="logo">
    </router-link>
    <el-menu-item index="/about">关于</el-menu-item>
    <el-menu-item index="/analyse">时间线系统</el-menu-item>
    <el-menu-item index="/graph">图谱系统</el-menu-item>
  </el-menu>
</div>
</template>

<script>
export default {
  name: 'Nav',
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
      if(key == "/analyse"){
        this.$emit("gotoAnalyse");
      }
    }
  }
}
</script>

<style scoped>
.logo{
  float: left;
  margin-left: 20px;
  height: 50px;
  width: 50px;
}
.titleimg {
  margin-left: 10px;
  height: 50px;
}
.title{
  margin-left: 10px;
  font-size: 36px;
  font-family: 'Times New Roman';
  font-weight: bold;
}
.el-menu--horizontal>.el-menu-item{
  height: 55px;
  float: right;
}
</style>