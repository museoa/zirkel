<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="기본 매크로/각의 이등분 반직선 (9)">
<Parameter name="P1">Point on first leg of angle</Parameter>
<Parameter name="P2">Vertex of angle</Parameter>
<Parameter name="P3">Point on second leg of angle</Parameter>
<Comment>
<P>Angle bisector of angle P1P2P3 as ray.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.100000000000001" y="4.26">좌표가 ( -4.1, 4.26 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="-0.5250000000000008" y="-2.6875">좌표가 ( -0.525, -2.6875 )인 점</Point>
<Point name="P3" n="2" mainparameter="true" x="3.425000000000001" y="2.7875">좌표가 ( 3.425, 2.7875 )인 점</Point>
<Ray name="r1" n="3" hidden="super" from="P2" to="P1">Strahl von P2 in Richtung P1</Ray>
<Circle name="k1" n="4" hidden="super" through="P3" midpoint="P2" acute="true">Kreis um P2 durch P3</Circle>
<Intersection name="S1" n="5" hidden="super" first="r1" second="k1" which="first">Schnitt zwischen r1 und k1</Intersection>
<Midpoint name="M1" n="6" hidden="super" first="S1" second="P3">Mitte zwischen S1 und P3</Midpoint>
<Ray name="r2" n="7" target="true" from="P2" to="M1">Strahl von P2 in Richtung M1</Ray>
</Objects>
</Macro>
<Macro Name="기본 매크로/각의 이등분선 (8)">
<Parameter name="P1">Point on first leg of angle</Parameter>
<Parameter name="P2">Vertex of angle</Parameter>
<Parameter name="P3">Point on second leg of angle</Parameter>
<Comment>
<P>Angle bisector of angle P1P2P3 as line.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-5.25" y="4.59">좌표가 ( -5.25, 4.59 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="-0.875" y="-3.237500000000001">좌표가 ( -0.875, -3.2375 )인 점</Point>
<Point name="P3" n="2" mainparameter="true" x="3.5250000000000012" y="2.6875">좌표가 ( 3.525, 2.6875 )인 점</Point>
<Ray name="r1" n="3" hidden="true" from="P2" to="P1">Strahl von P2 in Richtung P1</Ray>
<Circle name="k1" n="4" hidden="true" through="P3" midpoint="P2" acute="true">Kreis um P2 durch P3</Circle>
<Intersection name="S1" n="5" hidden="true" first="r1" second="k1" which="first">Schnitt zwischen r1 und k1</Intersection>
<Circle name="k2" n="6" hidden="super" through="P3" midpoint="S1" acute="true">Kreis um S1 durch P3</Circle>
<Circle name="k3" n="7" hidden="super" through="S1" midpoint="P3" acute="true">Kreis um P3 durch S1</Circle>
<Intersection name="S2" n="8" hidden="super" first="k2" second="k3" which="second">Schnitt zwischen k2 und k3</Intersection>
<Intersection name="S3" n="9" hidden="super" first="k3" second="k2" which="second">Schnitt zwischen k3 und k2</Intersection>
<Line name="g1" n="10" target="true" from="S2" to="S3">Gerade durch S2 und S3</Line>
</Objects>
</Macro>
<Macro Name="기본 매크로/선대칭이동 (5)">
<Parameter name="g1">Reflection line</Parameter>
<Parameter name="P3">Reflected point</Parameter>
<Comment>
<P>Reflect P at g.</P>
</Comment>
<Objects>
<Line name="g1" n="0" mainparameter="true">???</Line>
<Point name="P3" n="1" mainparameter="true" x="-1.4000000000000001" y="3.34">좌표가 ( -1.4, 3.34 )인 점</Point>
<Plumb name="l1" n="2" hidden="super" point="P3" line="g1" valid="true">Lot durch P3 zu g1</Plumb>
<Intersection name="S1" n="3" hidden="super" first="l1" second="g1">Schnitt zwischen l1 und g1</Intersection>
<Circle name="k1" n="4" hidden="super" through="P3" midpoint="S1" acute="true">Kreis um S1 durch P3</Circle>
<Intersection name="S2" n="5" target="true" first="l1" second="k1" awayfrom="P3" which="second">Schnitt zwischen l1 und k1</Intersection>
</Objects>
</Macro>
<Macro Name="기본 매크로/수선의 발 (2)">
<Parameter name="g1">Line to project to</Parameter>
<Parameter name="P3">Projected point</Parameter>
<Comment>
<P>Projects P to the line g.</P>
</Comment>
<Objects>
<Line name="g1" n="0" mainparameter="true">???</Line>
<Point name="P3" n="1" mainparameter="true" x="1.675041876046901" y="2.46566164154104">좌표가 ( 1.67504, 2.46566 )인 점</Point>
<Plumb name="l1" n="2" hidden="super" point="P3" line="g1" valid="true">Lot durch P3 zu g1</Plumb>
<Intersection name="S1" n="3" target="true" first="g1" second="l1">Schnitt zwischen g1 und l1</Intersection>
</Objects>
</Macro>
<Macro Name="기본 매크로/수직이등분선 (1)">
<Parameter name="P1">First endpoint of segment</Parameter>
<Parameter name="P2">Second endpoint of segment</Parameter>
<Comment>
<P>Middle perpendicular line of P1 and P2.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.225" y="0.012500000000000181">좌표가 ( -4.225, 0.0125 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="1.4499999999999993" y="1.8125">좌표가 ( 1.45, 1.8125 )인 점</Point>
<Circle name="k1" n="2" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Circle name="k2" n="3" hidden="super" through="P1" midpoint="P2" acute="true">Kreis um P2 durch P1</Circle>
<Intersection name="S1" n="4" hidden="super" first="k1" second="k2" which="second">Schnitt zwischen k1 und k2</Intersection>
<Intersection name="S2" n="5" hidden="super" first="k2" second="k1" which="second">Schnitt zwischen k2 und k1</Intersection>
<Line name="g1" n="6" target="true" from="S1" to="S2">Middle perpendicular of S1 and S2</Line>
</Objects>
</Macro>
<Macro Name="기본 매크로/슬라이더">
<Parameter name="P1">Slider Start</Parameter>
<Parameter name="P2">Slider End</Parameter>
<Comment>
<P>Slider to set a value in [a,b]. The value can be used later
(rename the expression). a and b are asked during macro
run.</P>
</Comment>
<Objects>
<Expression name="AD1" n="0" color="5" hidden="super" showvalue="true" x="2.666670000000001" y="2.0" value="0" prompt="Wert">식 &quot; 0 &quot; at 2.66667, 2.0</Expression>
<Expression name="AD2" n="1" color="5" hidden="super" showvalue="true" x="2.68852" y="1.4535500000000001" value="10" prompt="Wert">식 &quot; 10 &quot; at 2.68852, 1.45355</Expression>
<Point name="P1" n="2" mainparameter="true" x="-0.9617486338797817" y="1.3224043715847003">좌표가 ( -0.96175, 1.3224 )인 점</Point>
<Point name="P2" n="3" mainparameter="true" x="1.4863400000000007" y="3.77049">좌표가 ( 1.48634, 3.77049 )인 점</Point>
<Segment name="s1" n="4" target="true" from="P1" to="P2">Segment from P1 to P2</Segment>
<PointOn name="OP3" n="5" type="thick" target="true" on="s1" alpha="0.20682542521125796" x="-0.4554216612227482" y="1.8287307226352643" shape="circle">s1 위의 점</PointOn>
<Circle name="k1" n="6" hidden="super" fixed="d(P1,P2)/10" midpoint="OP3" acute="true">Circle around OP3 with Radius 0.3462118</Circle>
<PointOn name="OP4" n="7" type="invisible" hidden="super" on="k1" alpha="-1.017350822361835" x="-0.2734452952243154" y="1.534201922257689" shape="circle">k1 위의 점</PointOn>
<Expression name="AD3" n="8" color="5" showvalue="true" x="x(OP4)" y="y(OP4)" value="AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1)" prompt="Wert" fixed="true">식 &quot; AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1) &quot; at 0.53516, 0.22548</Expression>
</Objects>
<PromptFor object0="AD1" prompt0="a" object1="AD2" prompt1="b"/>
</Macro>
<Macro Name="기본 매크로/원에 대한 반전 (6)">
<Parameter name="k">Reflection circle</Parameter>
<Parameter name="P">Reflected point</Parameter>
<Comment>
<P>Reflect P at the circle k.</P>
</Comment>
<Objects>
<Point name="P1" n="0" parameter="true" x="-0.72" y="-0.89">좌표가 ( -0.72, -0.89 )인 점</Point>
<Circle name="k" n="1" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P" n="2" mainparameter="true" x="1.45" y="1.86">좌표가 ( 1.45, 1.86 )인 점</Point>
<Ray name="r1" n="3" hidden="super" from="P1" to="P">Strahl von P1 in Richtung P</Ray>
<Circle name="k2" n="4" hidden="super" fixed="k^2/d(P,P1)" midpoint="P1" acute="true">Kreis um P1 mit Radius 6.738402</Circle>
<Intersection name="S1" n="5" target="true" first="r1" second="k2" which="first">Schnitt zwischen r1 und k2</Intersection>
</Objects>
</Macro>
<Macro Name="기본 매크로/점대칭이동 (7)">
<Parameter name="P1">Reflection center</Parameter>
<Parameter name="P2">Reflected point</Parameter>
<Comment>
<P>Reflection of P at the point O.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="0.15000000000000038" y="-0.4874999999999998">좌표가 ( 0.15, -0.4875 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="-2.7800000000000002" y="-2.54">좌표가 ( -2.78, -2.54 )인 점</Point>
<Ray name="r1" n="2" hidden="super" from="P2" to="P1">Strahl von P2 in Richtung P1</Ray>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Intersection name="S1" n="4" target="true" first="r1" second="k1" awayfrom="P2" which="first">Schnitt zwischen r1 und k1</Intersection>
</Objects>
</Macro>
<Macro Name="기본 매크로/좌표축">
<Parameter name="P1">Zentrum</Parameter>
<Comment>
<P>Generates the coordinate axes to be used in the
construction. Click on (0,0)!</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="0" actx="0.0" y="0" acty="0.0" shape="circle" fixed="true">좌표가 ( &quot;0&quot;, &quot;0&quot; )인 점</Point>
<Point name="P2" n="1" hidden="super" x="x(P1)+1" actx="1.0" y="y(P1)" acty="0.0" shape="circle" fixed="true">좌표가 ( &quot;x(P1)+1&quot;, &quot;y(P1)&quot; )인 점</Point>
<Line name="g1" n="2" target="true" from="P1" to="P2">Gerade durch P1 und P2</Line>
<Point name="P3" n="3" hidden="super" x="x(P1)" actx="0.0" y="y(P1)+1" acty="1.0" shape="circle" fixed="true">좌표가 ( &quot;x(P1)&quot;, &quot;y(P1)+1&quot; )인 점</Point>
<Line name="g2" n="4" target="true" from="P1" to="P3">Gerade durch P1 und P3</Line>
</Objects>
</Macro>
<Macro Name="기본 매크로/주어진 각만큼 회전 (11)">
<Parameter name="P1">Center of rotation</Parameter>
<Parameter name="P2">Rotating point</Parameter>
<Comment>
<P>Rotates a point P around Q with a given angle.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.221870047543582" y="-0.48177496038034834">좌표가 ( -4.22187, -0.48177 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="2.2440570522979413" y="0.45641838351822467">좌표가 ( 2.24406, 0.45642 )인 점</Point>
<Angle name="w1" n="2" hidden="super" first="P2" root="P1" fixed="16">점 P2을 지나 꼭지점이 P1이고 크기 16인 각</Angle>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Circle around P1 through P2</Circle>
<Intersection name="S1" n="4" target="true" first="w1" second="k1" which="first">Intersection of w1 and k1</Intersection>
</Objects>
<PromptFor object0="w1" prompt0="w1"/>
</Macro>
<Macro Name="기본 매크로/평행이동 (12)">
<Parameter name="P1">Start of shift vector</Parameter>
<Parameter name="P2">End of shift vector</Parameter>
<Parameter name="P3">Shifted point</Parameter>
<Comment>
<P>Shifts P by AB.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-3.3597500000000013" y="-2.8652900000000012">좌표가 ( -3.35975, -2.86529 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="1.7622820919175914" y="-1.4960380348652933">좌표가 ( 1.76228, -1.49604 )인 점</Point>
<Point name="P3" n="2" mainparameter="true" x="-3.9175911251980984" y="3.1442155309033297">좌표가 ( -3.91759, 3.14422 )인 점</Point>
<Point name="P4" n="3" target="true" x="x(P3)+x(P2)-x(P1)" actx="1.2044409667194942" y="y(P3)+y(P2)-y(P1)" acty="4.513467496038038" fixed="true">좌표가 ( &quot;x(P3)+x(P2)-x(P1)&quot;, &quot;y(P3)+y(P2)-y(P1)&quot; )인 점</Point>
</Objects>
</Macro>
<Macro Name="기본 매크로/회전이동 (10)">
<Parameter name="P1">Point on first leg of angle</Parameter>
<Parameter name="P2">Vertex of angle</Parameter>
<Parameter name="P3">Point on second leg of angle</Parameter>
<Parameter name="P4">Rotation center</Parameter>
<Parameter name="P5">Rotating point</Parameter>
<Comment>
<P>Rotate a point P around Q with angle ABC.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="2.852610000000001" y="-3.271">좌표가 ( 2.85261, -3.271 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="-3.689381933438986" y="-3.3217115689381935">좌표가 ( -3.68938, -3.32171 )인 점</Point>
<Point name="P3" n="2" mainparameter="true" x="2.3454800000000002" y="-0.9128400000000001">좌표가 ( 2.34548, -0.91284 )인 점</Point>
<Point name="P4" n="3" mainparameter="true" x="-3.942947702060222" y="0.6085578446909674">좌표가 ( -3.94295, 0.60856 )인 점</Point>
<Point name="P5" n="4" mainparameter="true" x="1.2044400000000006" y="2.71315">좌표가 ( 1.20444, 2.71315 )인 점</Point>
<Angle name="w1" n="5" color="1" hidden="super" first="P5" root="P4" fixed="a(P1,P2,P3)">점 P5을 지나 꼭지점이 P4이고 크기 a(P1,P2,P3)인 각</Angle>
<Circle name="k1" n="6" color="1" hidden="super" through="P5" midpoint="P4" acute="true">Circle P4 through P5</Circle>
<Intersection name="S1" n="7" color="1" target="true" first="w1" second="k1" which="first">Intersection w1 and k1</Intersection>
</Objects>
</Macro>
<Macro Name="기본 매크로/삼각형 (34)">
<Parameter name="P1">A</Parameter>
<Parameter name="P2">B</Parameter>
<Parameter name="P3">C</Parameter>
<Comment>
<P>Triangle with boundary.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-3.0072289156626506" y="-1.3975903614457836">Punkt in -3.00723, -1.39759</Point>
<Point name="P2" n="1" mainparameter="true" x="1.5421686746987968" y="4.4626506024096395">Punkt in 1.54217, 4.46265</Point>
<Segment name="s1" n="2" target="true" from="P1" to="P2">Strecke von P1 nach P2</Segment>
<Point name="P3" n="3" mainparameter="true" x="5.590361445783134" y="-3.0939759036144583">Punkt in 5.59036, -3.09398</Point>
<Segment name="s2" n="4" target="true" from="P2" to="P3">Strecke von P2 nach P3</Segment>
<Segment name="s3" n="5" target="true" from="P3" to="P1">Strecke von P3 nach P1</Segment>
<Polygon name="A1" n="6" type="thin" background="true" target="true" decorative="true" point1="P1" point2="P2" point3="P3">Vieleck P1, P2, P3</Polygon>
</Objects>
</Macro>
<Macro Name="소활팀/삼각형의 오심/외심">
<Parameter name="P3">P3</Parameter>
<Parameter name="P4">P4</Parameter>
<Parameter name="P5">P5</Parameter>
<Comment>
<P>삼각형의 세 꼭지점을 선택하여라.</P>
</Comment>
<Objects>
<Point name="P3" n="0" mainparameter="true" x="-5.064638783269962" y="2.577946768060836">좌표가 ( -5.06464, 2.57795 )인 점</Point>
<Point name="P4" n="1" mainparameter="true" x="-5.551330798479087" y="0.9809885931558933">좌표가 ( -5.55133, 0.98099 )인 점</Point>
<Point name="P5" n="2" mainparameter="true" x="-4.501901140684411" y="1.2699619771863122">좌표가 ( -4.5019, 1.26996 )인 점</Point>
<Midpoint name="M1" n="3" hidden="true" first="P4" second="P5">두 점 P4, P5의 중점</Midpoint>
<Midpoint name="M2" n="4" hidden="true" first="P5" second="P3">두 점 P5, P3의 중점</Midpoint>
<Segment name="s1" n="5" hidden="true" from="P4" to="P5">점 P4에서 점 P5에 그은 선분</Segment>
<Segment name="s2" n="6" hidden="true" from="P5" to="P3">점 P5에서 점 P3에 그은 선분</Segment>
<Plumb name="p1" n="7" hidden="true" point="M1" line="s1" valid="true">점 M1을 지나고 s1에 수직인 직선</Plumb>
<Plumb name="p2" n="8" hidden="true" point="M2" line="s2" valid="true">점 M2을 지나고 s2에 수직인 직선</Plumb>
<Intersection name="I1" n="9" target="true" first="p1" second="p2">두 도형 p1, p2의 교점</Intersection>
</Objects>
</Macro>
<Macro Name="소활팀/삼각형의 오심/내심">
<Parameter name="P3">P3</Parameter>
<Parameter name="P4">P4</Parameter>
<Parameter name="P5">P5</Parameter>
<Comment>
<P>삼각형의 세 꼭지점을 선택하여라.</P>
</Comment>
<Objects>
<Point name="P3" n="0" mainparameter="true" x="-5.064638783269962" y="2.577946768060836">좌표가 ( -5.06464, 2.57795 )인 점</Point>
<Point name="P4" n="1" mainparameter="true" x="-5.551330798479087" y="0.9809885931558933">좌표가 ( -5.55133, 0.98099 )인 점</Point>
<Point name="P5" n="2" mainparameter="true" x="-4.501901140684411" y="1.2699619771863122">좌표가 ( -4.5019, 1.26996 )인 점</Point>
<Ray name="r1" n="3" hidden="true" from="P4" to="P3">점 P4에서 점 P3을 지나는 반직선</Ray>
<Circle name="c1" n="4" hidden="true" through="P5" midpoint="P4" acute="true">중심이 P4이고 P5을 지나는 원</Circle>
<Intersection name="I1" n="5" hidden="true" first="r1" second="c1" which="first">두 도형 r1, c1의 교점</Intersection>
<Circle name="c2" n="6" hidden="super" through="P5" midpoint="I1" acute="true">중심이 I1이고 P5을 지나는 원</Circle>
<Circle name="c3" n="7" hidden="super" through="I1" midpoint="P5" acute="true">중심이 P5이고 I1을 지나는 원</Circle>
<Intersection name="I2" n="8" hidden="super" first="c2" second="c3" which="second">두 도형 c2, c3의 교점</Intersection>
<Intersection name="I3" n="9" hidden="super" first="c3" second="c2" which="second">두 도형 c3, c2의 교점</Intersection>
<Line name="l1" n="10" hidden="true" from="I2" to="I3">두 점 I2, I3을 지나는 직선</Line>
<Ray name="r2" n="11" hidden="true" from="P5" to="P3">점 P5에서 점 P3을 지나는 반직선</Ray>
<Circle name="c4" n="12" hidden="true" through="P4" midpoint="P5" acute="true">중심이 P5이고 P4을 지나는 원</Circle>
<Intersection name="I4" n="13" hidden="true" first="r2" second="c4" which="first">두 도형 r2, c4의 교점</Intersection>
<Circle name="c5" n="14" hidden="super" through="P4" midpoint="I4" acute="true">중심이 I4이고 P4을 지나는 원</Circle>
<Circle name="c6" n="15" hidden="super" through="I4" midpoint="P4" acute="true">중심이 P4이고 I4을 지나는 원</Circle>
<Intersection name="I5" n="16" hidden="super" first="c5" second="c6" which="second">두 도형 c5, c6의 교점</Intersection>
<Intersection name="I6" n="17" hidden="super" first="c6" second="c5" which="second">두 도형 c6, c5의 교점</Intersection>
<Line name="l2" n="18" hidden="true" from="I5" to="I6">두 점 I5, I6을 지나는 직선</Line>
<Intersection name="I7" n="19" target="true" first="l2" second="l1">두 도형 l2, l1의 교점</Intersection>
</Objects>
</Macro>
<Macro Name="소활팀/삼각형의 오심/무게중심">
<Parameter name="P3">P3</Parameter>
<Parameter name="P4">P4</Parameter>
<Parameter name="P5">P5</Parameter>
<Comment>
<P>삼각형의 세 꼭지점을 선택하여라.</P>
</Comment>
<Objects>
<Point name="P3" n="0" mainparameter="true" x="-5.20152091254753" y="2.121673003802281">좌표가 ( -5.20152, 2.12167 )인 점</Point>
<Point name="P4" n="1" mainparameter="true" x="-5.551330798479087" y="0.9809885931558933">좌표가 ( -5.55133, 0.98099 )인 점</Point>
<Point name="P5" n="2" mainparameter="true" x="-4.501901140684411" y="1.2699619771863122">좌표가 ( -4.5019, 1.26996 )인 점</Point>
<Midpoint name="M1" n="3" hidden="true" first="P4" second="P5">두 점 P4, P5의 중점</Midpoint>
<Midpoint name="M2" n="4" hidden="true" first="P5" second="P3">두 점 P5, P3의 중점</Midpoint>
<Ray name="r1" n="5" hidden="true" from="P3" to="M1">점 P3에서 점 M1을 지나는 반직선</Ray>
<Ray name="r2" n="6" hidden="true" from="P4" to="M2">점 P4에서 점 M2을 지나는 반직선</Ray>
<Intersection name="I1" n="7" target="true" first="r2" second="r1">두 도형 r2, r1의 교점</Intersection>
</Objects>
</Macro>
<Macro Name="소활팀/삼각형의 오심/수심">
<Parameter name="P3">P3</Parameter>
<Parameter name="P4">P4</Parameter>
<Parameter name="P5">P5</Parameter>
<Comment>
<P>삼각형의 세 꼭지점을 선택하여라.</P>
</Comment>
<Objects>
<Point name="P3" n="0" mainparameter="true" x="-5.20152091254753" y="2.121673003802281">좌표가 ( -5.20152, 2.12167 )인 점</Point>
<Point name="P4" n="1" mainparameter="true" x="-5.551330798479087" y="0.9809885931558933">좌표가 ( -5.55133, 0.98099 )인 점</Point>
<Point name="P5" n="2" mainparameter="true" x="-4.501901140684411" y="1.2699619771863122">좌표가 ( -4.5019, 1.26996 )인 점</Point>
<Segment name="s1" n="3" hidden="true" from="P4" to="P5">점 P4에서 점 P5에 그은 선분</Segment>
<Segment name="s2" n="4" hidden="true" from="P5" to="P3">점 P5에서 점 P3에 그은 선분</Segment>
<Plumb name="p1" n="5" hidden="true" point="P3" line="s1" valid="true">점 P3을 지나고 s1에 수직인 직선</Plumb>
<Plumb name="p2" n="6" hidden="true" point="P4" line="s2" valid="true">점 P4을 지나고 s2에 수직인 직선</Plumb>
<Intersection name="I1" n="7" target="true" first="p2" second="p1">두 도형 p2, p1의 교점</Intersection>
</Objects>
</Macro>
<Macro Name="소활팀/삼각형의 오심/방심과 방접원">
<Parameter name="P1">P1</Parameter>
<Parameter name="P2">P2</Parameter>
<Parameter name="P3">P3</Parameter>
<Comment>
<P>삼각형의 세 꼭지범을 선택하면 방심과 방접원을 그려 줍니다.</P>
</Comment>
<Objects>
<Point name="P1" n="0" fillbackground="false" x="-1.8507462686567164" y="4.567164179104477">좌표가 ( -1.85075, 4.56716 )인 점</Point>
<Point name="P2" n="1" fillbackground="false" x="-3.243781094527363" y="-0.22885572139303534">좌표가 ( -3.24378, -0.22886 )인 점</Point>
<Point name="P3" n="2" fillbackground="false" x="-0.07960199004975088" y="0.5273631840796016">좌표가 ( -0.0796, 0.52736 )인 점</Point>
<Line name="l1" n="3" hidden="super" from="P3" to="P2">두 점 P3, P2을 지나는 직선</Line>
<Ray name="r1" n="4" hidden="super" from="P1" to="P2">점 P1에서 점 P2을 지나는 반직선</Ray>
<Circle name="c1" n="5" hidden="super" through="P1" midpoint="P2" acute="true">중심이 P2이고 P1을 지나는 원</Circle>
<Intersection name="I1" n="6" hidden="super" first="r1" second="c1" awayfrom="P1" shape="circle" which="first">두 도형 r1, c1의 교점</Intersection>
<Ray name="r2" n="7" hidden="super" from="P2" to="I1">점 P2에서 점 I1을 지나는 반직선</Ray>
<Circle name="c2" n="8" hidden="super" through="P3" midpoint="P2" acute="true">중심이 P2이고 P3을 지나는 원</Circle>
<Intersection name="I2" n="9" hidden="super" first="r2" second="c2" which="first">두 도형 r2, c2의 교점</Intersection>
<Circle name="c3" n="10" hidden="super" through="P3" midpoint="I2" acute="true">중심이 I2이고 P3을 지나는 원</Circle>
<Circle name="c4" n="11" hidden="super" through="I2" midpoint="P3" acute="true">중심이 P3이고 I2을 지나는 원</Circle>
<Intersection name="I3" n="12" hidden="super" first="c3" second="c4" which="second">두 도형 c3, c4의 교점</Intersection>
<Intersection name="I4" n="13" hidden="super" first="c4" second="c3" which="second">두 도형 c4, c3의 교점</Intersection>
<Line name="l2" n="14" hidden="super" from="I3" to="I4">두 점 I3, I4을 지나는 직선</Line>
<Ray name="r3" n="15" hidden="super" from="P1" to="P2">점 P1에서 점 P2을 지나는 반직선</Ray>
<Circle name="c5" n="16" hidden="super" through="P3" midpoint="P1" acute="true">중심이 P1이고 P3을 지나는 원</Circle>
<Intersection name="I5" n="17" hidden="super" first="r3" second="c5" which="first">두 도형 r3, c5의 교점</Intersection>
<Circle name="c6" n="18" hidden="super" through="P3" midpoint="I5" acute="true">중심이 I5이고 P3을 지나는 원</Circle>
<Circle name="c7" n="19" hidden="super" through="I5" midpoint="P3" acute="true">중심이 P3이고 I5을 지나는 원</Circle>
<Intersection name="I6" n="20" hidden="super" first="c6" second="c7" which="second">두 도형 c6, c7의 교점</Intersection>
<Intersection name="I7" n="21" hidden="super" first="c7" second="c6" which="second">두 도형 c7, c6의 교점</Intersection>
<Line name="l3" n="22" hidden="super" from="I6" to="I7">두 점 I6, I7을 지나는 직선</Line>
<Intersection name="I8" n="23" target="true" ctag0="red" cexpr0="1" first="l2" second="l3" shape="circle">두 도형 l2, l3의 교점</Intersection>
<Plumb name="p1" n="24" hidden="super" point="I8" line="l1" valid="true">점 I8을 지나고 l1에 수직인 직선</Plumb>
<Intersection name="I9" n="25" hidden="super" first="l1" second="p1" shape="circle">두 도형 l1, p1의 교점</Intersection>
<Circle name="c8" n="26" target="true" ctag0="red" cexpr0="1" ctag1="thin" cexpr1="1" through="I9" midpoint="I8" acute="true">중심이 I8이고 I9을 지나는 원</Circle>
</Objects>
</Macro>
<Macro Name="소활팀/정n각형/회전이동 사용">
<Parameter name="P1">P1</Parameter>
<Parameter name="P2">P2</Parameter>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-2.8130939809926074" y="1.7486800422386493">좌표가 ( -2.81309, 1.74868 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="-2.6948257655755015" y="1.1911298838437174">좌표가 ( -2.69483, 1.19113 )인 점</Point>
<Circle name="c1" n="2" hidden="super" through="P2" midpoint="P1" acute="true">중심이 P1이고 P2을 지나는 원</Circle>
<Circle name="c2" n="3" hidden="super" through="P1" midpoint="P2" acute="true">중심이 P2이고 P1을 지나는 원</Circle>
<Intersection name="I1" n="4" hidden="super" first="c1" second="c2" which="second">두 도형 c1, c2의 교점</Intersection>
<Intersection name="I2" n="5" hidden="super" first="c2" second="c1" which="second">두 도형 c2, c1의 교점</Intersection>
<Line name="l1" n="6" hidden="true" from="I1" to="I2">두 점 I1, I2을 지나는 직선</Line>
<Expression name="E1" n="7" hidden="true" showvalue="true" x="-2.9651500000000004" y="3.06653" value="24" prompt="Value">식 &quot; 24 &quot; at -2.96515, 3.06653</Expression>
<Angle name="a1" n="8" hidden="true" unit="°" first="P2" root="P1" fixed="90-180/E1" acute="true">점 P2을 지나 꼭지점이 P1이고 크기 90-180/E1인 각</Angle>
<Intersection name="I3" n="9" hidden="true" first="l1" second="a1" shape="circle">두 도형 l1, a1의 교점</Intersection>
<Angle name="a2" n="10" hidden="super" first="P2" root="I3" fixed="360/E1">점 P2을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c3" n="11" hidden="super" through="P2" midpoint="I3" acute="true">중심이 I3이고 P2을 지나는 원</Circle>
<Intersection name="I4" n="12" hidden="true" first="a2" second="c3" shape="circle" which="first">두 도형 a2, c3의 교점</Intersection>
<Angle name="a3" n="13" hidden="super" first="I4" root="I3" fixed="360/E1">점 I4을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c4" n="14" hidden="super" through="I4" midpoint="I3" acute="true">중심이 I3이고 I4을 지나는 원</Circle>
<Intersection name="I5" n="15" hidden="true" first="a3" second="c4" shape="circle" which="first">두 도형 a3, c4의 교점</Intersection>
<Angle name="a4" n="16" hidden="super" first="I5" root="I3" fixed="360/E1">점 I5을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c5" n="17" hidden="super" through="I5" midpoint="I3" acute="true">중심이 I3이고 I5을 지나는 원</Circle>
<Intersection name="I6" n="18" hidden="true" first="a4" second="c5" shape="circle" which="first">두 도형 a4, c5의 교점</Intersection>
<Intersection name="I7" n="19" hidden="true" first="a1" second="l1" shape="circle">두 도형 a1, l1의 교점</Intersection>
<Angle name="a5" n="20" hidden="super" first="I6" root="I7" fixed="360/E1">점 I6을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c6" n="21" hidden="super" through="I6" midpoint="I7" acute="true">중심이 I7이고 I6을 지나는 원</Circle>
<Intersection name="I8" n="22" hidden="true" first="a5" second="c6" shape="circle" which="first">두 도형 a5, c6의 교점</Intersection>
<Angle name="a6" n="23" hidden="super" first="I8" root="I3" fixed="360/E1">점 I8을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c7" n="24" hidden="super" through="I8" midpoint="I3" acute="true">중심이 I3이고 I8을 지나는 원</Circle>
<Intersection name="I9" n="25" hidden="true" first="a6" second="c7" shape="circle" which="first">두 도형 a6, c7의 교점</Intersection>
<Angle name="a7" n="26" hidden="super" first="I9" root="I7" fixed="360/E1">점 I9을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c8" n="27" hidden="super" through="I9" midpoint="I7" acute="true">중심이 I7이고 I9을 지나는 원</Circle>
<Intersection name="I10" n="28" hidden="true" first="a7" second="c8" shape="circle" which="first">두 도형 a7, c8의 교점</Intersection>
<Angle name="a8" n="29" hidden="super" first="I10" root="I3" fixed="360/E1">점 I10을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c9" n="30" hidden="super" through="I10" midpoint="I3" acute="true">중심이 I3이고 I10을 지나는 원</Circle>
<Intersection name="I11" n="31" hidden="true" first="a8" second="c9" shape="circle" which="first">두 도형 a8, c9의 교점</Intersection>
<Segment name="s1" n="32" target="true" from="P1" to="P2">점 P1에서 점 P2에 그은 선분</Segment>
<Segment name="s2" n="33" target="true" from="P2" to="I4">점 P2에서 점 I4에 그은 선분</Segment>
<Segment name="s3" n="34" target="true" from="I4" to="I5">점 I4에서 점 I5에 그은 선분</Segment>
<Segment name="s4" n="35" target="true" from="I5" to="I6">점 I5에서 점 I6에 그은 선분</Segment>
<Segment name="s5" n="36" target="true" from="I6" to="I8">점 I6에서 점 I8에 그은 선분</Segment>
<Segment name="s6" n="37" target="true" from="I8" to="I9">점 I8에서 점 I9에 그은 선분</Segment>
<Segment name="s7" n="38" target="true" from="I9" to="I10">점 I9에서 점 I10에 그은 선분</Segment>
<Segment name="s8" n="39" target="true" from="I10" to="I11">점 I10에서 점 I11에 그은 선분</Segment>
<Angle name="a9" n="40" hidden="super" first="I11" root="I3" fixed="360/E1">점 I11을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c10" n="41" hidden="super" through="I11" midpoint="I3" acute="true">중심이 I3이고 I11을 지나는 원</Circle>
<Intersection name="I12" n="42" hidden="true" first="a9" second="c10" shape="circle" which="first">두 도형 a9, c10의 교점</Intersection>
<Angle name="a10" n="43" hidden="super" first="I12" root="I7" fixed="360/E1">점 I12을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c11" n="44" hidden="super" through="I12" midpoint="I7" acute="true">중심이 I7이고 I12을 지나는 원</Circle>
<Intersection name="I13" n="45" hidden="true" first="a10" second="c11" shape="circle" which="first">두 도형 a10, c11의 교점</Intersection>
<Angle name="a11" n="46" hidden="super" first="I13" root="I3" fixed="360/E1">점 I13을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c12" n="47" hidden="super" through="I13" midpoint="I3" acute="true">중심이 I3이고 I13을 지나는 원</Circle>
<Intersection name="I14" n="48" hidden="true" first="a11" second="c12" shape="circle" which="first">두 도형 a11, c12의 교점</Intersection>
<Angle name="a12" n="49" hidden="super" first="I14" root="I7" fixed="360/E1">점 I14을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c13" n="50" hidden="super" through="I14" midpoint="I7" acute="true">중심이 I7이고 I14을 지나는 원</Circle>
<Intersection name="I15" n="51" hidden="true" first="a12" second="c13" shape="circle" which="first">두 도형 a12, c13의 교점</Intersection>
<Angle name="a13" n="52" hidden="super" first="I15" root="I3" fixed="360/E1">점 I15을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c14" n="53" hidden="super" through="I15" midpoint="I3" acute="true">중심이 I3이고 I15을 지나는 원</Circle>
<Intersection name="I16" n="54" hidden="true" first="a13" second="c14" shape="circle" which="first">두 도형 a13, c14의 교점</Intersection>
<Angle name="a14" n="55" hidden="super" first="I16" root="I7" fixed="360/E1">점 I16을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c15" n="56" hidden="super" through="I16" midpoint="I7" acute="true">중심이 I7이고 I16을 지나는 원</Circle>
<Intersection name="I17" n="57" hidden="true" first="a14" second="c15" shape="circle" which="first">두 도형 a14, c15의 교점</Intersection>
<Angle name="a15" n="58" hidden="super" first="I17" root="I3" fixed="360/E1">점 I17을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c16" n="59" hidden="super" through="I17" midpoint="I3" acute="true">중심이 I3이고 I17을 지나는 원</Circle>
<Intersection name="I18" n="60" hidden="true" first="a15" second="c16" shape="circle" which="first">두 도형 a15, c16의 교점</Intersection>
<Angle name="a16" n="61" hidden="super" first="I18" root="I7" fixed="360/E1">점 I18을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c17" n="62" hidden="super" through="I18" midpoint="I7" acute="true">중심이 I7이고 I18을 지나는 원</Circle>
<Intersection name="I19" n="63" hidden="true" first="a16" second="c17" shape="circle" which="first">두 도형 a16, c17의 교점</Intersection>
<Angle name="a17" n="64" hidden="super" first="I19" root="I3" fixed="360/E1">점 I19을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c18" n="65" hidden="super" through="I19" midpoint="I3" acute="true">중심이 I3이고 I19을 지나는 원</Circle>
<Intersection name="I20" n="66" hidden="true" first="a17" second="c18" shape="circle" which="first">두 도형 a17, c18의 교점</Intersection>
<Angle name="a18" n="67" hidden="super" first="I20" root="I7" fixed="360/E1">점 I20을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c19" n="68" hidden="super" through="I20" midpoint="I7" acute="true">중심이 I7이고 I20을 지나는 원</Circle>
<Intersection name="I21" n="69" hidden="true" first="a18" second="c19" shape="circle" which="first">두 도형 a18, c19의 교점</Intersection>
<Angle name="a19" n="70" hidden="super" first="I21" root="I3" fixed="360/E1">점 I21을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c20" n="71" hidden="super" through="I21" midpoint="I3" acute="true">중심이 I3이고 I21을 지나는 원</Circle>
<Intersection name="I22" n="72" hidden="true" first="a19" second="c20" shape="circle" which="first">두 도형 a19, c20의 교점</Intersection>
<Angle name="a20" n="73" hidden="super" first="I22" root="I7" fixed="360/E1">점 I22을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c21" n="74" hidden="super" through="I22" midpoint="I7" acute="true">중심이 I7이고 I22을 지나는 원</Circle>
<Intersection name="I23" n="75" hidden="true" first="a20" second="c21" shape="circle" which="first">두 도형 a20, c21의 교점</Intersection>
<Angle name="a21" n="76" hidden="super" first="I23" root="I3" fixed="360/E1">점 I23을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c22" n="77" hidden="super" through="I23" midpoint="I3" acute="true">중심이 I3이고 I23을 지나는 원</Circle>
<Intersection name="I24" n="78" hidden="true" first="a21" second="c22" shape="circle" which="first">두 도형 a21, c22의 교점</Intersection>
<Angle name="a22" n="79" hidden="super" first="I24" root="I7" fixed="360/E1">점 I24을 지나 꼭지점이 I7이고 크기 360/E1인 각</Angle>
<Circle name="c23" n="80" hidden="super" through="I24" midpoint="I7" acute="true">중심이 I7이고 I24을 지나는 원</Circle>
<Intersection name="I25" n="81" hidden="true" first="a22" second="c23" shape="circle" which="first">두 도형 a22, c23의 교점</Intersection>
<Angle name="a23" n="82" hidden="super" first="I25" root="I3" fixed="360/E1">점 I25을 지나 꼭지점이 I3이고 크기 360/E1인 각</Angle>
<Circle name="c24" n="83" hidden="super" through="I25" midpoint="I3" acute="true">중심이 I3이고 I25을 지나는 원</Circle>
<Intersection name="I26" n="84" hidden="true" first="a23" second="c24" shape="circle" which="first">두 도형 a23, c24의 교점</Intersection>
<Segment name="s9" n="85" target="true" from="I11" to="I12">점 I11에서 점 I12에 그은 선분</Segment>
<Segment name="s10" n="86" target="true" from="I12" to="I13">점 I12에서 점 I13에 그은 선분</Segment>
<Segment name="s11" n="87" target="true" from="I13" to="I14">점 I13에서 점 I14에 그은 선분</Segment>
<Segment name="s12" n="88" target="true" from="I14" to="I15">점 I14에서 점 I15에 그은 선분</Segment>
<Segment name="s13" n="89" target="true" from="I15" to="I16">점 I15에서 점 I16에 그은 선분</Segment>
<Segment name="s14" n="90" target="true" from="I16" to="I17">점 I16에서 점 I17에 그은 선분</Segment>
<Segment name="s15" n="91" target="true" from="I17" to="I18">점 I17에서 점 I18에 그은 선분</Segment>
<Segment name="s16" n="92" target="true" from="I18" to="I19">점 I18에서 점 I19에 그은 선분</Segment>
<Segment name="s17" n="93" target="true" from="I19" to="I20">점 I19에서 점 I20에 그은 선분</Segment>
<Segment name="s18" n="94" target="true" from="I20" to="I21">점 I20에서 점 I21에 그은 선분</Segment>
<Segment name="s19" n="95" target="true" from="I21" to="I22">점 I21에서 점 I22에 그은 선분</Segment>
<Segment name="s20" n="96" target="true" from="I22" to="I23">점 I22에서 점 I23에 그은 선분</Segment>
<Segment name="s21" n="97" target="true" from="I23" to="I24">점 I23에서 점 I24에 그은 선분</Segment>
<Segment name="s22" n="98" target="true" from="I24" to="I25">점 I24에서 점 I25에 그은 선분</Segment>
<Segment name="s23" n="99" target="true" from="I25" to="I26">점 I25에서 점 I26에 그은 선분</Segment>
</Objects>
<PromptFor object0="E1" prompt0="실수 n≤23(소수인 경우 별다각형)"/>
</Macro>
<Macro Name="소활팀/정n각형/함수 사용">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Comment>
<P>실수 n을 입력하시오.</P>
<P>n이 정수이면 볼록 다각형</P>
<P>n이 소수이면 별 다각형</P>
</Comment>
<Objects>
<Point name="A" n="0" x="-1.8716981132075468" y="-2.166037735849057">좌표가 ( -1.8717, -2.16604 )인 점</Point>
<Point name="B" n="1" x="0.19622641509434047" y="-0.8830188679245285">좌표가 ( 0.19623, -0.88302 )인 점</Point>
<Expression name="E1" n="2" hidden="true" showvalue="true" x="-4.64906" y="2.6528300000000002" value="3" prompt="Value">정n각형 </Expression>
<Point name="P3" n="3" hidden="true" showname="true" x="x(A)+sqrt(d(A,B)^2/(2-2*rcos(2*pi/E1)))" actx="-0.46665371649465004" y="y(A)" acty="-2.166037735849057" shape="dcross" fixed="true">좌표가 ( &quot;x(A)+sqrt(d(A,B)^2/(2-2*rcos(2*pi/round(E1))))&quot;, &quot;y(A)&quot; )인 점</Point>
<Circle3 name="c1" n="4" hidden="true" from="A" to="P3" midpoint="A">중심이 A이고 선분A P3을(를) 반지름으로 하는 원</Circle3>
<Circle3 name="c2" n="5" hidden="true" from="A" to="P3" midpoint="B">중심이 B이고 선분A P3을(를) 반지름으로 하는 원</Circle3>
<Angle name="a1" n="6" hidden="true" unit="rad" first="P3" root="A" second="B">각 P3 A B</Angle>
<Angle name="a2" n="7" hidden="true" unit="rad" first="P3" root="A" fixed="(180-360/E1)/2">점 P3을 지나 꼭지점이 A이고 크기 (180-360/E1)/2인 각</Angle>
<Intersection name="I1" n="8" hidden="true" showname="true" first="c2" second="c1" shape="dcross" which="first">두 도형 c2, c1의 교점</Intersection>
<Expression name="E2" n="9" hidden="true" showvalue="true" x="-4.66415" y="2.24906" value="if(floor(E1)*100==floor(E1*100),1,if(floor(E1*10)*10==floor(E1*100),10,100))" prompt="Value">식 &quot; if(floor(E1)*100==floor(E1*100),1,if(floor(E1*10)*10==floor(E1*100),10,100)) &quot; at -4.66415, 2.24906</Expression>
<Function name="f1" n="10" target="true" x="d(A,P3)*rcos(x)+x(I1)" y="d(A,P3)*rsin(x)+y(I1)" var="x" min="(a1-a2)*pi/180" max="E2*2*pi+(a1-a2)*pi/180" d="2*pi/E1">함수 ( 변의 길이가 주어진 정n각형 ) </Function>
</Objects>
<PromptFor object0="E1" prompt0="실수 n"/>
</Macro>
<Macro Name="소활팀/선분의 길이 표시">
<Parameter name="P1">P1</Parameter>
<Parameter name="P2">P2</Parameter>
<Comment>
<P>점선을 오른쪽 마우스로 클릭하여 이름에 길이나 문자를 쓰세요</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" fillbackground="false" x="-1.7092682926829266" y="0.835121951219512">좌표가 ( -1.709268, 0.835122 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" fillbackground="false" x="1.6936585365853656" y="1.2097560975609758">좌표가 ( 1.693659, 1.209756 )인 점</Point>
<Circle name="c1" n="2" hidden="super" through="P1" midpoint="P2" acute="true">중심이 P2이고 P1을 지나는 원</Circle>
<Midpoint name="M1" n="3" hidden="super" first="P1" second="P2" shape="circle">두 점 P1, P2의 중점</Midpoint>
<Segment name="s1" n="4" hidden="super" from="P1" to="P2">점 P1에서 점 P2에 그은 선분</Segment>
<Plumb name="p1" n="5" hidden="super" point="M1" line="s1" valid="true">점 M1을 지나고 s1에 수직인 직선</Plumb>
<Intersection name="I1" n="6" hidden="super" first="p1" second="c1" shape="circle" which="first">두 도형 p1, c1의 교점</Intersection>
<Circle3 name="c2" n="7" color="5" type="thin" target="true" ctag0="thin" cexpr0="1" from="P1" to="P2" midpoint="I1" start="P1" end="P2">중심이 I1이고 선분P1 P2을(를) 반지름으로 하는 원</Circle3>
</Objects>
</Macro>
<Macro Name="소활팀/비율지정">
<Parameter name="P2">P2</Parameter>
<Parameter name="P1">P1</Parameter>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-3.8099260823653642" y="1.3262935586061246">좌표가 ( -3.80993, 1.32629 )인 점</Point>
<Point name="P2" n="1" mainparameter="true" x="-6.04012671594509" y="1.3600844772967262">좌표가 ( -6.04013, 1.36008 )인 점</Point>
<Expression name="E1" n="2" hidden="true" showvalue="true" x="-1.2080300000000002" y="3.5227000000000004" value="d(P1,P2)" prompt="Value">식 &quot; d(P1,P2) &quot; at -1.20803, 3.5227</Expression>
<Expression name="E2" n="3" hidden="true" showvalue="true" x="0.38015000000000004" y="3.69166" value="2/3" prompt="Value">식 &quot; 2/3 &quot; at 0.38015, 3.69166</Expression>
<Expression name="E3" n="4" hidden="true" showvalue="true" x="0.21119000000000002" y="2.4076" value="E1*E2" prompt="Value">식 &quot; E1*E2 &quot; at 0.21119, 2.4076</Expression>
<Circle name="c1" n="5" hidden="true" fixed="E3" midpoint="P2" acute="true">중심이 P2이고 반지름이 E3인 원</Circle>
<Line name="l1" n="6" hidden="true" from="P2" to="P1">두 점 P2, P1을 지나는 직선</Line>
<Intersection name="I1" n="7" target="true" first="l1" second="c1" shape="circle" which="first">두 도형 l1, c1의 교점</Intersection>
</Objects>
<PromptFor object0="E2" prompt0="비율(분수값)"/>
</Macro>
</CaR>
