<template>
  <div class="home">
    <div class="titles">
      <div class="title">EGV</div>
      <div class="intro">一个可视化事件图谱分析系统</div>
      <router-link to="/graph"><el-button type="primary" class="start">现在开始</el-button></router-link>
    </div>
    <div class="bg"></div>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
}
</script>

<style scoped>
.home{
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  display:flex;/*Flex布局*/
  display: -webkit-flex; /* Safari */
  align-items:center;/*指定垂直居中*/
}
.titles{
  margin: 0 auto;
  text-align: center;
}
.title,.intro{
  font-weight: bold;
  letter-spacing: 6px;
  /* font-family:Verdana, Geneva, Tahoma, sans-serif; */
  color: white; 
}
.title{
  font-size: 45px;
  /* padding-top: 20%; */
}
.intro{
  padding-top: 10px;
  font-size: 25px;
}
.start{
  margin: 70px;
}
.bg{
  background: url("../assets/background.jpg");
  position:fixed;
  top: 0;
  left: 0;
  width:100%;
  height:100%;
  min-width: 1000px;
  z-index:-10;
  zoom: 1;
  background-color: #fff;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -o-background-size: cover;
  background-position: center 0;
}
</style>
