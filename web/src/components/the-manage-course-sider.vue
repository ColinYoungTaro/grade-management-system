<template>
    <a-layout-sider class="the_manage-course_sider" width="200" style="background: #fff">
        <a-menu
                mode="inline"
                :style="{ height: '100%', borderRight: 0 }"
        >
            <a-sub-menu key="/admin/course">
                <template #title>
                    <span><user-outlined />
                        <router-link to="/admin/course">课程</router-link>
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="/admin/course_schedule">
                <template #title>
                    <span><user-outlined />
                         <router-link to="/admin/course_schedule">教学安排</router-link>
                    </span>
                </template>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: 'the-manage-course-sider'
    });
</script>