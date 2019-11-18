const testcases = require('./testcases');

testcases.forEach(tc => {
	let maxProfit = getMaxProfit(tc)
	console.log(maxProfit)
});

function getMaxProfit(tc) {
	const { items, w } = tc
	return knapsack(items, w, 0)
}

function knapsack(items, w, n) {

	if (n === items.length || w === 0) return 0;

	if(items[n].weight > w) return knapsack(items, w, n+1);
	return Math.max(items[n].profit + knapsack(items, w-items[n].weight, n+1), knapsack(items, w, n+1))
}

// import testcases from './testcases'