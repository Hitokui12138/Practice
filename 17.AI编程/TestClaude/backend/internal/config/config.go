package config

import (
	"log"
	"os"

	"github.com/joho/godotenv"
)

type Config struct {
	ChasterAPIURL  string
	BearerToken    string
}

func Load() *Config {
	err := godotenv.Load()
	if err != nil {
		log.Println("Warning: .env file not found, using environment variables")
	}

	return &Config{
		ChasterAPIURL: getEnv("CHASTER_API_URL", "https://api.chaster.app"),
		BearerToken:   getEnv("CHASTER_BEARER_TOKEN", ""),
	}
}

func getEnv(key, defaultValue string) string {
	value := os.Getenv(key)
	if value == "" {
		return defaultValue
	}
	return value
}
