const { request } = require('../../../utils/request')

Page({
  data: {
    elderly: null,
    healthRecords: []
  },

  onLoad(options) {
    const id = options.id
    if (id) {
      this.loadElderlyDetail(id)
      this.loadHealthRecords(id)
    }
  },

  loadElderlyDetail(id) {
    wx.showLoading({ title: '加载中...' })

    request({
      url: '/elderly/get/' + id,
      method: 'GET'
    }).then(elderly => {
      wx.hideLoading()
      this.setData({
        elderly: elderly
      })
    }).catch(() => {
      wx.hideLoading()
    })
  },

  loadHealthRecords(elderlyId) {
    request({
      url: '/health/list/' + elderlyId,
      method: 'GET'
    }).then(list => {
      this.setData({
        healthRecords: list.slice(0, 5)
      })
    }).catch(() => {})
  },

  viewAllHealthRecords() {
    wx.navigateTo({
      url: '/pages/health/list/list?elderlyId=' + this.data.elderly.id
    })
  },

  makeCall() {
    if (this.data.elderly && this.data.elderly.phone) {
      wx.makePhoneCall({
        phoneNumber: this.data.elderly.phone
      })
    }
  },

  callEmergencyContact() {
    if (this.data.elderly && this.data.elderly.emergencyPhone) {
      wx.makePhoneCall({
        phoneNumber: this.data.elderly.emergencyPhone
      })
    }
  }
})
