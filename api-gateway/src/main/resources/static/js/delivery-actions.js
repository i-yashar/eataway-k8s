let orderUpdateButton = document.getElementById("orderUpdateButton")

orderUpdateButton.addEventListener('click', onUpdateOrder)

if(orderUpdateButton.name === 'DELIVERED') {
    orderUpdateButton.style.display = 'none'
}

async function onUpdateOrder(event) {
    event.preventDefault()

    const orderId = location.pathname.substring(25)
    const status = event.target.name;

    console.log(event)

    if (status === 'REGISTERED') {
        const url = "http://localhost:8082/eataway/delivery/orders"

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                "Content-Type": "application/text"
            },
            body: orderId
        })

        window.location.replace('http://localhost:8082/eataway/delivery/orders/' + orderId)
    } else {
        const newStatus = status === 'ACTIVE' ? 'ABOUT_TO_BE_DELIVERED' : 'DELIVERED'
        const url = 'http://localhost:8082/eataway/delivery/orders/' + orderId + '?' + 'status=' + newStatus

        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            }
        })

        window.location.replace('http://localhost:8082/eataway/delivery/orders/' + orderId)
    }
}