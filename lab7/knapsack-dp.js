const testcases = require('./testcases');

testcases.slice(0,1).forEach(tc => {
  let maxProfit = getMaxProfit(tc)
  console.log(tc)
  console.log(maxProfit)
  console.log("---------------------------")
});

function getMaxProfit(tc) {
  const { items, w: cap } = tc
  table = []
  for (let i = 0; i <= items.length; i++)
    table[i] = new Array(cap + 1).fill(0)
  
  for ( let n = 0; n <= items.length; n++) {
    for (let w = 0; w <= cap; w++) {
      if( n === 0 || w === 0 ) table[n][w] = 0
      else if(items[n-1].weight <= w) {
        const { profit, weight } = items[n-1]
        table[n][w] = Math.max(profit + table[n-1][w-weight], table[n-1][w])
      }else
        table[n][w] = table[n-1][w]
    }
  }
  for ( let n = 0; n <= items.length; n++) {
    console.log(table[n].join(','));
  }
  return table[items.length][cap]
}