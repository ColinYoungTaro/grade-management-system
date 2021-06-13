<template>
    <a-layout>
        <the-select-course-sider></the-select-course-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            学生选课页面
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheSelectCourseSider from '@/components/the-select-course-sider.vue';
    import store from "@/store";
    import axios from 'axios';

    export default defineComponent({
        name: 'SelectMajorCourse',
        components: {
            TheSelectCourseSider,
        },
    });

</script>