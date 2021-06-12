<template>
  <a-layout id="home">
    <a-layout-content
            class="content"
            :style="note"
    >
      <pre>
学生成绩管理系统首页
      </pre>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import axios from 'axios';


  export default defineComponent({
    name: 'Home',

      setup() {
          const note = ref();
          note.value = {
              backgroundImage: 'url(' + require('../assets/home.jpg') + ')',
              backgroundRepeat: 'no-repeat',
              backgroundSize: '100%',
          };

          return {
              note
          }
      }

  });
</script>

<style>
    html,body{height: 100%;}

    #home {
        display: flex;
        flex-direction: column;
        min-height: 100%;
    }

    .content{flex: 1;background: white;}

    #components-layout-demo-top-side-2 {
        position:absolute;
        top:0;
        right:0;
        bottom:0;
        left:0;
    }
</style>