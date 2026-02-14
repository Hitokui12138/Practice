package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/peihanggu/chaster-api/internal/service"
)

type LockHandler struct {
	service *service.ChasterService
}

type UpdateTimeRequest struct {
	Days    int `json:"days"`
	Hours   int `json:"hours"`
	Minutes int `json:"minutes"`
}

func NewLockHandler(svc *service.ChasterService) *LockHandler {
	return &LockHandler{service: svc}
}

func (h *LockHandler) GetLocks(c *gin.Context) {
	locks, err := h.service.GetLocks()
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, locks)
}

func (h *LockHandler) GetLockDetail(c *gin.Context) {
	id := c.Param("id")
	lock, err := h.service.GetLockByID(id)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, lock)
}

func (h *LockHandler) UpdateTime(c *gin.Context) {
	id := c.Param("id")

	var req UpdateTimeRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// 转换为毫秒
	duration := int64(req.Days*24*3600+req.Hours*3600+req.Minutes*60) * 1000

	lock, err := h.service.UpdateLockTime(id, duration)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, lock)
}

func (h *LockHandler) GetKeyholderLocks(c *gin.Context) {
	// 默认搜索参数
	searchReq := service.KeyholderLocksSearchRequest{
		Criteria: make(map[string]interface{}),
		Status:   "unlocked",
		Search:   "",
		Page:     0,
		Limit:    15,
	}

	// 支持查询参数覆盖
	if status := c.Query("status"); status != "" {
		searchReq.Status = status
	}
	if search := c.Query("search"); search != "" {
		searchReq.Search = search
	}

	response, err := h.service.GetKeyholderLocks(searchReq)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, response)
}
