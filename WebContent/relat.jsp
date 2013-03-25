<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="https://github.com/favicon.ico"
	type="image/x-icon">
<title>relation path</title>
<script src="http://d3js.org/d3.v3.min.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
<style>
.node {
	/*  stroke: #fff;
  stroke-width: 1.5px;*/
	
}

.link {
	/*  fill: none;
  stroke: #bbb;*/
	fill: none;
	/*  stroke: #666;*/
	stroke-width: 0.5px;
}

text {
	pointer-events: none;
	/*  font: 12px normal;*/
	font: 12px normal "宋体", Arial, Times;
	fill: white;
}
</style>

</head>
<body>
	
	<input type="text" id="iuid" >
	<input type="button"  onclick="getData2();"> 

	<script type="text/javascript">
		function getData2() {
			var push = document.getElementById("iuid").value;
			alert(push);
			push = {
				push : push
			};
			var request = $.ajax({
						type : "POST",
						url : '/weiboRelationPic/RelationPath',
						data : push,
						dataType : "JSON",
						success : function(data) {
							alert(data);
							console.log(data);

							//use d3
							var width = 1260, height = 1000;

							var color = d3.scale.category20();

							var force = d3.layout.force().linkDistance(120)
									.linkStrength(2).charge(-250).size(
											[ width, height ]);

							var svg = d3.select("body").append("svg").attr(
									"width", width).attr("height", height);

							//#292929
							d3.select("body").transition().style(
									"background-color", "grey");

							//word:
							d3.select("body").append("span").text("Hello, cr!");

							var graph = data;

							var nodes = graph.nodes.slice(), links = [], bilinks = [];

							graph.links
									.forEach(function(link) {
										var s = nodes[link.source], t = nodes[link.target], i = {}, v = link.value; // intermediate node
										nodes.push(i);
										links.push({
											source : s,
											target : i
										}, {
											source : i,
											target : t
										});
										bilinks.push([ s, i, t, v ]);
									});

							force.nodes(nodes).links(links).start();

							var link = svg.selectAll(".link").data(bilinks)
									.enter().append("path").attr("class",
											"link").style("stroke",
											function(d) {
												//console.log(d);
												if (d[3] != null) {
													if (d[3] == "1") {
														//	console.log(d[3]);
														return color("1");
													} else {
														return color("2");
													}
												}
												;
											});

							//		link.style("stroke",function(d) {
							//			return color(d.value)
							//		});

							var node = svg.selectAll(".node").data(graph.nodes)
									.enter().append("g").attr("class", "node")
									.call(force.drag);

							//小圆   2.5-7.5
							node.append("circle").attr("class", "node").attr(
									"r", function(d) {
										console.log(d.count / 2);
										if (d.count / 2 < 2) {
											return 2.5;
										} else {
											return d.count / 2;
										}
									}).style("fill", function(d) {
								//			console.log(d);
								//			return color(d.group);
								return color(d.count + "");
							});
							//小图  like,github
							//		node.append("image")
							//			.attr("xlink:href", "https://github.com/favicon.ico")
							//		 	.attr("x", -8)
							//			.attr("y", -8)
							//			.attr("width", 16)
							//			.attr("height", 16);

							node.append("text").attr("dx", 12).attr("dy",".35em").attr("class", "text").text(
									function(d) {
										return d.name;
									});

							force.on("tick", function() {
								link.attr("d", function(d) {
									return "M" + d[0].x + "," + d[0].y + "S"
											+ d[1].x + "," + d[1].y + " "
											+ d[2].x + "," + d[2].y;
								});
								node.attr("transform",
										function(d) {
											return "translate(" + d.x + ","	+ d.y + ")";
										});
							});

						}
					});

		}
	</script>

</body>
</html>