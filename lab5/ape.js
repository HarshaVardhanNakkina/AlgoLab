let arr = new Array(10);

arr[3] = 1
arr[5] = 2
arr[8] = 3

function getNextFreeSlot(arr, curPos) {
	if(!arr[curPos]) return curPos
	else {
		let lo = 0, hi = curPos
		while(lo < hi) {
			let mid = Math.floor(lo + (hi - lo) / 2)
			if(!arr[mid]) {
				lo = mid
			}else {
				hi = mid
			}
		}
		return hi
	}
}

let curPos = 5
console.log(getNextFreeSlot(arr, curPos))