<template>
    <a-layout-header class="header">
        <div class="logo" />
        <a-menu
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }"
        >
            <a-menu-item key="/">
                <router-link to="/">首页</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===managerRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">用户管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===managerRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">审核申请</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===teacherRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">个人信息</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===teacherRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">课程表</router-link>
            </a-menu-item>
            <a-menu-item key="/about"  :style="user.roleId===teacherRoleId? {} : {display:'none'}">
                <router-link to="/about">开设课程</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===teacherRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">班级管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===teacherRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">成绩录入</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===studentRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">个人信息</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===studentRoleId? {} : {display:'none'}">
                <router-link to="/about">课程表</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===studentRoleId? {} : {display:'none'}">
                <router-link to="/about">选课</router-link>
            </a-menu-item>
            <a-menu-item key="/query/student/score"  :style="user.roleId===studentRoleId? {} : {display:'none'}">
                <router-link to="/query/student/score">成绩查询</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/schooluser"  :style="user.roleId===studentRoleId? {} : {display:'none'}">
                <router-link to="/admin/schooluser">成绩详情</router-link>
            </a-menu-item>
            <a-menu-item key="/about">
                <router-link to="/about">关于我们</router-link>
            </a-menu-item>
            <a-popconfirm
                    title="确认退出登录?"
                    ok-text="是"
                    cancel-text="否"
                    @confirm="logout()"
            >
                <a class="login-menu" v-show="user.userId">
                    <span>退出登录</span>
                </a>
            </a-popconfirm>
            <a class="login-menu" v-show="user.userId">
                <span>您好：{{user.userName}}</span>
            </a>
            <a class="login-menu" v-show="!user.userId" @click="showLoginModal">
                <span>登录</span>
            </a>
        </a-menu>

        <a-modal
                title="登录"
                v-model:visible="loginModalVisible"
                :confirm-loading="loginModalLoading"
                @ok="login"
        >
            <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
                <a-form-item label="登录名">
                    <a-input v-model:value="loginUser.userId" />
                </a-form-item>
                <a-form-item label="密码">
                    <a-input v-model:value="loginUser.passwordEncode" type="password" />
                </a-form-item>
            </a-form>
        </a-modal>
    </a-layout-header>
</template>

<script lang="ts">
    import { defineComponent, ref, computed } from 'vue';

    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import store from "@/store";

    declare let hexMd5: any;
    declare let KEY: any;


    export default defineComponent({
        name: 'the-header',
        setup () {
            // 获取配置数据
            const managerRoleId = computed(() => store.state.managerRoleId);
            const teacherRoleId = computed(() => store.state.teacherRoleId);
            const studentRoleId = computed(() => store.state.studentRoleId);

            // 登录后保存
            const user = computed(() => store.state.user);

            // 请求登录的用户
            const loginUser = ref({
                userId: 3170100002,
                passwordEncode: "123456"
            });
            const loginModalVisible = ref(false);
            const loginModalLoading = ref(false);
            const showLoginModal = () => {
                loginModalVisible.value = true;
            };

            // 登录
            const login = () => {
                // 调用后端接口
                console.log("开始登录");
                loginModalLoading.value = true;
                loginUser.value.passwordEncode = hexMd5(loginUser.value.passwordEncode + KEY);
                axios.post('/schooluser/login', loginUser.value).then((response) => {
                    loginModalLoading.value = false;
                    const data = response.data;
                    if (data.success) {
                        loginModalVisible.value = false;
                        message.success("登录成功！");

                        store.commit("setUser", data.content);
                    } else {
                        message.error(data.message);
                    }
                });
            };


            // 退出登录
            const logout = () => {
                console.log("退出登录开始");
                axios.get('/schooluser/logout/' + user.value.token).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        message.success("退出登录成功！");
                        store.commit("setUser", {});
                    } else {
                        message.error(data.message);
                    }
                });
            };

            return {
                loginModalVisible,
                loginModalLoading,
                showLoginModal,
                loginUser,
                login,
                user,
                logout,

                managerRoleId,
                teacherRoleId,
                studentRoleId
            }
        }

    });
</script>

<style>
    .login-menu {
        float: right;
        color: white;
        padding-left: 10px;

    }
</style>