<template>
    <a-layout-sider class="the_user_sider" width="200" style="background: #fff">
        <a-menu
                mode="inline"
                :style="{ height: '100%', borderRight: 0 }"
        >
            <a-sub-menu key="/admin/student">
                <template #title>
                    <span><user-outlined />
                        <router-link to="/admin/student" class="router-link-active">学生</router-link>
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="/admin/teacher">
                <template #title>
                    <span><user-outlined />
                         <router-link to="/admin/teacher" class="router-link-active">教师</router-link>
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="/admin/manager">
                <template #title>
                    <span><laptop-outlined />
                         <router-link to="/admin/manager" class="router-link-active">管理员</router-link>
                    </span>
                </template>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: 'the-user-sider'
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