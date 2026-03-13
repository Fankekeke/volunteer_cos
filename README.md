### 基于SpringBoot + Vue的高考志愿填报系统.

高考志愿辅助系统、智能志愿填报平台、高考择校填报系统

#### 管理员功能模块介绍：
###### 公告管理：发布高考政策、填报时间等重要通知公告。学校管理：维护全国高校基本信息，如名称、地区、类型等。专业管理：管理各高校开设的专业名称、代码及简介信息。学校专业绑定：配置每所高校对应开设的具体专业列表。学校评价：查看并审核学生对高校的评分与文字评价。学校专业分数线：录入并管理历年各专业录取最低/平均分。志愿信息：查看所有学生填报的志愿草表或历史记录。志愿申请：审核或导出学生正式提交的志愿填报申请。消息管理：向学生或学校发送系统通知与提醒消息。学生管理：管理考生账号、资料及填报状态权限。数据统计：分析热门院校、专业趋势、填报人数等数据。

#### 学校功能模块介绍：
###### 校信息修改：更新本校招生简介、联系方式、办学特色等。学校专业绑定：维护本校当年招生专业及计划人数设置。学校评价管理：查看并回复学生对本校的公开评价内容。专业分数线变更：根据招办公布数据调整各专业录取分数。志愿审核：查看报考本校的学生志愿并配合招办审核。数据统计：统计本校志愿填报热度、专业选择分布等。帖子信息：发布或管理在平台社区中的招生宣传帖文。

#### 学生功能模块介绍：
###### 用户注册：通过考生号或手机号完成系统实名注册。修改个人信息：完善或更正个人基本信息与联系方式。个人志愿管理：草拟、保存、修改或删除志愿填报方案。志愿申请：正式提交高考志愿填报申请至招办系统。学校评价：浏览或撰写对目标高校的真实体验与评价。志愿推荐：基于成绩、兴趣等获得智能匹配的院校专业建议。

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok

##### 管理员：
公告管理、学校管理、专业管理、学校专业绑定、学校评价、学校专业分数线、志愿信息、志愿申请、消息管理、学生管理、数据统计

##### 学校：
校信息修改、学校专业绑定、学校评价管理、专业分数线变更、志愿审核、数据统计、帖子信息

##### 学生：
用户注册、修改个人信息、个人志愿管理、志愿申请、学校评价、志愿推荐


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

[学校]
school
1234qwer

[学生]
fank
1234qwer
#### 项目截图

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879393958.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879527630.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725841407741.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879508089.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879633341.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879492094.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879617877.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879471554.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879591691.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879456818.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879577859.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879446178.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879568379.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879434903.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879555177.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879420137.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879545610.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1725879407528.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png) |

#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)

<p><img align="center" src="https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/%E5%90%88%E4%BD%9C%E7%89%A9%E6%96%99%E6%A0%B7%E5%BC%8F%20(3).png" alt="fankekeke" /></p>
