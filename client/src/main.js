import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import axios from 'axios';
Vue.prototype.$axios = axios;

Vue.config.productionTip = false

Array.prototype.update = function(array) {
  for(var i = 0; i < array.length; i++){
    var toadd = true;
    for(var j = 0; j < this.length; j++){
      if(array[i].id == this[j].id) {
        toadd = false;
        break;
      }
    }
    if(toadd == true) this.push(array[i]);
  }
}

Array.prototype.remove = function(item) {
  for(var i = 0; i < this.length; i++){
    if(this[i] == item)
      this.splice(i, 1);
  }
}

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
