# [React-admin](https://marmelab.com/react-admin/) SpringBoot 客户管理系统

    ⏬⏬⏬ English Description Below ⏬⏬⏬

### 项目描述:

一个 CRM 后台管理系统，灵感来自 “[React-admin](https://marmelab.com/react-admin/) 的官方 [CRM demo 项目](https://marmelab.com/react-admin-demo/)”，本项目按照其对于 api、数据的需求设计编写的，完全实现 Demo 的所有功能。前端源码来自 React-admin，重写用户登录 api、权限控制方面内容。可通过 [Demo 项目](https://marmelab.com/react-admin-demo/) 本身预览（需要梯子）。

### 技术栈:

    SringBoot + Spring Security 项目
    语言：Java (前端：Typescript React)
    ORM 相关: JPA(Hibernate) + Mybatis
    数据库: MySQL
    额外依赖项：Lombok, OpenAPI

### 功能亮点：

1. 整合 [Mybatis](https://mybatis.org/mybatis-3/index.html)：

   i) 使用 Mybatis 提供的 [`Dynamic SQL`](https://mybatis.org/mybatis-3/dynamic-sql.html#dynamic-sql) API，按不同条件查询数据;

2. MySQL:

   i) "搜索功能"运用 Mysql [`Pattern Matching`](https://dev.mysql.com/doc/refman/8.0/en/pattern-matching.html) 用于 query 不同表格中的关键词，达到全文搜索的效果，举例：LIKE '%${keyword}%'；

   ii) 运用 mysql 提供的 `GROUP_CONCAT` 函数，对“一对多”关系数据进行 query（[StackOverflow 相关提问](https://stackoverflow.com/questions/72455204/mysql-select-distinct-user-with-each-of-their-own-preference)）；

3. Spring Security:

   i) 对本项目设计的 REST api 进行`用户角色授权（authorization）和认证（authentication）保护`，用户角色分为三种，且每种用户设置有不同的权限。

4. 前端登录逻辑：

   i) 因前端源码并无提供一套完整的真实登录逻辑，所以重写了用户登录方面的代码（[StackOverflow 相关提问](https://stackoverflow.com/questions/72637511/react-admin-unable-to-include-credtials-in-dataprovider-with-typescript)）。

### 另一个类似的项目：

[React-admin SpringBoot 电商管理系统](https://github.com/Enfield-Li/React_Admin_SpringBoot_Backend)

# A Springboot project for [React-admin](https://marmelab.com/react-admin/) CRM

### Description:

A CRM management system. Inspired by the "[React-admin](https://marmelab.com/react-admin/)'s official [CRM demo project](https://marmelab.com/react-admin-crm/)", and based on it's requirement to API and data, this backend project is designed and written in SpringBoot to support all of it's functionalities. The frontend source code is from React-admin, with some of user login api and resource control related section reworte. [Demo for preview](https://marmelab.com/react-admin-crm/).

### Stacks:

    A SpringBoot + Spring Security project
    Language: Java (Frontend: Typescript React)
    ORM implementation: JPA(Hibernate) + Mybatis
    DB: MySQL
    Additional dependencies：Lombok, OpenAPI

### Implementation highlight：

1. Integrate [Mybatis](https://mybatis.org/mybatis-3/index.html)：

   i) Using [`Dynamic SQL`](https://mybatis.org/mybatis-3/dynamic-sql.html#dynamic-sql) API provided by Mybatis, in order to conditionally query data;

2. MySQL:

   i) "Search functionalities" utilize Mysql [`Pattern Matching`](https://dev.mysql.com/doc/refman/8.0/en/pattern-matching.html) in order to query based on certain keyword accross different tables, for instance: LIKE '%${keyword}%';
   ii) Utilize `GROUP_CONCAT` function provided in order to query one-to-many relational data ([Relavent StackOverflow question](https://stackoverflow.com/questions/72455204/mysql-select-distinct-user-with-each-of-their-own-preference))

3. Spring Security:

   i) Securing this project's REST api through `role-based authorization and authentication`. There're three user roles, and each have their own access control specified.

4. Frontend login logic:

   i) The source code doesn't come with a real world login logic, therefore, that part of code had been reworte ([Relavent StackOverflow question](https://stackoverflow.com/questions/72637511/react-admin-unable-to-include-credtials-in-dataprovider-with-typescript)).

### Another similar project:

[A Springboot E-commerce project for React-admin](https://github.com/Enfield-Li/React_Admin_SpringBoot_Backend)
