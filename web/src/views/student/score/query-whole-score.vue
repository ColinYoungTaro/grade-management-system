<template>
    <a-layout>
        <the-student-score-sider></the-student-score-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >

            <div style="background-color: #ffffff; padding: 5px">
                <a-row :gutter="0">
                    <a-cow>
   <pre>

   </pre>
                    </a-cow>
                    <a-cow >
                        <p>
                            <a-form layout="inline" :model="param">
                                <a-form-item>
                                    <a-input v-model:value="param.courseUid" placeholder="课程号" >
                                    </a-input>
                                </a-form-item>
                                <a-form-item>
                                    <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
                                        查询
                                    </a-button>
                                </a-form-item>
                            </a-form>
                        </p>
                    </a-cow>
                </a-row>
                <a-row :gutter="0">
                    <a-col :span="2.5" >
                        <a-card :bordered="false" >
                            <p>
                                <a-button @click="handleQuery">
                                    在校成绩
                                </a-button>
                            </p>
                        </a-card>
                    </a-col>

                    <a-col :span="3">
                        <a-card :bordered="false">
                            <p>
                                <a-dropdown-button >
                                    按课程类型查询
                                    <template #overlay>
                                        <a-menu  @click="handleCourseTypeMenuClick">
                                            <a-menu-item key="0">
                                                <UserOutlined />
                                                通识课程
                                            </a-menu-item>
                                            <a-menu-item key="1">
                                                <UserOutlined />
                                                专业课程
                                            </a-menu-item>
                                        </a-menu>
                                    </template>
                                </a-dropdown-button>
                            </p>
                        </a-card>
                    </a-col>
                </a-row>
            </div>

            <a-table
                    :columns="columns"
                    :row-key="record => record.id"
                    :data-source="course_scores"
                    :pagination="pagination"
                    :loading="loading"
                    @change="handleTableChange"
            >
            </a-table>
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheStudentScoreSider from '@/components/the-student-score-sider.vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";


    export default defineComponent({
        name: 'StudentQueryWholeScore',
        components: {
            TheStudentScoreSider,
        },
        setup() {

            const user = computed(() => store.state.user);

            const param = ref();
            param.value = {};



            const course_scores = ref();

            const pagination = ref({
                current: 1,
                pageSize: 8,
                total: 0
            });
            const loading = ref(false);

            const columns = [
                {
                    title: '课程代码',
                    key: 'courseUid',
                    dataIndex: 'courseUid'
                },
                {
                    title: '课程名称',
                    dataIndex: 'courseName'
                },
                {
                    title: '成绩',
                    dataIndex: 'score'
                },
                {
                    title: '学分',
                    dataIndex: 'credit'
                },
                {
                    title: '绩点',
                    dataIndex: 'gpa'
                },
            ];

            var query_year = 0;

            /**
             * 数据查询
             **/
            const handleQuery = (params: any) => {
                loading.value = true;
                course_scores.value = [];
                axios.get("/studentscore/whole/list", {
                    params :{
                        page: params.page,
                        size: params.size,
                        studentId: user.value.userId,
                        courseUid: param.value.courseUid
                    }
                }).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        course_scores.value = data.content.list;

                        // 重置分页按钮
                        pagination.value.current = params.page;
                        pagination.value.total = data.content.total;
                    } else {
                        message.error(data.message);
                    }
                });
            };

        /*    const handleYearMenuClick = (e: any) => {
                loading.value = true;
                course_scores.value = [];

                axios.get("/studentscore/single/year/list", {
                    params :{
                        year: e.key,
                        studentId: user.value.userId,
                    }
                }).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        course_scores.value = data.content.list;

                        // 重置分页按钮
                        pagination.value.current = 1;
                        pagination.value.total = data.content.total;
                    } else {
                        message.error(data.message);
                    }
                });
            };*/

            const handleCourseTypeMenuClick = (e: any) => {
                loading.value = true;
                course_scores.value = [];

                axios.get("/studentscore/course/type/list", {
                    params :{
                        courseType: e.key,
                        studentId: user.value.userId,
                    }
                }).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        course_scores.value = data.content.list;

                        // 重置分页按钮
                        pagination.value.current = 1;
                        pagination.value.total = data.content.total;
                    } else {
                        message.error(data.message);
                    }
                });
            };


            /**
             * 表格点击页码时触发
             */
            const handleTableChange = (pagination: any) => {
                console.log("分页参数：" + pagination);
                handleQuery({
                    page: pagination.current,
                    size: pagination.pageSize
                });
            };

            onMounted(() => {
                handleQuery({
                    page: 1,
                    size: pagination.value.pageSize,
                });
            });

            return {
                param,
                pagination,
                columns,
                loading,
                handleTableChange,
                handleQuery,
                course_scores,
                user,

                //handleYearMenuClick,
                handleCourseTypeMenuClick,
            }
        }
    });
</script>

