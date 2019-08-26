var data = [
  {
    name: "bubblesort",
    values: [
      { size: 10000, time: 266 },
      { size: 20000, time: 1121 },
      { size: 30000, time: 2213 },
      { size: 50000, time: 6321 },
      { size: 75000, time: 14753 },
      { size: 100000, time: 25809 },
      { size: 120000, time: 36714 },
      { size: 150000, time: 57382 }
    ]
  },
  {
    name: "insertionsort",
    values: [
      { size: 10000, time: 28 },
      { size: 20000, time: 87 },
      { size: 30000, time: 156 },
      { size: 50000, time: 442 },
      { size: 75000, time: 995 },
      { size: 100000, time: 1753 },
      { size: 120000, time: 2543 },
      { size: 150000, time: 4011 }
    ]
  },
  {
    name: "selectionsort",
    values: [
      { size: 10000, time: 204 },
      { size: 20000, time: 697 },
      { size: 30000, time: 1488 },
      { size: 50000, time: 4292 },
      { size: 75000, time: 9792 },
      { size: 100000, time: 17348 },
      { size: 120000, time: 24654 },
      { size: 150000, time: 38735 }
    ]
  },
  {
    name: "mergesort",
    values: [
      { size: 10000, time: 9 },
      { size: 20000, time: 8 },
      { size: 30000, time: 12 },
      { size: 50000, time: 21 },
      { size: 75000, time: 24 },
      { size: 100000, time: 25 },
      { size: 120000, time: 32 },
      { size: 150000, time: 39 }
    ]
  },
  {
    name: "quicksort",
    values: [
      { size: 10000, time: 4 },
      { size: 20000, time: 4 },
      { size: 30000, time: 4 },
      { size: 50000, time: 10 },
      { size: 75000, time: 11 },
      { size: 100000, time: 20 },
      { size: 120000, time: 18 },
      { size: 150000, time: 23 }
    ]
  }
];

var width = 900;
var height = 700;
var margin = 50;
var duration = 250;

var lineOpacity = "1";
var lineOpacityHover = "0.85";
var otherLinesOpacityHover = "0.1";
var lineStroke = "1.5px";
var lineStrokeHover = "2.5px";

var circleOpacity = "0.85";
var circleOpacityOnLineHover = "0.25";
var circleRadius = 3;
var circleRadiusHover = 6;

/* Format Data */
// var parseDate = d3.timeParse("%Y");
// data.forEach(function(d) {
// 	d.values.forEach(function(d) {
// 		d.time = Math.floor(d.time / 1000);
// 	});
// });

console.log(data);
/* Scale */
var xScale = d3
  .scaleLinear()
  .domain([0, d3.max(data[0].values, d => d.size)])
  .range([0, width - margin]);

var yScale = d3
  .scaleLinear()
  .domain([0, 3000])
  .range([height - margin, 0]);

var color = d3.scaleOrdinal(d3.schemeCategory10);

/* Add SVG */
var svg = d3
  .select("#chart")
  .append("svg")
  .attr("width", width + margin + "px")
  .attr("height", height + margin + "px")
  .append("g")
  .attr("transform", `translate(${margin}, ${margin})`);

/* Add line into SVG */
var line = d3
  .line()
  .x(d => xScale(d.size))
  .y(d => yScale(d.time))
  .curve(d3.curveMonotoneX);

let lines = svg.append("g").attr("class", "lines");

lines
  .selectAll(".line-group")
  .data(data)
  .enter()
  .append("g")
  .attr("class", "line-group")
  .on("mouseover", function(d, i) {
    svg
      .append("text")
      .attr("class", "title-text")
      .style("fill", color(i))
      .text(d.name)
      .attr("text-anchor", "middle")
      .attr("x", (width - margin) / 2)
      .attr("y", 5);
  })
  .on("mouseout", function(d) {
    svg.select(".title-text").remove();
  })
  .append("path")
  .attr("class", "line")
  .attr("id", (d, i) => d.name)
  .attr("d", d => line(d.values))
  .style("stroke", (d, i) => color(i))
  .style("opacity", lineOpacity)
  .on("mouseover", function(d) {
    d3.selectAll(".line").style("opacity", otherLinesOpacityHover);
    d3.selectAll(".circle").style("opacity", circleOpacityOnLineHover);
    d3.select(this)
      .style("opacity", lineOpacity)
      .style("stroke-width", lineStroke)
      .style("cursor", "pointer");
  })
  .on("mouseout", function(d) {
    d3.selectAll(".line").style("opacity", lineOpacity);
    d3.selectAll(".circle").style("opacity", circleOpacity);
    d3.select(this)
      .style("stroke-width", lineStroke)
      .style("cursor", "none");
  });

/* Add circles in the line */
lines
  .selectAll("circle-group")
  .data(data)
  .enter()
  .append("g")
  .style("fill", (d, i) => color(i))
  .selectAll("circle")
  .data(d => d.values)
  .enter()
  .append("g")
  .attr("class", "circle")
  .on("mouseover", function(d) {
    d3.select(this)
      .style("cursor", "pointer")
      .append("text")
      .attr("class", "text")
      .text(`${d.time}`)
      .attr("x", d => xScale(d.size) + 5)
      .attr("y", d => yScale(d.time) - 10);
  })
  .on("mouseout", function(d) {
    d3.select(this)
      .style("cursor", "none")
      .transition()
      .duration(duration)
      .selectAll(".text")
      .remove();
  })
  .append("circle")
  .attr("cx", d => xScale(d.size))
  .attr("cy", d => yScale(d.time))
  .attr("r", circleRadius)
  .style("opacity", circleOpacity)
  .on("mouseover", function(d) {
    d3.select(this)
      .transition()
      .duration(duration)
      .attr("r", circleRadiusHover);
  })
  .on("mouseout", function(d) {
    d3.select(this)
      .transition()
      .duration(duration)
      .attr("r", circleRadius);
  });

/* Add Axis into SVG */
var xAxis = d3.axisBottom(xScale).ticks(20);
var yAxis = d3.axisLeft(yScale).ticks(20);

svg
  .append("g")
  .attr("class", "x axis")
  .attr("transform", `translate(0, ${height - margin})`)
  .call(xAxis);

svg
  .append("g")
  .attr("class", "y axis")
  .call(yAxis)
  .append("text")
  .attr("y", 15)
  .attr("transform", "rotate(-90)")
  .attr("fill", "#000")
  .text("Total values");

const legend = svg
  .append("g")
  .attr("class", "legend")
  .attr("transform", `translate(${margin}, ${margin})`);

const lg = legend
  .selectAll("g")
  .data(data)
  .enter()
  .append("g")
  .attr("transform", (d, i) => `translate(${i * 100},${height})`);

lg.append("rect")
  .style("fill", (d, i) => color(i))
  .attr("x", 0)
  .attr("y", 0)
  .attr("width", 10)
  .attr("height", 10);

lg.append("text")
  .style("font-family", "Georgia")
  .style("font-size", "13px")
  .style("cursor", "pointer")
  .attr("x", 17.5)
  .attr("y", 10)
  .text(d => d.name)
  .on("mouseover", function(d) {
    d3.selectAll(".line").style("opacity", otherLinesOpacityHover);
    d3.selectAll(".circle").style("opacity", circleOpacityOnLineHover);
    d3.select("#" + d.name)
      .style("opacity", lineOpacityHover)
      .style("stroke-width", lineStrokeHover)
      .style("cursor", "pointer");
  })
  .on("mouseout", function(d) {
    d3.selectAll(".line").style("opacity", lineOpacity);
    d3.selectAll(".circle").style("opacity", circleOpacity);
    d3.select("#" + d.name)
      .style("stroke-width", lineStroke)
      .style("cursor", "none");
  });

let offset = 0;
lg.attr("transform", function(d, i) {
  let x = offset;
  offset += this.getBBox().width + 10;
  return `translate(${x},${height - 35})`;
});

legend.attr("transform", function() {
  return `translate(${margin},${15})`;
});
