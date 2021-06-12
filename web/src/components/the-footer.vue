<template>
    <a-layout-footer style="text-align: center">
        学生成绩管理系统<span v-show="user.userId">，欢迎：{{user.userName}}</span>
    </a-layout-footer>
</template>

<script lang="ts">
    import { defineComponent, computed } from 'vue';
    import store from "@/store";

    export default defineComponent({
        name: 'the-footer',
        setup(){
            const user = computed(() => store.state.user);

            return {
                user
            }
        }
    });
</script>
