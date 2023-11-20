<a name="readme-top"></a>
# CarWash - Semi RESTful/Thymeleaf site.

<br />
<div align="center">
  <a href="https://github.com/Borovaneca/CarWash">
    <img src="src/main/resources/static/images/logo.png" alt="Logo" width="80" height="80">
  </a>
    <h2>CarWash</h2>
  <p>
<!--     <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a> -->
    <a href="https://github.com/Borovaneca/CarWash/issues">Report Bug</a>
    ·
    <a href="https://github.com/Borovaneca/CarWash/issues">Request Feature</a>
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#perequisites">Prerequisites</a>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a>
      <ul>
        <li><a href="#admin">Admin</a></li>
      </ul>
      <ul>
        <li><a href="#manager">Manager</a></li>
      </ul>
      <ul>
        <li><a href="#employee">Employee</a></li>
      </ul>
      <ul>
        <li><a href="#user">Employee</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">REST Tests</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


## Getting Started

Please make sure you follow the steps, step by step!

### Prerequisites

* JDK 17
* Apache Tomcat/10.1.15
* Depending on the features you want to use, you may need some third-party software, such as [DataGrip](https://www.jetbrains.com/datagrip/download/#section=windows) (payed) or [MySQL](https://dev.mysql.com/downloads/workbench/) Workbench (free) for data modeling, SQL development, and comprehensive administration for the system data.

### Installation

_In order to run CarWash you need to:_

1. <a href="https://github.com/Borovaneca/CarWash/archive/refs/heads/master.zip">DOWNLOAD</a> the repo.
2. Set up environment variable `${MYYSQL_PORT}` 
   ```yaml
   spring.datasource.url: jdbc:mysql://localhost:${MYSQL_PORT}/carwash?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC
   spring.datasource.username: ${MYSQL_USER}
   spring.datasource.password: ${MYSQL_PASSWORD}
   admin.username: ${ADMIN_USERNAME}
   admin.password: ${ADMIN_PASSWORD}
   ```
3. Enjoy the application!

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## About The Project

![project-index](src/main/resources/static/images/project/index.png)
_The CarWash project is a Spring Boot-based application designed to streamline car cleaning services. It offers users the convenience of registering, managing their vehicles, and scheduling appointments for various car cleaning services._

### Built With

* ![JavaScript](https://img.shields.io/badge/JavaScript-ECDB6F)
* ![Bootstrap](https://img.shields.io/badge/Bootstrap-850EF6)
* ![HTML](https://img.shields.io/badge/HTML-F17545)
* ![CSS](https://img.shields.io/badge/CSS-2964F2)
* ![Java](https://img.shields.io/badge/Java-ED4236)
* ![MySQL](https://img.shields.io/badge/My-SQL-F5921B)
* ![SpringSecurity](https://img.shields.io/badge/Spring-Security-%23D43534)
* ![SpringBoot](https://img.shields.io/badge/Spring-Boot-%236BB13D)
* ![SpringDataJPA](https://img.shields.io/badge/Spring-DataJPA-%236BB13D)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage
_Here you can find all the information about the roles in the applicaтion and what they can do._

### Admin

_Only the users with role Admin can change others users roles ban them!_

![admin-view](src/main/resources/static/images/project/admin.png)

### Manager

_This role has the authority to approve/decline the appointments requested by users._

![manager-view](src/main/resources/static/images/project/manager.png)

### Employee

_The role of the Employee is basically a permission to see the approved appointments by the manager, wich are for the current day!_

![employee-view](src/main/resources/static/images/project/employee.png)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## REST Tests
