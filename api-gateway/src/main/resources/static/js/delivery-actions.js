let orderUpdateButton = document.getElementById("orderUpdateButton")

orderUpdateButton.addEventListener('click', onUpdateOrder)

if(orderUpdateButton.name === 'DELIVERED') {
    orderUpdateButton.style.display = 'none'
}

async function onUpdateOrder(event) {
    event.preventDefault()

    const orderId = location.pathname.substring(25)
    const status = event.target.name;

    if (status === 'REGISTERED') {
        const url = "http://localhost:8082/eataway/delivery/orders/take"

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/text"
            },
            body: orderId
        })

        window.location.replace('http://localhost:8082/eataway/delivery/orders/' + orderId)
    } else {
        const newStatus = status === 'ACTIVE' ? 'ABOUT_TO_BE_DELIVERED' : 'DELIVERED'
        const url = 'http://localhost:8082/eataway/delivery/orders/' + orderId +'/update' + '?' + 'status=' + newStatus

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json"
            }
        })

        window.location.replace('http://localhost:8082/eataway/delivery/orders/' + orderId)
    }
}