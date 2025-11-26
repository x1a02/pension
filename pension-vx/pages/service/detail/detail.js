const { request } = require('../../../utils/request')

Page({
  data: {
    service: null
  },

  onLoad(options) {
    const id = options.id
    if (id) {
      this.loadServiceDetail(id)
    }
  },

  loadServiceDetail(id) {
    wx.showLoading({ title: '加载中...' })

    request({
      url: '/service/get/' + id,
      method: 'GET'
    }).then(service => {
      wx.hideLoading()
      this.setData({
        service: service
      })
    }).catch(() => {
      wx.hideLoading()
    })
  },

  handleBook() {
    wx.showToast({
      title: '预约功能开发中',
      icon: 'none'
    })
  }
})
