<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="Default Makros/Drehung (10)">
<Parameter name="P1">Erster Schenkelpunkt des Winkels</Parameter>
<Parameter name="P2">Scheitel des Winkels</Parameter>
<Parameter name="P3">Zweiter Schenkelpunkt des Winkels</Parameter>
<Parameter name="P4">Drehpunkt Q</Parameter>
<Parameter name="P5">Zu drehender Punkt P</Parameter>
<Comment>
<P>Dreht den Punkt P um Q um den Winkel ABC.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="2.852610000000001" y="-3.271">Punkt in 2.85261, -3.271</Point>
<Point name="P2" n="1" mainparameter="true" x="-3.689381933438986" y="-3.3217115689381935">Punkt in -3.689381933439, -3.321711568938</Point>
<Point name="P3" n="2" mainparameter="true" x="2.3454800000000002" y="-0.9128400000000001">Punkt in 2.34548, -0.91284</Point>
<Point name="P4" n="3" mainparameter="true" x="-3.942947702060222" y="0.6085578446909674">Punkt in -3.94294770206, 0.608557844691</Point>
<Point name="P5" n="4" mainparameter="true" x="1.2044400000000006" y="2.71315">Punkt in 1.20444, 2.71315</Point>
<Angle name="w1" n="5" color="1" hidden="super" first="P5" root="P4" fixed="a(P1,P2,P3)">Fester Winkel P5 - P4 mit Größe a(P1,P2,P3)</Angle>
<Circle name="k1" n="6" color="1" hidden="super" through="P5" midpoint="P4" acute="true">Kreis um P4 durch P5</Circle>
<Intersection name="S1" n="7" color="1" target="true" first="w1" second="k1" which="first">Schnitt zwischen w1 und k1</Intersection>
</Objects>
</Macro>
<Macro Name="Default Makros/Drehung mit Winkel (11)">
<Parameter name="P1">Drehzentrum</Parameter>
<Parameter name="P2">zu drehender Punkt</Parameter>
<Comment>
<P>Dreht einen Punkt um einen Winkel. der eingegeben werden
muss.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.221870047543582" y="-0.48177496038034834">Punkt in -4.221870047544, -0.48177496038</Point>
<Point name="P2" n="1" mainparameter="true" x="2.2440570522979413" y="0.45641838351822483">Punkt in 2.244057052298, 0.456418383518</Point>
<Angle name="w1" n="2" hidden="super" first="P2" root="P1" fixed="16">Fester Winkel P2 - P1 mit Größe 16</Angle>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Intersection name="S1" n="4" target="true" first="w1" second="k1" which="first">Schnitt zwischen w1 und k1</Intersection>
</Objects>
<PromptFor object0="w1" prompt0="w1"/>
</Macro>
<Macro Name="Default Makros/Dreieck (34)">
<Parameter name="P1">A</Parameter>
<Parameter name="P2">B</Parameter>
<Parameter name="P3">C</Parameter>
<Comment>
<P>Dreick mit Randstrecken und Füllung</P>
</Comment>
<Objects>
<Point name="P1" n="0" x="-3.00722891566265" y="-1.3975903614457836">Punkt in -3.007228915663, -1.397590361446</Point>
<Point name="P2" n="1" x="1.5421686746987966" y="4.462650602409639">Punkt in 1.542168674699, 4.46265060241</Point>
<Segment name="s1" n="2" target="true" from="P1" to="P2">Strecke von P1 nach P2</Segment>
<Point name="P3" n="3" x="5.590361445783133" y="-3.093975903614458">Punkt in 5.590361445783, -3.093975903614</Point>
<Segment name="s2" n="4" target="true" from="P2" to="P3">Strecke von P2 nach P3</Segment>
<Segment name="s3" n="5" target="true" from="P3" to="P1">Strecke von P3 nach P1</Segment>
<Polygon name="A1" n="6" type="thin" background="true" target="true" decorative="true" point1="P1" point2="P2" point3="P3">Vieleck P1, P2, P3</Polygon>
</Objects>
</Macro>
<Macro Name="Default Makros/Inkreis (3)">
<Parameter name="P2">A</Parameter>
<Parameter name="P3">B</Parameter>
<Parameter name="P1">C</Parameter>
<Comment>
<P>Inkreis von ABC.</P>
</Comment>
<Objects>
<Point name="P1" n="0" x="-4.772616136919315" y="-0.2542787286063568">Punkt in -4.772616136919, -0.254278728606</Point>
<Point name="P2" n="1" x="-1.0953545232273838" y="5.202933985330074">Punkt in -1.095354523227, 5.20293398533</Point>
<Point name="P3" n="2" x="0.8019559902200498" y="-1.036674816625917">Punkt in 0.80195599022, -1.036674816626</Point>
<Line name="g1" n="3" hidden="super" from="P3" to="P1">Gerade durch P3 und P1</Line>
<Ray name="r1" n="4" hidden="super" from="P3" to="P2">Strahl von P3 in Richtung P2</Ray>
<Circle name="k1" n="5" hidden="super" through="P1" midpoint="P3" acute="true">Kreis um P3 durch P1</Circle>
<Intersection name="S1" n="6" hidden="super" first="r1" second="k1" which="first">Schnitt zwischen r1 und k1</Intersection>
<Circle name="k2" n="7" hidden="super" through="P1" midpoint="S1" acute="true">Kreis um S1 durch P1</Circle>
<Circle name="k3" n="8" hidden="super" through="S1" midpoint="P1" acute="true">Kreis um P1 durch S1</Circle>
<Intersection name="S2" n="9" hidden="super" first="k2" second="k3" which="second">Schnitt zwischen k2 und k3</Intersection>
<Intersection name="S3" n="10" hidden="super" first="k3" second="k2" which="second">Schnitt zwischen k3 und k2</Intersection>
<Line name="g2" n="11" hidden="super" from="S2" to="S3">Gerade durch S2 und S3</Line>
<Ray name="r2" n="12" hidden="super" from="P1" to="P3">Strahl von P1 in Richtung P3</Ray>
<Circle name="k4" n="13" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Intersection name="S4" n="14" hidden="super" first="r2" second="k4" which="first">Schnitt zwischen r2 und k4</Intersection>
<Circle name="k5" n="15" hidden="super" through="P2" midpoint="S4" acute="true">Kreis um S4 durch P2</Circle>
<Circle name="k6" n="16" hidden="super" through="S4" midpoint="P2" acute="true">Kreis um P2 durch S4</Circle>
<Intersection name="S5" n="17" hidden="super" first="k5" second="k6" which="second">Schnitt zwischen k5 und k6</Intersection>
<Intersection name="S6" n="18" hidden="super" first="k6" second="k5" which="second">Schnitt zwischen k6 und k5</Intersection>
<Line name="g3" n="19" hidden="super" from="S5" to="S6">Gerade durch S5 und S6</Line>
<Intersection name="S7" n="20" target="true" first="g3" second="g2" shape="circle">Schnitt zwischen g3 und g2</Intersection>
<Plumb name="l1" n="21" hidden="super" point="S7" line="g1" valid="true">Lot durch S7 zu g1</Plumb>
<Intersection name="S8" n="22" hidden="super" first="g1" second="l1" shape="circle">Schnitt zwischen g1 und l1</Intersection>
<Circle name="k7" n="23" target="true" through="S8" midpoint="S7" acute="true">Kreis um S7 durch S8</Circle>
</Objects>
</Macro>
<Macro Name="Default Makros/Koordinatenachsen">
<Parameter name="P1">Zentrum</Parameter>
<Comment>
<P>Erzeugt die Koordiantenachsen, damit diese zur Konstruktion
weiterverwendet werden können. Klicken Sie auf (0,0)!</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="0" actx="0.0" y="0" acty="0.0" shape="circle" fixed="true">Punkt in &quot;0&quot;, &quot;0&quot;</Point>
<Point name="P2" n="1" hidden="super" x="x(P1)+1" actx="1.0" y="y(P1)" acty="0.0" shape="circle" fixed="true">Punkt in &quot;x(P1)+1&quot;, &quot;y(P1)&quot;</Point>
<Line name="g1" n="2" target="true" from="P1" to="P2">Gerade durch P1 und P2</Line>
<Point name="P3" n="3" hidden="super" x="x(P1)" actx="0.0" y="y(P1)+1" acty="1.0" shape="circle" fixed="true">Punkt in &quot;x(P1)&quot;, &quot;y(P1)+1&quot;</Point>
<Line name="g2" n="4" target="true" from="P1" to="P3">Gerade durch P1 und P3</Line>
</Objects>
</Macro>
<Macro Name="Default Makros/Lotfußpunkt (2)">
<Parameter name="g1">Gerade</Parameter>
<Parameter name="P3">Projizieter Punkt</Parameter>
<Comment>
<P>Konstruiert den Lotfußpunkt auf g von P aus.</P>
</Comment>
<Objects>
<Line name="g1" n="0" mainparameter="true">???</Line>
<Point name="P3" n="1" mainparameter="true" x="1.675041876046901" y="2.46566164154104">Punkt in 1.675041876047, 2.465661641541</Point>
<Plumb name="l1" n="2" hidden="super" point="P3" line="g1" valid="true">Lot durch P3 zu g1</Plumb>
<Intersection name="S1" n="3" target="true" first="g1" second="l1">Schnitt zwischen g1 und l1</Intersection>
</Objects>
</Macro>
<Macro Name="Default Makros/Mittelsenkrechte (1)">
<Parameter name="P1">Erster Endpunkt der Strecke</Parameter>
<Parameter name="P2">Zweiter Endpunkt der Strecke</Parameter>
<Comment>
<P>Mittelsenkrechte zwischen P1 und P2.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.225" y="0.012500000000000181">Punkt in -4.225, 0.0125</Point>
<Point name="P2" n="1" mainparameter="true" x="1.4499999999999993" y="1.8125">Punkt in 1.45, 1.8125</Point>
<Circle name="k1" n="2" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Circle name="k2" n="3" hidden="super" through="P1" midpoint="P2" acute="true">Kreis um P2 durch P1</Circle>
<Intersection name="S1" n="4" hidden="super" first="k1" second="k2" which="second">Schnitt zwischen k1 und k2</Intersection>
<Intersection name="S2" n="5" hidden="super" first="k2" second="k1" which="second">Schnitt zwischen k2 und k1</Intersection>
<Line name="g1" n="6" target="true" from="S1" to="S2">Gerade durch S1 und S2</Line>
</Objects>
</Macro>
<Macro Name="Default Makros/Punktspiegelung (7)">
<Parameter name="P1">Spiegelungszentrum</Parameter>
<Parameter name="P2">Gespiegelter Punkt</Parameter>
<Comment>
<P>Spiegelt den Punkt P am Punkt O.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="0.15000000000000038" y="-0.4874999999999998">Punkt in 0.15, -0.4875</Point>
<Point name="P2" n="1" mainparameter="true" x="-2.7800000000000002" y="-2.54">Punkt in -2.78, -2.54</Point>
<Ray name="r1" n="2" hidden="super" from="P2" to="P1">Strahl von P2 in Richtung P1</Ray>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Kreis um P1 durch P2</Circle>
<Intersection name="S1" n="4" target="true" first="r1" second="k1" awayfrom="P2" which="first">Schnitt zwischen r1 und k1</Intersection>
</Objects>
</Macro>
<Macro Name="Default Makros/Schieber">
<Parameter name="P1">Schieberanfangspunkt</Parameter>
<Parameter name="P2">Schieberendpunkt</Parameter>
<Comment>
<P>Schieber für die EIngabe von Zahlen in Intervall [a,b]. Der
eingestellte Ausdruck kann weiter verwendet werden
(eventuell Namen ändern). a und b werden abgefragt.</P>
</Comment>
<Objects>
<Expression name="AD1" n="0" hidden="super" showvalue="true" x="2.666670000000001" y="2.0" value="0" prompt="Wert">Ausdruck &quot;0&quot; in 2.66667, 2.0</Expression>
<Expression name="AD2" n="1" hidden="super" showvalue="true" x="2.68852" y="1.4535500000000001" value="10" prompt="Wert">Ausdruck &quot;10&quot; in 2.68852, 1.45355</Expression>
<Point name="P1" n="2" mainparameter="true" x="-0.9617486338797817" y="1.3224043715847005">Punkt in -0.96174863388, 1.322404371585</Point>
<Point name="P2" n="3" mainparameter="true" x="1.4863400000000009" y="3.77049">Punkt in 1.48634, 3.77049</Point>
<Segment name="s1" n="4" target="true" from="P1" to="P2">Strecke von P1 nach P2</Segment>
<PointOn name="OP3" n="5" type="thick" target="true" on="s1" alpha="0.20682542521125796" x="-0.4554216612227481" y="1.8287307226352645" shape="circle">Punkt auf s1</PointOn>
<Circle name="k1" n="6" hidden="super" fixed="d(P1,P2)/10" midpoint="OP3" acute="true">Kreis um OP3 mit Radius 0.3462118</Circle>
<PointOn name="OP4" n="7" type="invisible" hidden="super" on="k1" alpha="-1.017350822361835" x="-0.27344529522431527" y="1.5342019222576893" shape="circle">Punkt auf k1</PointOn>
<Expression name="AD3" n="8" showvalue="true" x="x(OP4)" y="y(OP4)" value="AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1)" prompt="Wert" fixed="true">Ausdruck &quot;AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1)&quot; in 0.5351573317, 0.2254783395</Expression>
</Objects>
<PromptFor object0="AD1" prompt0="a" object1="AD2" prompt1="b"/>
</Macro>
<Macro Name="Default Makros/Spiegeln am Kreis (6)">
<Parameter name="k">Spiegelungskreis</Parameter>
<Parameter name="P">Gespiegelter Punkt</Parameter>
<Comment>
<P>Spiegelt den Punkt P am Kreis k.</P>
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
<Macro Name="Default Makros/Spiegeln an Gerade (5)">
<Parameter name="g1">Spiegelungsgerade</Parameter>
<Parameter name="P3">Gespiegelter Punkt</Parameter>
<Comment>
<P>Spiegelt den Punkt P an der Geraden  g.</P>
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
<Macro Name="Default Makros/Umkreis (4)">
<Parameter name="P2">A</Parameter>
<Parameter name="P3">B</Parameter>
<Parameter name="P1">C</Parameter>
<Comment>
<P>Kreis durch A, B und C</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-2.3667481662591685" y="0.0977995110024441">Punkt in -2.366748166259, 0.097799511002</Point>
<Point name="P2" n="1" mainparameter="true" x="1.3300733496332526" y="4.107579462102692">Punkt in 1.330073349633, 4.107579462103</Point>
<Point name="P3" n="2" mainparameter="true" x="5.163814180929096" y="-0.3520782396088029">Punkt in 5.163814180929, -0.352078239609</Point>
<Circle name="k1" n="3" hidden="super" through="P3" midpoint="P2" acute="true">Kreis um P2 durch P3</Circle>
<Circle name="k2" n="4" hidden="super" through="P2" midpoint="P3" acute="true">Kreis um P3 durch P2</Circle>
<Intersection name="S1" n="5" hidden="super" first="k1" second="k2" which="second">Schnitt zwischen k1 und k2</Intersection>
<Intersection name="S2" n="6" hidden="super" first="k2" second="k1" which="second">Schnitt zwischen k2 und k1</Intersection>
<Line name="g1" n="7" hidden="super" from="S1" to="S2">Gerade durch S1 und S2</Line>
<Circle name="k3" n="8" hidden="super" through="P1" midpoint="P3" acute="true">Kreis um P3 durch P1</Circle>
<Circle name="k4" n="9" hidden="super" through="P3" midpoint="P1" acute="true">Kreis um P1 durch P3</Circle>
<Intersection name="S3" n="10" hidden="super" first="k3" second="k4" which="second">Schnitt zwischen k3 und k4</Intersection>
<Intersection name="S4" n="11" hidden="super" first="k4" second="k3" which="second">Schnitt zwischen k4 und k3</Intersection>
<Line name="g2" n="12" hidden="super" from="S3" to="S4">Gerade durch S3 und S4</Line>
<Intersection name="S5" n="13" target="true" first="g2" second="g1" shape="circle">Schnitt zwischen g2 und g1</Intersection>
<Circle name="k5" n="14" target="true" through="P2" midpoint="S5" acute="true">Kreis um S5 durch P2</Circle>
</Objects>
</Macro>
<Macro Name="Default Makros/Verschiebung (12)">
<Parameter name="P1">Beginn des Verschiebevektors</Parameter>
<Parameter name="P2">Ende des Verschiebevektors</Parameter>
<Parameter name="P3">zu verschiebender Punkt</Parameter>
<Comment>
<P>Verschiebt P um AB.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-3.3597500000000013" y="-2.8652900000000012">Punkt in -3.35975, -2.86529</Point>
<Point name="P2" n="1" mainparameter="true" x="1.762282091917592" y="-1.4960380348652933">Punkt in 1.762282091918, -1.496038034865</Point>
<Point name="P3" n="2" mainparameter="true" x="-3.917591125198098" y="3.1442155309033297">Punkt in -3.917591125198, 3.144215530903</Point>
<Point name="P4" n="3" target="true" x="x(P3)+x(P2)-x(P1)" actx="1.2044409667194955" y="y(P3)+y(P2)-y(P1)" acty="4.513467496038038" fixed="true">Punkt in &quot;x(P3)+x(P2)-x(P1)&quot;, &quot;y(P3)+y(P2)-y(P1)&quot;</Point>
</Objects>
</Macro>
<Macro Name="Default Makros/Winkelhalbierende (8)">
<Parameter name="P1">Punkt auf dem ersten Schenkel</Parameter>
<Parameter name="P2">Scheitelpunkt</Parameter>
<Parameter name="P3">Punkt auf dem zweiten Schenkel</Parameter>
<Comment>
<P>Winkelhalbierende des Winkels P1P2P3 (Scheitel in P3) als
Gerade.</P>
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
<Macro Name="Default Makros/Winkelhalbierende als Strahl (9)">
<Parameter name="P1">Punkt auf dem ersten Schenkel</Parameter>
<Parameter name="P2">Scheitelpunkt</Parameter>
<Parameter name="P3">Punkt auf dem zweiten Schenkel</Parameter>
<Comment>
<P>Winkelhalbierende des Winkels P1P2P3 (Scheitel in P3) als
Strahl.</P>
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
</CaR>
