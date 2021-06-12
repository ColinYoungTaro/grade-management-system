<template>
  <a-layout>
    <the-user-sider></the-user-sider>
    <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
        <router-view/>
        用户管理页面
    </a-layout-content>
  </a-layout>
</template>


<script lang="ts">
  import { defineComponent, onMounted, ref, computed } from 'vue';
  import TheUserSider from '@/components/the-user-sider.vue';
  import store from "@/store";
  import axios from 'axios';

  export default defineComponent({
    name: 'AdminSchoolUser',
    components: {
      TheUserSider,
    },
  });

</script>