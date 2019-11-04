const fs 		= require('fs')
const path 	= require('path')
const chalk = require('chalk')

function getFreeSlot(deadLine, slots) {
	if(slots[deadLine] === 0) return -1
  if(deadLine === slots[deadLine]) {
    slots[deadLine] = slots[deadLine] - 1
    return deadLine
  } else return getFreeSlot(slots[deadLine], slots)
}

function scheduleTheJobs(jobs) {
  let maxDeadLine = jobs.reduce((acc, cur) => {
    return cur[1] > acc ? cur[1] : acc
	}, 0)

	let jobsMap = jobs.reduce((acc, cur) => {
		return {...acc,[cur[0]]: {deadline: cur[1],profit: cur[2]}}
	}, {})

  let freeSlots = {}
  for(let i = 0; i < maxDeadLine + 1; i++) freeSlots = {...freeSlots, [i]:i}

	let schedule = new Array(maxDeadLine + 1)

  jobs.forEach(job => {
		let slot = getFreeSlot(job[1], freeSlots)
		if(slot !== -1)
			schedule[slot] = job[0]
  });

	schedule = schedule.filter(x => x)
	let profits = schedule.reduce((acc, cur) => {
		return [...acc, jobsMap[cur].profit]
	},[])
	return [schedule, profits]
}

function print(jobs) {
	jobs.forEach(element => {
		console.log(chalk.blue(element))
	})
}

function getSchedule(data) {
	data = data.split('\n')

	let n = parseInt(data[0], 10)
	
	print(data.slice(1))
	
	let jobs = data.slice(1).reduce((acc, cur) => {
		return [...acc, cur.split(/\s+/).map(x => parseInt(x, 10))]
	},[])
	

  let jobsSortByPrft = jobs.slice(0).sort((first, second) => {
		// return -1 to 
		// put the first one on the lesser index than the second 
		if(first[2] > second[2]) return -1
    else if(first[2] < second[2]) return 1
    else return 0
	})

	let answer = scheduleTheJobs(jobsSortByPrft);
	
	console.log(answer[0].join('\t'))
	console.log(answer[1].join('\t'))
	
	const profit = answer[1].reduce((acc, cur) => {
		return acc + cur
	}, 0)
	console.log(chalk.red(profit))
}

const dirPath = path.join(__dirname, 'testcases')
fs.readdir(dirPath, (err, files) => {
	if(err)
		return console.log('failed: ' + err)
	
	files.forEach((file) => {
		const filePath = path.join(dirPath, file)
		fs.readFile(filePath, 'ascii', (err, data) => {
			if(err)
				return console.log('failed: ' + err)
			getSchedule(data)
			console.log('-------------------------------')
		})
	})
})

// console.log('\x1b[36m%s\x1b[0m', 'I am cyan');  //cyan
// console.log('\x1b[33m%s\x1b[0m', 'I am yellow');  //yellow