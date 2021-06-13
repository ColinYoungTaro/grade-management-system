<template>
    <a-layout>
        <a-layout-content
                :style="{ background: '#fff', padding: '3px', margin: 0, minHeight: '280px' }"
        >
        <div align="center">

            <a-card title="个人信息"  style="width: 1200px" :body-style='{padding:1}' :headStyle="{ height: 9,  background: '#0785fd', color:'#ffffff'}">
                <div align="center">
                    <a-card title="基本信息" style="width: 1100px" :bordered=false :body-style='{padding:0}' :headStyle="{ height: 6, 'text-align': 'left' }"  >
                        <div style="background-color: #ffffff; ">
                            <a-row :gutter="0">
                 <pre>

                 </pre>
                                <a-col :span="10">
                                    <a-card :bordered="false">
                                        <a-form :model="teacher" :label-col="{ span: 3 }" :wrapper-col="{ span: 11 }">
                                            <a-form-item label="工号">
                                                <a-input v-model:value="teacher.userId" :disabled="true"/>
                                            </a-form-item>
                                            <a-form-item label="姓名">
                                                <a-input v-model:value="teacher.userName" :disabled="true"/>
                                            </a-form-item>
                                            <a-form-item label="性别">
                                                <a-input v-model:value="teacher.stringGender" :disabled="true"/>
                                            </a-form-item>
                                            <a-form-item label="学院">
                                                <a-input v-model:value="teacher.departmentName" :disabled="true"/>
                                            </a-form-item>
                                        </a-form>
                                    </a-card>
                                </a-col>
    <pre>

    </pre>
                                <a-col :span="10">
                                    <a-card :bordered="false">
                                        <a-form :model="teacher" :label-col="{ span: 3 }" :wrapper-col="{ span: 11 }">
                                            <a-form-item label="出生">
                                                <a-date-picker v-model:value="teacher.birthday" :format="dateFormat" />
                                            </a-form-item>
                                            <a-form-item label="邮箱 ">
                                                <a-input v-model:value="teacher.email" />
                                            </a-form-item>
                                            <a-form-item label="电话 ">
                                                <a-input v-model:value="teacher.phoneNumber" />
                                            </a-form-item>
                                        </a-form>
                                    </a-card>
                                </a-col>
                            </a-row>
                        </div>
                    </a-card>
                </div>
                <a-button type="primary" @click="handleConfirm">
                    确定
                </a-button>
            </a-card>

        </div>
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import moment, { Moment } from 'moment';
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";


    export default defineComponent({
        name: 'TeacherInformation',
        components: {
        },
        setup() {
            const dateFormat = 'YYYY/MM/DD';
            const user = computed(() => store.state.user);

            const teacher = ref();
            teacher.value = {};

            /**
             * 数据查询
             **/
            const handleQuery = (params: any) => {
                teacher.value = {};
                axios.get("/teacher/information", {
                    params :{
                        userId: user.value.userId,
                    }
                }).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        teacher.value = data.content;
                    } else {
                        message.error(data.message);
                    }
                });
            };

            const handleConfirm = (params: any) => {
                axios.post("/teacher/information/save", teacher.value).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        message.info("提交成功");
                        // 重新加载列表
                        handleQuery({
                            userId: user.value.userId,
                        });
                    } else{
                        message.error(data.message);
                    }
                });
            };

            onMounted(() => {
                handleQuery({
                    userId: user.value.userId,
                });
            });

            return {
                handleQuery,
                teacher,
                user,
                dateFormat,
                handleConfirm,
            }
        }
    });
</script>

