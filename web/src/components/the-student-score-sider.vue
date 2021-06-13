<template>
    <a-layout-sider class="the_student_score_sider" width="200" style="background: #fff">
        <a-menu
                :style="{ height: '100%', borderRight: 0 }"
        >
            <a-sub-menu key="/student/query/whole/score">
                <template #title>
                    <span><user-outlined />
                        <router-link to="/student/query/whole/score" class="router-link-active"> 成绩查询 </router-link>
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="/student/analysis/score">
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
    import { defineComponent,ref } from 'vue';

    export default defineComponent({
        name: 'the-student-score-sider',
        setup(){
            const selected = ref();
            selected.value = ['/student/query/whole/score'];

            return{
                selected,
            }
        }
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