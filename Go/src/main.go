package main

import "net/http"
import "github.com/gin-gonic/gin"

type user struct {
	ID    string `json:"id"`
	Name  string `json:"name"`
	Email string `json:"email"`
}

// A slice of users, we don't have a DB for now.
var users = []user{
	{ID: "1", Name: "Test User", Email: "test@example.com"},
	{ID: "2", Name: "Real User", Email: "user@example.com"},
}

func getUsers(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, users)
}

func postUsers(c *gin.Context) {
	var newUser user

	if err := c.BindJSON(&newUser); err != nil {
		return
	}

	users = append(users, newUser)
	c.IndentedJSON(http.StatusCreated, newUser)
}

func main() {
	router := gin.Default()
	router.GET("/api/users", getUsers)
	router.POST("/api/users", postUsers)

	router.Run("localhost:8080")
}
