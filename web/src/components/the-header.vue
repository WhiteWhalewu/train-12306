<template>
  <a-layout-header class="header">
    <div class="logo">
      <router-link to="/welcome" style="color: white; font-size: 18px">
        12306
      </router-link>
    </div>
      <div style="float: right; color: white;">
          您好：{{ member.mobile }} &nbsp;&nbsp;
          <router-link to="/login" @click="clearToken" style="color: white; ">
              退出登录 &nbsp;&nbsp;
          </router-link>
      </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <coffee-outlined /> &nbsp; 欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/passenger">
        <router-link to="/passenger">
          <user-outlined /> &nbsp; 乘车人管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/ticket">
        <router-link to="/ticket">
          <border-outer-outlined /> &nbsp; 余票查询
        </router-link>
      </a-menu-item>
      <a-menu-item key="/my-ticket">
        <router-link to="/my-ticket">
          <idcard-outlined /> &nbsp; 我的车票
        </router-link>
      </a-menu-item>
      <a-menu-item key="/seat">
        <router-link to="/seat">
          <usergroup-add-outlined /> &nbsp; 座位销售图
        </router-link>
      </a-menu-item>
      <a-menu-item key="/admin">
        <router-link to="/admin">
          <desktop-outlined /> &nbsp; 关于控台管理
        </router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script>
    import {defineComponent, ref, watch} from 'vue';
    import store from "@/store";
    import router from '@/router'

    function clearToken() {
        // 这里是清除token的逻辑，例如删除localStorage中的token
        window.SessionStorage.clearAll();
    }

    export default defineComponent({
        methods: {
            clearToken() {
                clearToken(); // 调用清除token的函数
                this.$router.push('/login'); // 使用编程式导航进行导航
            }
        },
        name: "the-header-view",
        setup() {
            let member = store.state.member;
            const selectedKeys = ref([]);
            watch(() => router.currentRoute.value.path, (newValue) => {
                console.log('watch', newValue);
                selectedKeys.value = [];
                selectedKeys.value.push(newValue);
            }, {immediate: true});
            return {
      member,
      selectedKeys
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.logo {
  float: left;
  height: 31px;
  width: 150px;
  color: white;
  font-size: 20px;
}
</style>
