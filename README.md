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
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


## Getting Started

Please make sure you follow the steps, steo by step!

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
