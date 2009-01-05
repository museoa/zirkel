<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="Macros Padrão/Bissetriz como uma reta (8)">
<Parameter name="P1">Ponto sobre o primeiro lado do ângulo</Parameter>
<Parameter name="P2">Vértice do ângulo</Parameter>
<Parameter name="P3">Ponto sobre o segundo lado do ângulo</Parameter>
<Comment>
<P>Bissetriz do ângulo P1P2P3 como uma reta.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-5.25" y="4.59">Punkt in -5.25, 4.59</Point>
<Point name="P2" n="1" mainparameter="true" x="-0.875" y="-3.237500000000001">Punkt in -0.875, -3.2375</Point>
<Point name="P3" n="2" mainparameter="true" x="3.5250000000000012" y="2.6875">Punkt in 3.525, 2.6875</Point>
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
<Macro Name="Macros Padrão/Bissetriz como uma semi-reta (9)">
<Parameter name="P1">Ponto sobre o primeiro lado do ângulo</Parameter>
<Parameter name="P2">Vértice do ângulo</Parameter>
<Parameter name="P3">Ponto sobre o segundo lado do ângulo</Parameter>
<Comment>
<P>Bissetriz do ângulo P1P2P3 como uma semi-reta.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.100000000000001" y="4.26">Punkt in -4.1, 4.26</Point>
<Point name="P2" n="1" mainparameter="true" x="-0.5250000000000008" y="-2.6875">Punkt in -0.525, -2.6875</Point>
<Point name="P3" n="2" mainparameter="true" x="3.425000000000001" y="2.7875">Punkt in 3.425, 2.7875</Point>
<Ray name="r1" n="3" hidden="super" from="P2" to="P1">Strahl von P2 in Richtung P1</Ray>
<Circle name="k1" n="4" hidden="super" through="P3" midpoint="P2" acute="true">Kreis um P2 durch P3</Circle>
<Intersection name="S1" n="5" hidden="super" first="r1" second="k1" which="first">Schnitt zwischen r1 und k1</Intersection>
<Midpoint name="M1" n="6" hidden="super" first="S1" second="P3">Mitte zwischen S1 und P3</Midpoint>
<Ray name="r2" n="7" target="true" from="P2" to="M1">Strahl von P2 in Richtung M1</Ray>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Eixos coordenados">
<Parameter name="P1">Centro</Parameter>
<Comment>
<P>Cria os eixos coordenados para serem usados na construção. Clique em (0, 0)!</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="0" actx="0.0" y="0" acty="0.0" shape="circle" fixed="true">Punkt in &quot;0&quot;, &quot;0&quot;</Point>
<Point name="P2" n="1" hidden="super" x="x(P1)+1" actx="1.0" y="y(P1)" acty="0.0" shape="circle" fixed="true">Punkt in &quot;x(P1)+1&quot;, &quot;y(P1)&quot;</Point>
<Line name="g1" n="2" target="true" from="P1" to="P2">Gerade durch P1 und P2</Line>
<Point name="P3" n="3" hidden="super" x="x(P1)" actx="0.0" y="y(P1)+1" acty="1.0" shape="circle" fixed="true">Punkt in &quot;x(P1)&quot;, &quot;y(P1)+1&quot;</Point>
<Line name="g2" n="4" target="true" from="P1" to="P3">Gerade durch P1 und P3</Line>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Mediatriz (1)">
<Parameter name="P1">Primeira extremidade do segmento</Parameter>
<Parameter name="P2">Segunda extremidade do segmento</Parameter>
<Comment>
<P>Mediatriz do segmento de extremidades P1 e P2.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.225" y="0.012500000000000181">Punkt in -4.225, 0.0125</Point>
<Point name="P2" n="1" mainparameter="true" x="1.4499999999999993" y="1.8125">Punkt in 1.45, 1.8125</Point>
<Circle name="k1" n="2" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Circle name="k2" n="3" hidden="super" through="P1" midpoint="P2" acute="true">Kreis um P2 durch P1</Circle>
<Intersection name="S1" n="4" hidden="super" first="k1" second="k2" which="second">Schnitt zwischen k1 und k2</Intersection>
<Intersection name="S2" n="5" hidden="super" first="k2" second="k1" which="second">Schnitt zwischen k2 und k1</Intersection>
<Line name="g1" n="6" target="true" from="S1" to="S2">Middle perpendicular of S1 and S2</Line>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Projeção de um ponto sobre uma reta (2)">
<Parameter name="g1">Reta</Parameter>
<Parameter name="P3">Ponto</Parameter>
<Comment>
<P>Determina a projeção de um ponto sobre uma reta.</P>
</Comment>
<Objects>
<Line name="g1" n="0" mainparameter="true">???</Line>
<Point name="P3" n="1" mainparameter="true" x="1.675041876046901" y="2.46566164154104">Punkt in 1.67504, 2.46566</Point>
<Plumb name="l1" n="2" hidden="super" point="P3" line="g1" valid="true">Lot durch P3 zu g1</Plumb>
<Intersection name="S1" n="3" target="true" first="g1" second="l1">Schnitt zwischen g1 und l1</Intersection>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Reflexão por um círculo (inversão) (6)">
<Parameter name="k">Círculo</Parameter>
<Parameter name="P">Ponto</Parameter>
<Comment>
<P>Determina a reflexão de um ponto por um círculo.</P>
</Comment>
<Objects>
<Point name="P1" n="0" parameter="true" x="-0.72" y="-0.89">Punkt in -0.72, -0.89</Point>
<Circle name="k" n="1" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P" n="2" mainparameter="true" x="1.45" y="1.86">Punkt in 1.45, 1.86</Point>
<Ray name="r1" n="3" hidden="super" from="P1" to="P">Strahl von P1 in Richtung P</Ray>
<Circle name="k2" n="4" hidden="super" fixed="k^2/d(P,P1)" midpoint="P1" acute="true">Kreis um P1 mit Radius 6.738402</Circle>
<Intersection name="S1" n="5" target="true" first="r1" second="k2" which="first">Schnitt zwischen r1 und k2</Intersection>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Reflexão por uma reta (5)">
<Parameter name="g1">Reta</Parameter>
<Parameter name="P3">Ponto</Parameter>
<Comment>
<P>Determina a reflexão de um ponto por uma reta.</P>
</Comment>
<Objects>
<Line name="g1" n="0" mainparameter="true">???</Line>
<Point name="P3" n="1" mainparameter="true" x="-1.4000000000000001" y="3.34">Punkt in -1.4, 3.34</Point>
<Plumb name="l1" n="2" hidden="super" point="P3" line="g1" valid="true">Lot durch P3 zu g1</Plumb>
<Intersection name="S1" n="3" hidden="super" first="l1" second="g1">Schnitt zwischen l1 und g1</Intersection>
<Circle name="k1" n="4" hidden="super" through="P3" midpoint="S1" acute="true">Kreis um S1 durch P3</Circle>
<Intersection name="S2" n="5" target="true" first="l1" second="k1" awayfrom="P3" which="second">Schnitt zwischen l1 und k1</Intersection>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Reflexão por um ponto (7)">
<Parameter name="P1">Centro da reflexão</Parameter>
<Parameter name="P2">Ponto</Parameter>
<Comment>
<P>Determina a reflexão de um ponto por outro.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="0.15000000000000038" y="-0.4874999999999998">Punkt in 0.15, -0.4875</Point>
<Point name="P2" n="1" mainparameter="true" x="-2.7800000000000002" y="-2.54">Punkt in -2.78, -2.54</Point>
<Ray name="r1" n="2" hidden="super" from="P2" to="P1">Strahl von P2 in Richtung P1</Ray>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Intersection name="S1" n="4" target="true" first="r1" second="k1" awayfrom="P2" which="first">Schnitt zwischen r1 und k1</Intersection>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Rotação (10)">
<Parameter name="P1">Ponto no primeiro lado do ângulo</Parameter>
<Parameter name="P2">Vértice do ângulo</Parameter>
<Parameter name="P3">Ponto no segundo lado do ângulo</Parameter>
<Parameter name="P4">Centro da rotação</Parameter>
<Parameter name="P5">Ponto</Parameter>
<Comment>
<P>Rotação de um ponto por um ângulo determinado por três pontos.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="2.8526100000000008" y="-3.271">Punkt in 2.85261, -3.271</Point>
<Point name="P2" n="1" mainparameter="true" x="-3.689381933438986" y="-3.3217115689381935">Punkt in -3.68938, -3.32171</Point>
<Point name="P3" n="2" mainparameter="true" x="2.3454800000000002" y="-0.9128400000000001">Punkt in 2.34548, -0.91284</Point>
<Point name="P4" n="3" mainparameter="true" x="-3.942947702060222" y="0.6085578446909674">Punkt in -3.94295, 0.60856</Point>
<Point name="P5" n="4" mainparameter="true" x="1.2044400000000004" y="2.71315">Punkt in 1.20444, 2.71315</Point>
<Angle name="w1" n="5" color="1" hidden="super" first="P5" root="P4" fixed="a(P1,P2,P3)">Fester Winkel P5 - P4 mit GrÃ¶Ãe a(P1,P2,P3)</Angle>
<Circle name="k1" n="6" color="1" hidden="super" through="P5" midpoint="P4" acute="true">Circle P4 through P5</Circle>
<Intersection name="S1" n="7" color="1" target="true" first="w1" second="k1" which="first">Intersection w1 and k1</Intersection>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Rotação dado um ângulo (11)">
<Parameter name="P1">Centro da rotação</Parameter>
<Parameter name="P2">Ponto</Parameter>
<Comment>
<P>Rotação de um ponto dado um ângulo.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.221870047543582" y="-0.48177496038034834">Punkt in -4.22187, -0.48177</Point>
<Point name="P2" n="1" mainparameter="true" x="2.244057052297941" y="0.4564183835182246">Punkt in 2.24406, 0.45642</Point>
<Angle name="w1" n="2" hidden="super" first="P2" root="P1" fixed="16">Fester Winkel P2 - P1 mit GrÃ¶Ãe 16</Angle>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Circle around P1 through P2</Circle>
<Intersection name="S1" n="4" target="true" first="w1" second="k1" which="first">Intersection of w1 and k1</Intersection>
</Objects>
<PromptFor object0="w1" prompt0="w1"/>
</Macro>
<Macro Name="Macros Padrão/Translação (12)">
<Parameter name="P1">Extremidade inicial do vetor translação</Parameter>
<Parameter name="P2">Extremidade final do vetor translação</Parameter>
<Parameter name="P3">Ponto</Parameter>
<Comment>
<P>Translação de um ponto por um vetor.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-3.359750000000001" y="-2.865290000000001">Punkt in -3.35975, -2.86529</Point>
<Point name="P2" n="1" mainparameter="true" x="1.7622820919175912" y="-1.4960380348652933">Punkt in 1.76228, -1.49604</Point>
<Point name="P3" n="2" mainparameter="true" x="-3.917591125198098" y="3.1442155309033293">Punkt in -3.91759, 3.14422</Point>
<Point name="P4" n="3" target="true" x="x(P3)+x(P2)-x(P1)" actx="1.2044409667194942" y="y(P3)+y(P2)-y(P1)" acty="4.513467496038037" fixed="true">Punkt in &quot;x(P3)+x(P2)-x(P1)&quot;, &quot;y(P3)+y(P2)-y(P1)&quot;</Point>
</Objects>
</Macro>
<Macro Name="Macros Padrão/Seletor">
<Parameter name="P1">Ponto inicial do seletor</Parameter>
<Parameter name="P2">Ponto final do seletor</Parameter>
<Comment>
<P>Seletor para escolher um valor numérico
no intervalo [a, b]. Este valor pode ser usado
depois (renomeie a expressão). Os valores de a e b
serão pedidos durante a execução da macro.</P>
</Comment>
<Objects>
<Expression name="AD1" n="0" hidden="super" showvalue="true" x="2.6666700000000008" y="2.0" value="0" prompt="Wert">Ausdruck &quot;0&quot; in 2.66667, 2.0</Expression>
<Expression name="AD2" n="1" hidden="super" showvalue="true" x="2.68852" y="1.4535500000000001" value="10" prompt="Wert">Ausdruck &quot;10&quot; in 2.68852, 1.45355</Expression>
<Point name="P1" n="2" mainparameter="true" x="-0.9617486338797817" y="1.3224043715847">Punkt in -0.96175, 1.3224</Point>
<Point name="P2" n="3" mainparameter="true" x="1.4863400000000004" y="3.77049">Punkt in 1.48634, 3.77049</Point>
<Segment name="s1" n="4" target="true" from="P1" to="P2">Segment from P1 to P2</Segment>
<PointOn name="OP3" n="5" type="thick" target="true" on="s1" alpha="0.20682542521125796" x="-0.4554216612227482" y="1.8287307226352643" shape="circle">Punkt auf s1</PointOn>
<Circle name="k1" n="6" hidden="super" fixed="d(P1,P2)/10" midpoint="OP3" acute="true">Circle around OP3 with Radius 0.3462118</Circle>
<PointOn name="OP4" n="7" type="invisible" hidden="super" on="k1" alpha="-1.017350822361835" x="-0.2734452952243154" y="1.534201922257689" shape="circle">Punkt auf k1</PointOn>
<Expression name="AD3" n="8" showvalue="true" x="x(OP4)" y="y(OP4)" value="AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1)" prompt="Wert" fixed="true">Ausdruck &quot;AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1)&quot; in 0.53516, 0.22548</Expression>
</Objects>
<PromptFor object0="AD1" prompt0="a" object1="AD2" prompt1="b"/>
</Macro>
<Macro Name="Macros Padrão/Triângulo (34)">
<Parameter name="P1">A</Parameter>
<Parameter name="P2">B</Parameter>
<Parameter name="P3">C</Parameter>
<Comment>
<P>Triângulo.</P>
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
</CaR>
