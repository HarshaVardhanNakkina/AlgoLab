const testcases = require('./testcases');

testcases.forEach(tc => {
	let maxProfit = getMaxProfit(tc)
});

function getMaxProfit(tc) {
	const { items, w } = tc
	let maxProfit = 0;
	return maxProfit + knapsack(w, 0)
}

function knapsack(w, n) {
	return Math.max()
}

// import testcases from './testcases'