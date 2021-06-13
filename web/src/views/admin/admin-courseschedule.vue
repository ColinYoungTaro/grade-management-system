<template>
    <a-layout>
        <the-manage-course-sider></the-manage-course-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <router-view/>
            管理教学安排的页面
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheManageCourseSider from '@/components/the-manage-course-sider.vue';
    import store from "@/store";
    import axios from 'axios';

    export default defineComponent({
        name: 'AdminCourseSchedule',
        components: {
            TheManageCourseSider,
        },
    });

</script>