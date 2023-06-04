const evtSource = new EventSource("http://localhost:8082/eataway/orders/sse")

const orderInfoSeparator = document.getElementById('orderInfoSeparator')
const div = document.createElement('div')
orderInfoSeparator.parentNode.insertBefore(div, orderInfoSeparator.nextSibling)

getOrderStatusInfoMessages()

evtSource.onmessage = (event) => {
    const data = JSON.parse(event.data)

    let time = getTime()
    let infoMessage = getInfoMessage(data.status)
    let statusField = document.getElementById('orderStatusField')
    let orderId = document.getElementById('orderId').textContent

    if (data.orderId === orderId) {
        statusField.textContent = 'Status: ' + data.status
        appendOrderInfoMessage(time, infoMessage)
    }
}

async function getOrderStatusInfoMessages() {
    const orderId = location.pathname.substring(16)
    const url = 'http://localhost:8082/eataway/api/v1/orders/' + orderId + '/info'

    const response = await fetch(url, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json"
        }
    })

    let orderInfo = await response.json()

    orderInfo.forEach(o => {
        let time = (new Date(Number(o.time) * 1000)).toLocaleString()
        let infoMessage = o.infoMessage
        appendOrderInfoMessage(time, infoMessage)
    })
}

function getTime() {
    let now = new Date()
    return now.toLocaleString()
}

function getInfoMessage(status) {
    return status === 'ACTIVE' ?
        'Your order was taken by one of our employees' : status === 'ABOUT_TO_BE_DELIVERED' ?
            'Your order will be delivered in a few minutes' : 'Your order was delivered. Enjoy your food!'
}

function appendOrderInfoMessage(time, infoMessage) {
    let paragraph = document.createElement("p")
    let updateInfo = document.createTextNode(time + ' - ' + infoMessage)
    paragraph.appendChild(updateInfo)
    div.appendChild(paragraph)
}