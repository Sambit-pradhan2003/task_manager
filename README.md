Task Manager API

This is the API documentation for the Task Manager application, which allows users to manage their tasks efficiently with authentication and role-based access.

postman url link:https://documenter.getpostman.com/view/32681241/2sAYdoFnce     

Base URL

http://localhost:8080/api

Authentication

User Registration

Endpoint: POST /users/register

Request Body:

{
    "username": "sn52",
    "email": "sn622@gmail.com",
    "password": "test",
    "role": "USER"
}

User Login

Endpoint: POST /users/login

Request Body:

{
    "username": "sn6",
    "password": "test"
}

Response:

{
    "token": "<JWT_TOKEN>"
}

Get Logged-in User

Endpoint: GET /users/me

Authorization: Bearer Token

Get User by ID

Endpoint: GET /users/{userId}

Example:

GET /users/sn6

Task Management

Create a Task

Endpoint: POST /tasks/user

Authorization: Bearer Token

Request Body:

{
  "title": "task 9",
  "description": "do category9",
  "completed": false,
  "categoryName": "work"
}

Get Tasks by User ID

Endpoint: GET /tasks/user/{userId}

Authorization: Bearer Token

Example:

GET /tasks/user/6

Update Task

Endpoint: PUT /tasks/{taskId}

Authorization: Bearer Token

Request Body:

{
    "title": "task change",
    "description": "do category1",
    "completed": false
}

Example:

PUT /tasks/4

Delete Task

Endpoint: DELETE /tasks/{taskId}

Authorization: Bearer Token

Example:

DELETE /tasks/4

Search Tasks

Endpoint: GET /tasks/search

Authorization: Bearer Token

Query Params:

title (optional)

categoryName (optional)

userId (optional)

Example:

GET /tasks/search?title=task&categoryName=work&userId=6

Toggle Task Completion

Endpoint: PUT /tasks/{taskId}/toggle

Authorization: Bearer Token

Example:

PUT /tasks/4/toggle

Get Tasks by Category ID

Endpoint: GET /tasks/category/{categoryId}

Authorization: Bearer Token

Example:

GET /tasks/category/2

Get Task by Task ID

Endpoint: GET /tasks/{taskId}

Authorization: Bearer Token

Example:

GET /tasks/4

Notes

Ensure to use the Bearer Token for all protected endpoints.

The token is received from the login API and must be included in the Authorization header.

Example Header:

Authorization: Bearer <token>

Postman Documentation

For more details, refer to the Postman documentation:
Postman Collection

