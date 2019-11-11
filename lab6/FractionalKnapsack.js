
const testcases = require('./testcases')
const chalk = require('chalk')
testcases.forEach((tc) => {
  let maxProfitItems = getMaxProfit(tc)
  printItems(maxProfitItems, "red")
  let totalProfit = maxProfitItems.reduce((acc, cur)=>{
    return acc + cur.profit
  },0)
  console.log('\n' + totalProfit)
  console.log("---------------------");
})

function getMaxProfit(tc) {
  let { w, items }= tc
  console.log(chalk.blue("knapsack: " + w + "\n"))
  printItems(items, "blue")
  items = sortByProfitWeightRatio(items)
  return items.reduce((acc, cur) => {
    if(w === 0) return acc
    if(w >= cur.weight){
      w -= cur.weight
      return [...acc, cur]
    }else {
      let pw = cur.profit / cur.weight
      let profit = parseFloat((w * pw).toFixed(2))
      let weight = w
      w = 0
      return [...acc, {profit, weight, id: cur.id}]
    }
  },[])
}

function sortByProfitWeightRatio(items) {
  return items.sort((first, second) => {
    let pw1 = first.profit / first.weight
    let pw2 = second.profit / second.weight
    if(pw1 > pw2) return -1
    else if(pw1 < pw2) return 1
    else return 0
  })
}

function printItems(items, color) {
  // console.log(chalk[color]("id \t profit \t weight"))
  items.forEach(item => {
    console.log(chalk[color](item.id +"\t"+ item.profit +"\t"+ item.weight))
  })
  console.log()
}