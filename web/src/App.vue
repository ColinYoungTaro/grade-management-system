<template>
  <a-layout id="components-layout-demo-top-side-2">
      <the-header class="header"></the-header>
    <div class="section">
      <router-view />
    </div>
      <the-footer class="footer"></the-footer>
  </a-layout>
</template>

<script lang="ts">
  import { defineComponent} from 'vue';
  import TheHeader from '@/components/the-header.vue';
  import TheFooter from '@/components/the-footer.vue';

  export default defineComponent({
    name: 'app',
    components: {
      TheHeader,
      TheFooter,
    },
  });
</script>

<style>
  html,body{height: 100%;}

  #components-layout-demo-top-side-2 {
    display: flex;
    flex-direction: column;
    min-height: 100%;
  }

  .header{flex: 0;background: blue;}

  .section{flex: 1;background: white;}

  .footer{flex: 0;background: red}

  #app {
    position:absolute;
    top:0;
    right:0;
    bottom:0;
    left:0;
  }
</style>


