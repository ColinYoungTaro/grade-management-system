<template>
    <a-layout-sider class="the_student_score_sider" width="200" style="background: #fff">
        <a-menu
                mode="inline"
                :style="{ height: '100%', borderRight: 0 }"
        >
            <a-sub-menu key="/student/query/whole/score">
                <template #title>
                    <span><user-outlined />
                        <router-link to="/student/query/whole/score" class="router-link-active"> 在校成绩 </router-link>
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="/student/query/single/year/score">
                <template #title>
                    <span><user-outlined /> 详细成绩 </span>
                </template>
                <a-menu-item key="/student/query/major/score">专业课程</a-menu-item>
                <a-menu-item key="/student/query/other/score">个性课程</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="/admin/manager">
                <template #title>
                    <span><laptop-outlined />
                        <router-link to="/student/analysis/score"  class="router-link-active"> 成绩分析 </router-link>
                    </span>
                </template>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: 'the-student-score-sider'
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