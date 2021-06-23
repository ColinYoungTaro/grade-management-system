<template>
    <a-layout>
        <the-student-score-sider></the-student-score-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '3px', margin: 0, minHeight: '280px' }"
        >
            <br>
            <br>
            <div align="center">
                <button type="button" onclick="printJS({ printable: 'printJS-form', type: 'html', header: '成绩单' })">
                    打印成绩单
                </button>
            </div>
            <br>


            <div id="printJS-form" align="center">
                <a-card style="width: 1100px" :bordered=false :body-style='{padding:0}' :headStyle="{ height: 6, 'text-align': 'left' }"  >
                    <div style="background-color: #ffffff; ">
                        <a-row :gutter="0">
                            <a-col :span="7">
                                <a-form :model="scoreAnalysis" :label-col="{ span: 3 }" :wrapper-col="{ span: 5 }">
                                    <a-form-item label="学号">
                                        <a>{{userId}}</a>
                                    </a-form-item>
                                </a-form>
                            </a-col>
                            <a-col :span="7">
                                <a-form :model="scoreAnalysis" :label-col="{ span: 3 }" :wrapper-col="{ span: 5 }">
                                    <a-form-item label="姓名">
                                        <a>{{userName}}</a>
                                    </a-form-item>
                                </a-form>
                            </a-col>
                        </a-row>
                        <a-row :gutter="0">
                            <a-col :span="7">
                                    <a-form :model="scoreAnalysis" :label-col="{ span: 3 }" :wrapper-col="{ span: 5 }">
                                        <a-form-item label="均分">
                                            <a>{{gpa}}</a>
                                        </a-form-item>
                                    </a-form>
                            </a-col>
                            <a-col :span="7">
                                    <a-form :model="scoreAnalysis" :label-col="{ span: 3 }" :wrapper-col="{ span: 5 }">
                                        <a-form-item label="排名">
                                            <a>{{rank}}</a>
                                        </a-form-item>
                                    </a-form>
                            </a-col>
                            <a-col :span="7">
                                    <a-form :model="scoreAnalysis" :label-col="{ span: 3 }" :wrapper-col="{ span: 5 }">
                                        <a-form :model="scoreAnalysis" :label-col="{ span: 3 }" :wrapper-col="{ span: 5 }">
                                            <a-form-item label="学分">
                                                <a>{{credit}}</a>
                                            </a-form-item>
                                        </a-form>
                                    </a-form>
                            </a-col>

                        </a-row>
                        <a-row :gutter="0">
                        <a-card style="width: 1500px" :bordered=false :body-style='{padding:0}' :headStyle="{ height: 6, 'text-align': 'left' }"  >

                            <a-table
                                    :align="center"
                                    :columns="columns"
                                    :row-key="record => record.id"
                                    :data-source="course_scores"
                                    :pagination="false"
                                    :loading="loading"
                            >
                            </a-table>
                        </a-card>
                        </a-row>
                    </div>
                </a-card>


            </div>
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import moment, { Moment } from 'moment';
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheStudentScoreSider from '@/components/the-student-score-sider.vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";
    import printJS from 'print-js';


    export default defineComponent({
        name: 'AnalyzeScore',
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
                pageSize: 100,
                total: 0
            });
            const loading = ref(false);

            const columns = [
                {
                    title: '课号',
                    key: 'courseUid',
                    dataIndex: 'courseUid',
                    width: "100px"
                },
                {
                    title: '课程名称',
                    dataIndex: 'courseName',
                    width: "150px"
                },
                {
                    title: '成绩',
                    dataIndex: 'score',
                    width: "100px"
                },
                {
                    title: '学分',
                    dataIndex: 'credit',
                    width: "100px"
                },
                {
                    title: '绩点',
                    dataIndex: 'gpa',
                    width: "100px"
                },
            ];


            var query_year = 0;
            const userId = user.value.userId;
            const userName = user.value.userName;

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

            /**
             * 获取学生成绩分析结果
             */
            const rank = ref();
            const credit = ref();
            const gpa = ref();
            const scoreAnalysis = ref();
            scoreAnalysis.value = {};
            const getScoreInformation = () => {
                axios.get("/score/analysis/student/"+user.value.userId).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        scoreAnalysis.value = data.content;
                        gpa.value = scoreAnalysis.value.gpa;
                        credit.value = scoreAnalysis.value.credit;
                        rank.value = scoreAnalysis.value.rank.toString()+"/"+scoreAnalysis.value.total.toString();
                    }
                });
            };


            function printHtml() {
                printJS({
                    printable: 'test',
                    type: 'html'
                })
            }

            onMounted(() => {
                handleQuery({
                    userId: user.value.userId,
                });
                getScoreInformation();
            });

            return {
                param,
                pagination,
                columns,
                loading,
                handleQuery,
                user,
                course_scores,
                scoreAnalysis,
                rank,
                credit,
                gpa,
                userId,
                userName,

            }
        }
    });
</script>
