const evtSource = new EventSource("http://localhost:8082/eataway/orders/sse")

const orderInfoSeparator = document.getElementById('orderInfoSeparator')
const div = document.createElement('div')
orderInfoSeparator.parentNode.insertBefore(div, orderInfoSeparator.nextSibling)

evtSource.onmessage = (event) => {
    const data = JSON.parse(event.data)

    let statusField = document.getElementById('orderStatusField')
    statusField.textContent = 'Status: ' + data.status

    let time = getTime()
    let infoMessage = getInfoMessage(data.status)

    let paragraph = document.createElement("p")
    let updateInfo = document.createTextNode(time + ' - ' + infoMessage)
    paragraph.appendChild(updateInfo)
    div.appendChild(paragraph)
}

function getTime(unixTime) {
    var now = new Date();
    return now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds()
}

function getInfoMessage(status) {
    return status === 'ACTIVE' ?
        'Your order was taken by one of our employees' : status === 'ABOUT_TO_BE_DELIVERED' ?
            'Your order will be delivered in few minutes' : 'Your order was delivered. Enjoy your food!'
}