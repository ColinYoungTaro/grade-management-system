<template>
    <a-layout-sider class="the_select_course_sider" width="200" style="background: #fff">
        <a-menu
                :style="{ height: '100%', borderRight: 0 }"
        >
            <a-sub-menu key="/select/all/course">
                <template #title>
                    <span><user-outlined />
                        <router-link to="/select/all/course" class="router-link-active"> 全部课程 </router-link>
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="/select/major/course">
                <template #title>
                    <span><user-outlined />
                        <router-link to="/select/major/course" class="router-link-active"> 专业课程 </router-link>
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="/select/general/course">
                <template #title>
                    <span><laptop-outlined />
                        <router-link to="/select/general/course"  class="router-link-active"> 通识课程 </router-link>
                    </span>
                </template>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
    import { defineComponent,ref } from 'vue';

    export default defineComponent({
        name: 'the-select-course-sider',
    });
</script>

<style>
    .router-link-active {
        text-decoration: none;
        color: gray;
    }
    a{
        text-decoration: none;
        color: gray;
    }
</style>