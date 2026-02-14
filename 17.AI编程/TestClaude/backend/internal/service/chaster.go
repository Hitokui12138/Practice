package service

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/peihanggu/chaster-api/internal/config"
)

type ChasterService struct {
	config *config.Config
	client *http.Client
}

type UpdateTimeRequest struct {
	Duration int64 `json:"duration"`
}

type Lock struct {
	ID                   string     `json:"_id"`
	Status               string     `json:"status"`
	StartDate            string     `json:"startDate"`
	EndDate              string     `json:"endDate"`
	MinDate              string     `json:"minDate"`
	MaxDate              string     `json:"maxDate"`
	MaxLimitDate         string     `json:"maxLimitDate"`
	TotalDuration        int64      `json:"totalDuration"`
	DisplayRemainingTime bool       `json:"displayRemainingTime"`
	LimitLockTime        bool       `json:"limitLockTime"`
	Combination          string     `json:"combination"`
	SharedLock           SharedLock `json:"sharedLock"`
	User                 User       `json:"user"`
	CreatedAt            string     `json:"createdAt"`
	UpdatedAt            string     `json:"updatedAt"`
	UnlockedAt           *string    `json:"unlockedAt"`
	ArchivedAt           *string    `json:"archivedAt"`
	FrozenAt             *string    `json:"frozenAt"`
	KeyholderArchivedAt  *string    `json:"keyholderArchivedAt"`
	AllowSessionOffer    bool       `json:"allowSessionOffer"`
	IsTestLock           bool       `json:"isTestLock"`
	OfferToken           string     `json:"offerToken"`
	HideTimeLogs         bool       `json:"hideTimeLogs"`
	Trusted              bool       `json:"trusted"`
}

type SharedLock struct {
	ID                   string      `json:"_id"`
	Name                 string      `json:"name"`
	Description          string      `json:"description"`
	IsPublic            bool        `json:"isPublic"`
	MaxLockedUsers      interface{} `json:"maxLockedUsers"`
	RequireContact      bool        `json:"requireContact"`
	RequirePassword     bool        `json:"requirePassword"`
	UnsplashPhoto       *UnsplashPhoto `json:"unsplashPhoto"`
	MinDuration         int64       `json:"minDuration"`
	MaxDuration         int64       `json:"maxDuration"`
	MaxLimitDuration    int64       `json:"maxLimitDuration"`
	DurationMode        string      `json:"durationMode"`
	Tags                []string    `json:"tags"`
	User                User        `json:"user"`
	HideTimeLogs        bool        `json:"hideTimeLogs"`
	LastSavedAt         string      `json:"lastSavedAt"`
	CreatedAt           string      `json:"createdAt"`
	UpdatedAt           string      `json:"updatedAt"`
}

type UnsplashPhoto struct {
	ID       string `json:"id"`
	Name     string `json:"name"`
	URL      string `json:"url"`
	Username string `json:"username"`
}

type User struct {
	ID          string      `json:"_id"`
	Username    string      `json:"username"`
	AvatarURL   string      `json:"avatarUrl"`
	Online      bool        `json:"online"`
	IsPremium   bool        `json:"isPremium"`
	IsFindom    bool        `json:"isFindom"`
	Age         int         `json:"age"`
	Description string      `json:"description"`
	Gender      string      `json:"gender"`
	Role        string      `json:"role"`
	Features    []string    `json:"features"`
}

type KeyholderLocksSearchRequest struct {
	Criteria map[string]interface{} `json:"criteria"`
	Status   string                 `json:"status"`
	Search   string                 `json:"search"`
	Page     int                    `json:"page"`
	Limit    int                    `json:"limit"`
}

type KeyholderLocksResponse struct {
	Pages int    `json:"pages"`
	Total int    `json:"total"`
	Locks []Lock `json:"locks"`
}

func NewChasterService(cfg *config.Config) *ChasterService {
	return &ChasterService{
		config: cfg,
		client: &http.Client{},
	}
}

func (s *ChasterService) makeRequest(method, path string, body io.Reader) (*http.Response, error) {
	url := s.config.ChasterAPIURL + path

	req, err := http.NewRequest(method, url, body)
	if err != nil {
		return nil, err
	}

	req.Header.Set("Authorization", "Bearer "+s.config.BearerToken)
	req.Header.Set("Content-Type", "application/json")
	req.Header.Set("Accept", "application/json")

	return s.client.Do(req)
}

func (s *ChasterService) GetLocks() ([]Lock, error) {
	resp, err := s.makeRequest("GET", "/locks/", nil)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		body, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("API error: %s", string(body))
	}

	var locks []Lock
	if err := json.NewDecoder(resp.Body).Decode(&locks); err != nil {
		return nil, err
	}

	return locks, nil
}

func (s *ChasterService) GetLockByID(id string) (*Lock, error) {
	resp, err := s.makeRequest("GET", "/locks/"+id, nil)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		body, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("API error: %s", string(body))
	}

	var lock Lock
	if err := json.NewDecoder(resp.Body).Decode(&lock); err != nil {
		return nil, err
	}

	return &lock, nil
}

func (s *ChasterService) UpdateLockTime(id string, duration int64) (*Lock, error) {
	reqBody := UpdateTimeRequest{Duration: duration}
	jsonBody, err := json.Marshal(reqBody)
	if err != nil {
		return nil, err
	}

	resp, err := s.makeRequest("POST", "/locks/"+id+"/update-time", bytes.NewBuffer(jsonBody))
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		body, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("API error: %s", string(body))
	}

	var lock Lock
	if err := json.NewDecoder(resp.Body).Decode(&lock); err != nil {
		return nil, err
	}

	return &lock, nil
}

func (s *ChasterService) GetKeyholderLocks(searchReq KeyholderLocksSearchRequest) (*KeyholderLocksResponse, error) {
	jsonBody, err := json.Marshal(searchReq)
	if err != nil {
		return nil, err
	}

	resp, err := s.makeRequest("POST", "/keyholder/locks/search", bytes.NewBuffer(jsonBody))
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	// Chaster API 返回 201 而不是 200
	if resp.StatusCode != http.StatusOK && resp.StatusCode != http.StatusCreated {
		body, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("API error (status %d): %s", resp.StatusCode, string(body))
	}

	var response KeyholderLocksResponse
	if err := json.NewDecoder(resp.Body).Decode(&response); err != nil {
		return nil, err
	}

	return &response, nil
}
