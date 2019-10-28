const fs = require('fs')

function getFreeSlot(deadLine, slots) {
  if(deadLine === slots[deadLine]) {
    slots[deadLine] = slots[deadLine] - 1
    return deadLine
  } else return getFreeSlot(slots[deadLine], slots)
}

function scheduleTheJobs(jobs) {
  let maxDeadLine = jobs.reduce((acc, cur) => {
    return cur[1] > acc ? cur[1] : acc
  }, 0)

  let freeSlots = {}
  for(let i = 0; i < maxDeadLine + 1; i++) freeSlots = {...freeSlots, [i]:i}

  let schedule = new Array(maxDeadLine + 1)

  jobs.forEach(job => {
    let slot = getFreeSlot(job[1], freeSlots)
    schedule[slot] = job[2]
  });

  return schedule.filter(x => x)

}

function getSchedule(data) {
  data = data.split('\n')
  let n = parseInt(data[0], 10)
  let jobs = data.slice(1).reduce((acc, cur) => {
    return [...acc, cur.split(' ').map(x => parseInt(x, 10))]
  },[])

  let jobsSortByPrft = jobs.slice(0).sort((first, second) => {
    if(first[2] > second[2]) return -1
    else if(first[2] < second[2]) return 1
    else return 0
  })
  
  // let freeSlots = jobsSortByPrft.reduce((acc, cur, idx) => {
  //   return {...acc, [idx]: idx}
  // },{})

  let schedule = scheduleTheJobs(jobsSortByPrft);
}

fs.readFile('1.txt', 'ascii', (err, data) => {
  getSchedule(data)
})