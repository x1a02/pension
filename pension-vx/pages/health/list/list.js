const { request } = require('../../../utils/request')

Page({
  data: {
    elderlyId: null,
    healthRecords: []
  },

  onLoad(options) {
    const elderlyId = options.elderlyId
    if (elderlyId) {
      this.setData({ elderlyId: elderlyId })
      this.loadHealthRecords(elderlyId)
    }
  },

  loadHealthRecords(elderlyId) {
    wx.showLoading({ title: '加载中...' })

    request({
      url: '/health/list/' + elderlyId,
      method: 'GET'
    }).then(list => {
      wx.hideLoading()
      this.setData({
        healthRecords: list
      })
    }).catch(() => {
      wx.hideLoading()
    })
  }
})
