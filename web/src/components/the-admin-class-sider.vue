<template>
    <a-layout-sider class="the_manage_class_sider" width="200" style="background: #fff">
        <a-menu
                mode="inline"
                :style="{ height: '100%', borderRight: 0 }"
        >
            <a-sub-menu key="/admin/class">
                <template #title>
                    <span><user-outlined />
                        <router-link to="/admin/class">教学班级管理</router-link>
                    </span>
                </template>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: 'the-manage-class-sider'
    });
</script>