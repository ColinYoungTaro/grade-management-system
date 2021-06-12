<template>
    <a-layout>
        <the-student-score-sider></the-student-score-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <router-view/>
            学生成绩查询页面
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheStudentScoreSider from '@/components/the-student-score-sider.vue';
    import store from "@/store";
    import axios from 'axios';

    export default defineComponent({
        name: 'QueryStudentScore',
        components: {
            TheStudentScoreSider,
        },
    });

</script>