<template>
    <a-layout>
        <the-admin-class-sider></the-admin-class-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <a-table
                    :columns="columns"
                    :rowKey="(record, index)=>{return index}"
                    :data-source="courseClasses"
                    :pagination="pagination"
                    :loading="loading"
                    @change="handleTableChange"
            >
                <template v-slot:action="{ text, record }">
                    <a-space size="small">
                        <a-button type="primary" @click="commitScore(record)">
                            登记分数
                        </a-button>
                        <a-button type="Default" @click="analyzeScore(record)">
                            成绩分析
                        </a-button>
                    </a-space>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>
    <a-modal
            title="登记分数"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <a-table
                :columns="columnsScore"
                :rowKey="record => record.userId"
                :data-source="courseClassStudents"
                :pagination="false"
        >
            <template v-slot:action="{ text, record }">
                <a-space size="small">
                    <a-input v-model:value="record.score" placeholder="分数"  :disabled="record.scoredStatus">
                    </a-input>
                </a-space>
            </template>
        </a-table>
    </a-modal>

    <a-modal
            title="教学班成绩分析"
            v-model:visible="analyzeModalVisible"
            :confirm-loading="analyzeModalLoading"
            width=1250px
            footer=" "

    >
        <div align="center">


                <div align="center">
                    <a-card style="width: 1100px" :bordered=false :body-style='{padding:0}' :headStyle="{ height: 6, 'text-align': 'left' }"  >
                        <div style="background-color: #ffffff; ">
                            <a-row :gutter="0">
             <pre>

             </pre>
                                <a-col :span="10">
                                    <a-card  title="成绩统计"  :bordered="false">
                                        <a-col :span="24">
                                            <div id="statistics_score" style="width: 100%;height:300px;"></div>
                                        </a-col>
                                    </a-card>
                                </a-col>
                                <pre>

    </pre>
                                <a-col :span="10">
                                    <a-card  title="成绩分段"  :bordered="false">
                                        <a-col :span="24">
                                            <div id="category_score" style="width: 100%;height:300px;"></div>
                                        </a-col>
                                    </a-card>
                                </a-col>
                            </a-row>

                        </div>
                    </a-card>
                </div>
        </div>
    </a-modal>
</template>

<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheAdminClassSider from '@/components/the-admin-class-sider.vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";

    declare let echarts: any;

    export default defineComponent({
        name: 'AdminClass',
        components: {
            TheAdminClassSider,
        },
        setup() {
            const user = computed(() => store.state.user);

            const courseClasses = ref();

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
                    title: '学分',
                    dataIndex: 'credit'
                },
                {
                    title: '学时',
                    dataIndex: 'schoolHour'
                },
                {
                    title: '班级编号',
                    dataIndex: 'classIndex'
                },
                {
                    title: '选课人数',
                    dataIndex: 'studentCount'
                },
                {
                    title: '操作',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ];

            const columnsScore = [
                {
                    title: '学号',
                    key: 'studentId',
                    dataIndex: 'studentId'
                },
                {
                    title: '名字',
                    dataIndex: 'userName'
                },
                {
                    title: '分数',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ];

            /**
             * 数据查询
             **/
            const handleQuery = (params: any) => {
                loading.value = true;
                courseClasses.value = [];
                axios.get("/teacher/class/list", {
                    params :{
                        page: params.page,
                        size: params.size,
                        teacherId: user.value.userId
                    }
                }).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        courseClasses.value = data.content.list;

                        // 重置分页按钮
                        pagination.value.current = params.page;
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
            const courseClass = ref();
            // -------- 表单 ---------
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            const handleModalOk = () => {
                modalLoading.value = true;

                axios.post("/teacher/commit/score", courseClassStudents.value).then((response) => {
                    modalLoading.value = false;
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        modalVisible.value = false;
                        message.success(data.message);
                        // 重新加载列表
                        handleQuery({
                            page: pagination.value.current,
                            size: pagination.value.pageSize,
                        });
                    } else{
                        message.error(data.message);
                    }
                });
            };

            const courseClassStudents = ref();
            /**
             * 登记分数
             */
            const commitScore = (record: any) => {

                modalVisible.value = true;
                courseClassStudents.value = [];
                courseClass.value = Tool.copy(record);
                axios.get("/teacher/courseClass/student/list", {
                    params :{
                        teacherId: user.value.userId,
                        courseUid: courseClass.value.courseUid,
                        classIndex: courseClass.value.classIndex
                    }

                }).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        courseClassStudents.value = data.content;
                    } else{
                        message.error(data.message);
                    }
                });
            };

            const analyzeModalVisible = ref(false);
            const analyzeModalLoading = ref(false);

            /**
             * 查询教学班成绩
             * @param record
             */
            const analyzeScore = (record: any) => {

                analyzeModalVisible.value = true;
                courseClass.value = Tool.copy(record);
                axios.get("/score/analysis/class", {
                    params :{
                        courseUid: courseClass.value.courseUid,
                        classIndex: courseClass.value.classIndex
                    }

                }).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        const CourseClassScores = data.content;
                        scoreEcharts(CourseClassScores);
                        categoryScoreEcharts(CourseClassScores);

                    } else{
                        message.error(data.message);
                    }
                });
            };

            const scoreEcharts = (list: any) => {
                // 基于准备好的dom，初始化echarts实例
                const averageScoreChart = echarts.init(document.getElementById('statistics_score'));

                let maxScore = list[0];
                let minScore = list[0];
                let totalScore = 0.0;
                let averageScore = 4.0;
                for (let i = 0; i < list.length; i++) {
                    const record = list[i];
                    totalScore = totalScore + record;
                    if(record>maxScore) maxScore = record;
                    if(record<minScore) minScore = record;
                }
                averageScore = totalScore / list.length;
                // 指定图表的配置项和数据
                const averageScoreOption = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['平均分', '最高分','最低分']
                    },
                    grid: {
                        left: '1%',
                        right: '3%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: ['成绩统计'],

                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                        name: '平均分',
                        type: 'bar',
                        data: [averageScore],
                            barGap:'50%'
                        },
                        {
                        name: '最高分',
                        type: 'bar',
                        data: [maxScore]
                        },
                        {
                        name: '最低分',
                        type: 'bar',
                        data: [minScore]
                        },
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                averageScoreChart.setOption(averageScoreOption);
            };


            const categoryScoreEcharts = (list: any) => {
                // 基于准备好的dom，初始化echarts实例
                const averageScoreChart = echarts.init(document.getElementById('category_score'));

                let category_60 = 0;
                let category_70 = 0;
                let category_80 = 0;
                let category_90 = 0;
                let category_100 = 0;
                for (let i = 0; i < list.length; i++) {
                    const record = list[i];

                    if(record<60)
                        category_60++;
                    else if(record<70)
                        category_70++;
                    else if(record<80)
                        category_80++;
                    else if(record<90)
                        category_90++;
                    else
                        category_100++;
                }

                // 指定图表的配置项和数据
                const categoryScoreOption = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['成绩分段'],
                    },
                    grid: {
                        left: '1%',
                        right: '3%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data:['<60', '60~70','70~80','80~90','90~100']
                    },
                    yAxis: {
                        type: 'value',
                        splitNumber : 1,
                    },
                    series: [
                        {
                            name: '成绩分段',
                            type: 'bar',
                            data: [category_60, category_70,category_80,category_90,category_100],
                        },
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                averageScoreChart.setOption(categoryScoreOption);
            };




            onMounted(() => {
                handleQuery({
                    page: 1,
                    size: pagination.value.pageSize,
                });
            });

            return {
                courseClasses,
                pagination,
                columns,
                loading,
                handleTableChange,
                handleQuery,

                courseClass,
                modalVisible,
                modalLoading,
                handleModalOk,
                commitScore,
                columnsScore,
                courseClassStudents,
                analyzeModalVisible,
                analyzeModalLoading,
                analyzeScore,
            }
        }
    });
</script>

