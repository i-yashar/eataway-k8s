let addMenuToBasketButtons = Array.from(document.getElementsByClassName('addMenuToBasketBtn'))
let removeMenuFromBasketButtons = Array.from(document.getElementsByClassName('removeMenuFromBasketBtn'))
let totalCost = document.getElementById('totalCostButton')
let orderForm = document.getElementById('orderForm')

addMenuToBasketButtons.forEach(button => button.addEventListener('click', onAddMenuToBasket))
removeMenuFromBasketButtons.forEach(button => button.addEventListener('click', onRemoveMenuFromBasket))

if(totalCost.textContent === 'Total: 0.00$') {
    orderForm.style.visibility = 'hidden'
}

async function onAddMenuToBasket(event) {
    event.preventDefault()

    const menuId = event.target.id.substring(10)
    const basketItem = {
        'menu': {
            'menuId': menuId
        },
        'count': 1
    }
    const url = 'http://localhost:8082/eataway/api/v1/basketItems'

    const response = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(basketItem)
    })

    alert('Menu was successfully added to your basket!')
}

async function onRemoveMenuFromBasket(event) {
    event.preventDefault()

    const menuId = event.target.id.substring(13)
    const url = "http://localhost:8082/eataway/api/v1/basketItems/" + menuId

    const response = await fetch(url, {
        method: 'DELETE'
    })

    location.reload()
    console.log('hello world')
}