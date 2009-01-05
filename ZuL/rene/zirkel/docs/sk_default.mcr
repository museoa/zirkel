<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="Štandardné makrá/Os úsečky (1)">
<Parameter name="A">A - začiatočný bod úsečky</Parameter>
<Parameter name="B">B - koncový bod úsečky</Parameter>
<Comment>
<P>Zostrojí os úsečky AB.</P>
</Comment>
<Objects>
<Point name="A" mainparameter="true" x="-4.225" y="0.01250000000000018">Bod v -4.225, 0.01250000000000018</Point>
<Point name="B" mainparameter="true" x="1.4499999999999993" y="1.8125">Bod v 1.4499999999999993, 1.8125</Point>
<Circle name="k1" hidden="super" through="B" midpoint="A" acute="true">Kružnica so stredom v A cez B</Circle>
<Circle name="k2" hidden="super" through="A" midpoint="B" acute="true">Kružnica so stredom v B cez A</Circle>
<Intersection name="S1" hidden="super" first="k1" second="k2" which="second">Priesečník kružnníc k1 a k2</Intersection>
<Intersection name="S2" hidden="super" first="k2" second="k1" which="second">Priesečník kružníc k2 a k1</Intersection>
<Line name="o" target="true" from="S1" to="S2">Priamka S1S2, čo je os úsečky AB</Line>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Os uhla (priamka) (8)">
<Parameter name="A">A - bod na prvom ramene uhla</Parameter>
<Parameter name="V">V - vrchol uhla</Parameter>
<Parameter name="B">B - bod na druhom ramene uhla</Parameter>
<Comment>
<P>Zostrojí os konvexného uhla AVB ako priamku.</P>
</Comment>
<Objects>
<Point name="A" mainparameter="true" x="-5.25" y="4.59">Bod v -5.25, 4.59</Point>
<Point name="V" mainparameter="true" x="-0.875" y="-3.237500000000001">Bod v -0.875, -3.237500000000001</Point>
<Point name="B" mainparameter="true" x="3.5250000000000012" y="2.6875">Bod v 3.5250000000000012, 2.6875</Point>
<Ray name="r1" hidden="true" from="V" to="A">Polpriamka z V cez A</Ray>
<Circle name="k1" hidden="true" through="B" midpoint="V" acute="true">Kružnica so stredom V cez B</Circle>
<Intersection name="S1" hidden="true" first="r1" second="k1" which="first">Priesečník polpriamky r1 a kružnice k1</Intersection>
<Circle name="k2" hidden="super" through="B" midpoint="S1" acute="true">Kružnica so stredom S1 cez B</Circle>
<Circle name="k3" hidden="super" through="S1" midpoint="B" acute="true">Kružnica so stredom B cez S1</Circle>
<Intersection name="S2" hidden="super" first="k2" second="k3" which="second">Priesečník kružnice k2 a k3</Intersection>
<Intersection name="S3" hidden="super" first="k3" second="k2" which="second">Priesečník kružnice k3 a k2</Intersection>
<Line name="o" target="true" from="S2" to="S3">Priamka S2S3</Line>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Os uhla (polpriamka) (9)">
<Parameter name="A">A - bod na prvom ramene uhla</Parameter>
<Parameter name="V">V - vrchol uhla</Parameter>
<Parameter name="B">B - bod na druhom ramene uhla</Parameter>
<Comment>
<P>Zostrojí os konvexného uhla AVB ako polpriamku.</P>
</Comment>
<Objects>
<Point name="A" mainparameter="true" x="-4.100000000000001" y="4.26">Bod v -4.100000000000001, 4.26</Point>
<Point name="V" mainparameter="true" x="-0.5250000000000007" y="-2.6875">Bod v -0.5250000000000007, -2.6875</Point>
<Point name="B" mainparameter="true" x="3.425000000000001" y="2.7875">Bod v 3.425000000000001, 2.7875</Point>
<Ray name="r1" hidden="super" from="V" to="A">Polpriamka z V cez A</Ray>
<Circle name="k1" hidden="super" through="B" midpoint="V" acute="true">Kružnica so stredom V cez B</Circle>
<Intersection name="S1" hidden="super" first="r1" second="k1" which="first">Prieseční polpriamky r1 a kružnice k1</Intersection>
<Midpoint name="M1" hidden="super" first="S1" second="B">Stred úsečky S1B</Midpoint>
<Ray name="r2" target="true" from="V" to="M1">Polpriamka z V cez M1</Ray>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Ekvidištanta priamky daná úsečkou">
<Parameter name="A">A - bod priamky</Parameter>
<Parameter name="X">X - ďalší bod priamky</Parameter>
<Parameter name="P">P - začiatočný bod úsečky </Parameter>
<Parameter name="Q">Q - koncový bod úsečky</Parameter>
<Comment>
<P>Zostrojí ekvidištantu priamky AX vo vzdialenosti |PQ|</P>
</Comment>
<Objects>
<Point name="A" n="0" x="-2.70378" y="0.25447000000000003">Point at -2.70378, 0.25447</Point>
<Point name="X" n="1" x="5.0258400000000005" y="0.36581">Point at 5.02584, 0.36581</Point>
<Ray name="r1" n="2" hidden="true" from="A" to="X">Ray from A pointing to X</Ray>
<Point name="P" n="3" x="-0.93837" y="-3.49901">Point at -0.93837, -3.49901</Point>
<Point name="Q" n="4" x="2.061519487585587" y="-3.5247599956016153">Point at 2.06152, -3.52476</Point>
<Plumb name="p1" n="5" hidden="true" point="A" line="r1" valid="true">Perpendicular line through A to r1</Plumb>
<Circle3 name="c1" n="6" hidden="true" from="P" to="Q" midpoint="A" acute="true">Circle around A with radius from P to Q</Circle3>
<Intersection name="I1" n="7" hidden="true" first="p1" second="c1" shape="circle" which="first">Intersection between p1 and c1</Intersection>
<Parallel name="pl1" n="8" target="true" point="I1" line="r1">Parallel line through I1 to r1</Parallel>
<Intersection name="I2" n="9" hidden="true" first="p1" second="c1" shape="circle" which="second">Intersection between p1 and c1</Intersection>
<Parallel name="pl2" n="10" target="true" point="I2" line="r1">Parallel line through I2 to r1</Parallel>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/G[gama, AB]">
<Parameter name="gama">gama - Uhol</Parameter>
<Parameter name="A">A - Prvý krajný bod úsečky</Parameter>
<Parameter name="B">B - Druhý krajný bod úsečky</Parameter>
<Comment>
<P>Zostrojí množinu G(gama, AB), t.j. množinu všetkých bodov
roviny, z ktorých vidno úsečku AB pod uhlom gama.</P>
</Comment>
<Objects>
<Point name="A" n="0" x="-1.4632207753479125" y="-0.04771286282306164">Bod v -1.46322, -0.04771</Point>
<Point name="B" n="1" x="1.3359840954274351" y="-0.07952286282306177">Bod v 1.33598, -0.07952</Point>
<Expression name="gama" n="2" x="0.0" y="0.0" value="56.6818836625388" prompt="Hodnota">Výraz &quot;56.6818836625388&quot; v 0.0, 0.0</Expression>
<Segment name="s1" n="3" hidden="true" from="A" to="B">Úsečka od A do B</Segment>
<Midpoint name="M1" n="4" hidden="true" first="A" second="B" shape="circle">Stred medzi A a B</Midpoint>
<Angle name="a1" n="5" hidden="true" unit="°" first="B" root="A" fixed="gama" acute="true">Uhol B - A s veľkosťou gama</Angle>
<Plumb name="p1" n="6" hidden="true" point="M1" line="s1" valid="true">Kolmica prechádzajúca cez M1 kolmá na s1</Plumb>
<Plumb name="p2" n="7" hidden="true" showname="true" point="A" line="a1" valid="true">Kolmica prechádzajúca cez A kolmá na a1</Plumb>
<Intersection name="I1" n="8" showname="true" xcoffset="0.2842869224052573" ycoffset="0.05900743680910714" keepclose="true" target="true" first="p2" second="p1" shape="circle">Priesečník p2 a p1</Intersection>
<Circle name="c1" n="9" target="true" through="A" midpoint="I1" start="A" end="B">Kružnica so stredom I1 cez A</Circle>
<Ray name="r1" n="10" hidden="super" from="I1" to="M1">Polpriamka od I1 smerujúca k M1</Ray>
<Circle name="c2" n="11" hidden="super" through="I1" midpoint="M1" acute="true">Kružnica so stredom M1 cez I1</Circle>
<Intersection name="I2" n="12" showname="true" target="true" first="r1" second="c2" shape="circle" which="first">Priesečník r1 a c2</Intersection>
<Circle name="c3" n="13" target="true" through="A" midpoint="I2" start="B" end="A">Kružnica so stredom I2 cez A</Circle>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Osová súmernosť (5)">
<Parameter name="o">o - os súmernosti</Parameter>
<Parameter name="P">P - bod, ktorý chcete zobraziť</Parameter>
<Comment>
<P>Zostrojí obraz bodu P v osovej súmernosti podľa priamky o.</P>
</Comment>
<Objects>
<Line name="o" mainparameter="true">???</Line>
<Point name="P" mainparameter="true" x="-1.4000000000000001" y="3.34">Bod v -1.4000000000000001, 3.34</Point>
<Plumb name="l1" hidden="super" point="P" line="o" valid="true">Kolmica cez P ku o</Plumb>
<Intersection name="S1" hidden="super" first="l1" second="o">Priesečník l1 a o</Intersection>
<Circle name="k1" hidden="super" through="P" midpoint="S1" acute="true">Kružnica so stredom S1 cez P</Circle>
<Intersection name="S2" target="true" first="l1" second="k1" awayfrom="P" which="second">Priesečník l1 a k1</Intersection>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Stredová súmernosť (7)">
<Parameter name="S">S - stred súmernosti</Parameter>
<Parameter name="P">P - zobrazovaný bod</Parameter>
<Comment>
<P>Zostrojí obraz bodu P v stredovej súmernosti podľa bodu S.</P>
</Comment>
<Objects>
<Point name="S" mainparameter="true" x="0.15000000000000038" y="-0.4874999999999998">Bod v 0.15000000000000038, -0.4874999999999998</Point>
<Point name="P" mainparameter="true" x="-2.7800000000000002" y="-2.54">Bod v -2.7800000000000002, -2.54</Point>
<Ray name="r1" hidden="super" from="P" to="S">Polpriamka z P cez S</Ray>
<Circle name="k1" hidden="super" through="P" midpoint="S" acute="true">Kružnica so stredom S cez P</Circle>
<Intersection name="S1" target="true" first="r1" second="k1" awayfrom="P" which="first">Priesečník polpriamky r1 a kružnice k1</Intersection>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Otočenie (10)">
<Parameter name="A">A - bod na prvom ramene uhla</Parameter>
<Parameter name="V">V - vrchol uhla</Parameter>
<Parameter name="B">B - bod na druhom ramene uhla</Parameter>
<Parameter name="S">S - stred otáčania</Parameter>
<Parameter name="P">P - zobrazovaný bod</Parameter>
<Comment>
<P>Zobrazí obraz bodu P v otáčaní so stredom S o uhol AVB.</P>
</Comment>
<Objects>
<Point name="A" mainparameter="true" x="2.85261" y="-3.271">Bod v 2.85261, -3.271</Point>
<Point name="V" mainparameter="true" x="-3.6893819334389857" y="-3.3217115689381935">Bod v -3.6893819, -3.3217116</Point>
<Point name="B" mainparameter="true" x="2.34548" y="-0.91284">Bod v 2.34548, -0.91284</Point>
<Point name="S" mainparameter="true" x="-3.9429477020602217" y="0.6085578446909672">Bod v -3.9429477, 0.6085578</Point>
<Point name="P" mainparameter="true" x="1.20444" y="2.71315">Bod v 1.20444, 2.71315</Point>
<Angle name="w1" color="1" hidden="super" first="P" root="S" fixed="a(A,V,B)">Pevný uhol P - S s veľkosťou a(A,V,B)</Angle>
<Circle name="k1" color="1" hidden="super" through="P" midpoint="S" acute="true">Kružnica so stredom S cez P</Circle>
<Intersection name="S1" color="1" target="true" first="w1" second="k1" which="first">Priesečník w1 a k1</Intersection>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Otočenie o uhol daný veľkosťou (11)">
<Parameter name="S">S - stred otáčania</Parameter>
<Parameter name="P">P - zobrazovaný bod</Parameter>
<Comment>
<P>Zobrazí obraz bodu P v otáčaní so stredom S o uhol daný jeho veľkosťou.</P>
</Comment>
<Objects>
<Point name="S" mainparameter="true" x="-4.2218700475435815" y="-0.4817749603803483">Bod v -4.22187, -0.481775</Point>
<Point name="P" mainparameter="true" x="2.24405705229794" y="0.4564183835182245">Bod v 2.2440571, 0.4564184</Point>
<Angle name="w1" hidden="super" first="P" root="S" fixed="16">Pevný uhol P - S s veľkosťou 16</Angle>
<Circle name="k1" hidden="super" through="P" midpoint="S" acute="true">Kružnica so stredom S cez P</Circle>
<Intersection name="S1" target="true" first="w1" second="k1" which="first">Priesečník w1 a k1</Intersection>
</Objects>
<PromptFor object0="w1" prompt0="w1"/>
</Macro>
<Macro Name="Štandardné makrá/Posunutie (12)">
<Parameter name="A">A - začiatočný bod vektora posunutia</Parameter>
<Parameter name="B">B - koncový bod vektora posunutia</Parameter>
<Parameter name="P">P - zobrazovaný bod</Parameter>
<Comment>
<P>Zostrojí obraz bodu P v posunutí danom vektorom AB.</P>
</Comment>
<Objects>
<Point name="A" mainparameter="true" x="-3.35975" y="-2.86529">Bod v -3.35975, -2.86529</Point>
<Point name="B" mainparameter="true" x="1.7622820919175908" y="-1.4960380348652933">Bod v 1.7622821, -1.496038</Point>
<Point name="P" mainparameter="true" x="-3.917591125198098" y="3.1442155309033284">Bod v -3.9175911, 3.1442155</Point>
<Point name="P4" target="true" x="x(P)+x(B)-x(A)" actx="1.2044409667194929" y="y(P)+y(B)-y(A)" acty="4.513467496038035" fixed="true">Bod v &quot;x(P)+x(B)-x(A)&quot;, &quot;y(P)+y(B)-y(A)&quot;</Point>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Rovnoľahlosť s kladným koeficientom daným dĺžkou úsečky">
<Parameter name="s2">u - úsečka, ktorej dĺžka predstavuje koeficient rovnoľahlosti</Parameter>
<Parameter name="S">S - stred rovnoľahlosti</Parameter>
<Parameter name="A">A - zobrazovaný bod</Parameter>
<Comment>
<P>Zoastrojí obraz bodu A v rovnoľahlosti so stredom S a
koeficientom daným dĺžkou úsečky</P>
</Comment>
<Objects>
<Point name="S" n="0" x="-3.3399600000000005" y="-1.49503">Bod v -3.33996, -1.49503</Point>
<Point name="A" n="1" x="-1.6699801192842942" y="-1.5586481113320079">Bod v -1.66998, -1.55865</Point>
<Ray name="r1" n="2" hidden="super" from="S" to="A">Polpriamka od S smerujúca k A</Ray>
<Circle name="c1" n="3" hidden="super" fixed="1" midpoint="S" acute="true">Kružnica so stredom S s polomerom 1</Circle>
<Point name="P3" n="4" x="-4.6600397614314115" y="2.862823061630219">Bod v -4.66004, 2.86282</Point>
<Point name="P4" n="5" x="-2.576540755467197" y="2.8787276341948305">Bod v -2.57654, 2.87873</Point>
<Segment name="s2" n="6" mainparameter="true" from="P3" to="P4">Úsečka od P3 do P4</Segment>
<Circle3 name="c2" n="7" hidden="super" from="P3" to="P4" midpoint="S" acute="true">Kružnica so stredom S s polomerom od P3 do P4</Circle3>
<Plumb name="p1" n="8" hidden="super" point="S" line="r1" valid="true">Kolmica prechádzajúca cez S kolmá na r1</Plumb>
<Intersection name="I1" n="9" hidden="super" first="p1" second="c1" shape="circle" which="first">Priesečník p1 a c1</Intersection>
<Line name="l1" n="10" hidden="super" from="I1" to="A">Priamka cez I1 a A</Line>
<Intersection name="I2" n="11" hidden="super" first="p1" second="c2" shape="circle" which="first">Priesečník p1 a c2</Intersection>
<Parallel name="pl1" n="12" hidden="super" point="I2" line="l1">Rovnobežka prechádzajúca cez I2 rovnobežná s l1</Parallel>
<Intersection name="I3" n="13" target="true" first="pl1" second="r1" shape="circle">Priesečník pl1 a r1</Intersection>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Kružnicová inverzia (6)">
<Parameter name="k">k - riadiaca kružnica</Parameter>
<Parameter name="P">P - zobrazovaný bod</Parameter>
<Comment>
<P>Zostrojí obraz bodu P v kružnicovej inverzii s riadiacou kružnicou k.</P>
</Comment>
<Objects>
<Point name="P1" parameter="true" x="-0.72" y="-0.89">Bod v -0.72, -0.89</Point>
<Circle name="k" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P" mainparameter="true" x="1.45" y="1.86">Bod v 1.45, 1.86</Point>
<Ray name="r1" hidden="super" from="P1" to="P">Polpriamka z P1 do P</Ray>
<Circle name="k2" hidden="super" fixed="k^2.0/d(P,P1)" midpoint="P1" acute="true">Kružnica so stredom P1 s polomerom 6.738402</Circle>
<Intersection name="S1" target="true" first="r1" second="k2" which="first">Priesečník polpriamky r1 a kružnice k2</Intersection>
</Objects>
</Macro>
<Macro Name="Štandardné makrá/Kolmý priemet bodu na priamku (2)">
<Parameter name="p">p - priamka na ktorú sa bude premietať</Parameter>
<Parameter name="P">P - zobrazovaný bod</Parameter>
<Comment>
<P>Zostrojí kolmý priemet bodu P na priamku p.</P>
</Comment>
<Objects>
<Line name="p" mainparameter="true">???</Line>
<Point name="P" mainparameter="true" x="1.675041876046901" y="2.4656616415410397">Bod v 1.675041876046901, 2.4656616415410397</Point>
<Plumb name="l1" hidden="super" point="P" line="p" valid="true">Kolmica z P na p</Plumb>
<Intersection name="S1" target="true" first="p" second="l1">Priesečník p a l1</Intersection>
</Objects>
</Macro>
</CaR>
