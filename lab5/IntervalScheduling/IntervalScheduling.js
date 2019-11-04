const fs    = require('fs')
const path  = require('path')

const dirPath = path.join(__dirname, 'testcases')

fs.readdir(dirPath, (err, fileNames) => {
  fileNames.forEach((fileName) => {
    const filePath = path.join(dirPath, fileName)
    fs.readFile(filePath, 'ascii', (err, data) => {
      let schedule = getSchedule(data)
      console.log(schedule);
    })
  })
})

function getSchedule(data) {
  data = data.split('\n')
  let jobs = data.map(job => 
    job.split(/\s+/).map(j => parseInt(j, 10))
  )
  jobs = jobs.reduce((acc, cur) => {
    return [...acc, {st: cur[0], ft:cur[1] } ]
  },[])
  jobsSorted = jobs.sort((first, second) => {
    if(first.ft > second.ft) return 1
    else if(first.ft > second.ft) return -1
    else return 0
  })
  console.log(jobsSorted);
  return scheduleThem(jobsSorted) 
}

function scheduleThem(jobsSorted) {
  let schedule = [];
  return jobsSorted.reduce((acc, cur) => {
    if(!acc.length) return [cur]
    const lastJob = acc[acc.length - 1]
    if(isCompatible(lastJob, cur)) return [...acc, cur]
    return acc
  }, [])
}

function isCompatible(last, cur) {
  return cur.st >= last.ft
}