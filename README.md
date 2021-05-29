# apring-boot-api-security-jwt
This is a demo application for API security using spring boot and JWT

**Step1** Create API to get list of movies(unsecured)
Get below commit to which conains basic movie API to get started with security.
We will be adding security capabilities for this API in next commits.

GET http://localhost:8081/app/movies
<br>
git checkout 1a656df8e44f77582c16ff3a331b5ea2a97bb16a

**Step2** Secure API with userName and password
<br>
git checkout 5f0aabad30fe7873c02d7e2cc0026a11699522f5

**Step3** Secure API using JWT token
<br>
POST http://localhost:8081/app/auth
<br>
Request Payload:
<br>
{
    "userName": "mahesh",
    "password": "mahesh"
}
<br>
Response:
<br>
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWhlc2giLCJ1c2VyTmFtZSI6Im1haGVzaCIsImV4cCI6MTYyMjM3NjQ4NCwiaWF0IjoxNjIyMjkwMDg0fQ.GASF5udAmiJqORP6562gmJQHXmCu68C03NGtpGDqWFA"
}
<br>

Use the token in the response of /auth API to and use it as Authorization header while calling GET /app/movies API as follow
<br>
Header Name: Authorization
<br>
Header value: Bearer <token>

<br>
you can get working code from below commit<br>
git checkout bc8a240e288aacc6ff7bd7463e23f86d34dcf660
