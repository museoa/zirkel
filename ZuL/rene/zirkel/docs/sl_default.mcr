<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="Privzeti makroji/Drsnik">
<Parameter name="P1">A</Parameter>
<Parameter name="P2">B</Parameter>
<Comment>
<P>Drsnik na podanem intervalu [a,b]. Izraz se lahko uporablja
v nadaljnih korakih konstrukcije.  Makro bo vpraal po
parametrih a in b .</P>
</Comment>
<Objects>
<Expression name="AD1" n="0" hidden="super" showvalue="true" x="2.6666700000000003" y="2.0" value="0" prompt="Vrednost">Ausdruck &quot;0&quot; in 2.66667, 2.0</Expression>
<Expression name="AD2" n="1" hidden="super" showvalue="true" x="2.68852" y="1.4535500000000001" value="10" prompt="Vrednost">Ausdruck &quot;10&quot; in 2.68852, 1.45355</Expression>
<Point name="P1" n="2" mainparameter="true" x="-0.9617486338797817" y="1.3224043715846998">Punkt in -0.96175, 1.3224</Point>
<Point name="P2" n="3" mainparameter="true" x="1.4863400000000002" y="3.77049">Punkt in 1.48634, 3.77049</Point>
<Segment name="s1" n="4" target="true" from="P1" to="P2">Daljica od A do B</Segment>
<PointOn name="OP3" n="5" type="thick" target="true" on="s1" alpha="0.20682542521125796" x="-0.4554216612227482" y="1.8287307226352638" shape="circle">Punkt auf s1</PointOn>
<Circle name="k1" n="6" hidden="super" fixed="d(P1,P2)/10" midpoint="OP3" acute="true">Krožnica s središčem v OP3 s polmerom 0.3462118</Circle>
<PointOn name="OP4" n="7" type="invisible" hidden="super" on="k1" alpha="-1.017350822361835" x="-0.27344529522431543" y="1.5342019222576886" shape="circle">Punkt auf k1</PointOn>
<Expression name="AD3" n="8" showvalue="true" x="x(OP4)" y="y(OP4)" value="AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1)" prompt="Vrednost" fixed="true">Ausdruck &quot;AD1+d(P1,OP3)/d(P1,P2)*(AD2-AD1)&quot; in 0.53516, 0.22548</Expression>
</Objects>
<PromptFor object0="AD1" prompt0="a" object1="AD2" prompt1="b"/>
</Macro>
<Macro Name="Privzeti makroji/Pravokotna projekcija točke na premico (2)">
<Parameter name="l1">p</Parameter>
<Parameter name="P4">A</Parameter>
<Comment>
<P>Konstruira pravokotno projekcijo točke na dano premico</P>
</Comment>
<Objects>
<Line name="l1" n="0" mainparameter="true">???</Line>
<Point name="P4" n="1" mainparameter="true" x="-1.176938369781312" y="3.045725646123261">Punkt in -1.17694, 3.04573</Point>
<Plumb name="pl1" n="2" hidden="true" point="P4" line="l1" valid="true">Pravokotnica skozi P4 na l1</Plumb>
<Intersection name="I1" n="3" target="true" first="l1" second="pl1">Presečišče l1 in pl1</Intersection>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Simetrala daljice (1)">
<Parameter name="P1">P1</Parameter>
<Parameter name="P2">P2</Parameter>
<Comment>
<P>S tem makrojem konstruiramo simetralo daljice, podane z
dvema točkama.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-3.9125248508946324" y="0.08747514910536759">Punkt in -3.91252, 0.08748</Point>
<Point name="P2" n="1" mainparameter="true" x="1.5745526838966202" y="0.13518886679920428">Punkt in 1.57455, 0.13519</Point>
<Segment name="s1" n="2" hidden="true" from="P1" to="P2">Daljica od P1 do P2</Segment>
<Midpoint name="M1" n="3" hidden="true" first="P1" second="P2">Središče daljice, določene s točkama P1 in P2</Midpoint>
<Plumb name="pl1" n="4" target="true" point="M1" line="s1" valid="true">Pravokotnica skozi M1 na s1</Plumb>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Simetrala kota (8)">
<Parameter name="P1">P1</Parameter>
<Parameter name="P2">P2</Parameter>
<Parameter name="P3">P3</Parameter>
<Comment>
<P>Konstruira simetralo poljubnega kota, če so dane tri točke
(krak, vrh, krak)</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="2.7992047713717705" y="-3.0934393638170974">Punkt in 2.7992, -3.09344</Point>
<Point name="P2" n="1" mainparameter="true" x="-2.0198807157057654" y="-3.0139165009940365">Punkt in -2.01988, -3.01392</Point>
<Point name="P3" n="2" mainparameter="true" x="-3.62624" y="0.31014">Punkt in -3.62624, 0.31014</Point>
<Ray name="r1" n="3" hidden="true" from="P2" to="P1">Poltrak z vrhom v P2 v smeri P1</Ray>
<Ray name="r2" n="4" hidden="true" from="P2" to="P3">Poltrak z vrhom v P2 v smeri P3</Ray>
<PointOn name="OP4" n="5" hidden="true" on="r1" alpha="1.0339189076101178" x="-0.986102549716835" y="-3.030975546637418">Punkt auf r1</PointOn>
<Circle name="c1" n="6" hidden="true" through="OP4" midpoint="P2">Krožnica s središčem v točki P2 skozi točko OP4</Circle>
<Intersection name="I1" n="7" hidden="true" first="r2" second="c1" which="first">Presečišče r2 in c1</Intersection>
<Circle name="c2" n="8" hidden="true" through="I1" midpoint="OP4">Krožnica s središčem v točki OP4 skozi točko I1</Circle>
<Circle name="c3" n="9" hidden="true" through="OP4" midpoint="I1">Krožnica s središčem v točki I1 skozi točko OP4</Circle>
<Intersection name="I2" n="10" hidden="true" first="c3" second="c2" which="first">Presečišče c3 in c2</Intersection>
<Intersection name="I3" n="11" hidden="true" first="c3" second="c2" which="second">Presečišče c3 in c2</Intersection>
<Line name="l1" n="12" target="true" from="I2" to="I3">Premica skozi I2 in I3</Line>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Simetrala kota (9)">
<Parameter name="P1">A</Parameter>
<Parameter name="P2">V</Parameter>
<Parameter name="P3">B</Parameter>
<Comment>
<P>Konstruira simetralo kota kot poltrak in pri tem upošteva
usmerjenost kota.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="3.3240556660039773" y="-2.15506958250497">Punkt in 3.32406, -2.15507</Point>
<Point name="P2" n="1" mainparameter="true" x="-0.286282306163022" y="-1.9642147117296223">Punkt in -0.28628, -1.96421</Point>
<Point name="P3" n="2" mainparameter="true" x="-4.40557" y="0.85089">Punkt in -4.40557, 0.85089</Point>
<Ray name="r1" n="3" hidden="true" from="P2" to="P1">Poltrak z vrhom v P2 v smeri P1</Ray>
<Ray name="r2" n="4" hidden="true" from="P2" to="P3">Poltrak z vrhom v P2 v smeri P3</Ray>
<Circle name="c1" n="5" hidden="true" fixed="2" midpoint="P2">Krožnica s središčem v točki P2 s polmerom 2.0</Circle>
<Intersection name="I1" n="6" hidden="true" first="r2" second="c1" which="first">Presečišče r2 in c1</Intersection>
<Intersection name="I2" n="7" hidden="true" first="r1" second="c1" which="first">Presečišče r1 in c1</Intersection>
<Midpoint name="M1" n="8" hidden="true" first="I1" second="P2">Središče daljice, določene s točkama I1 in P2</Midpoint>
<Midpoint name="M2" n="9" hidden="true" first="P2" second="I2">Središče daljice, določene s točkama P2 in I2</Midpoint>
<Circle3 name="c2" n="10" hidden="true" from="I1" to="P2" midpoint="M1">Krožnica s središčem v točki M1 s polmerom od I1 do P2</Circle3>
<Circle3 name="c3" n="11" hidden="true" from="P2" to="I2" midpoint="M2">Krožnica s središčem v točki M2 s polmerom od P2 do I2</Circle3>
<Intersection name="I3" n="12" hidden="true" first="c2" second="c3" which="second">Presečišče c2 in c3</Intersection>
<Ray name="r3" n="13" target="true" from="P2" to="I3">Poltrak z vrhom v P2 v smeri I3</Ray>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Vrtež (10)">
<Parameter name="P1">A</Parameter>
<Parameter name="P2">V</Parameter>
<Parameter name="P3">B</Parameter>
<Parameter name="P5">O</Parameter>
<Parameter name="P6">T</Parameter>
<Comment>
<P>Zavrti dano točko T okrog točke O za dani kot AVB. Najprej
določimo kot AVB, nato os vrtenja O, nato še točko T, ki jo
vrtimo.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="4.6918489065606375" y="-3.4592445328031802">Punkt in 4.69185, -3.45924</Point>
<Point name="P2" n="1" mainparameter="true" x="1.2882703777335982" y="-3.395626242544732">Punkt in 1.28827, -3.39563</Point>
<Point name="P3" n="2" mainparameter="true" x="2.2584500000000003" y="-0.54871">Punkt in 2.25845, -0.54871</Point>
<Ray name="r1" n="3" hidden="true" from="P2" to="P1">Poltrak z vrhom v P2 v smeri P1</Ray>
<Ray name="r2" n="4" hidden="true" from="P2" to="P3">Poltrak z vrhom v P2 v smeri P3</Ray>
<Point name="P5" n="5" mainparameter="true" x="-4.914512922465208" y="3.284294234592445">Punkt in -4.91451, 3.28429</Point>
<Point name="P6" n="6" mainparameter="true" x="-6.139165009940358" y="0.6759443339960233">Punkt in -6.13917, 0.67594</Point>
<Circle name="c1" n="7" hidden="true" fixed="2" midpoint="P2">Krožnica s središčem v točki P2 s polmerom 2.0</Circle>
<Ray name="r3" n="8" hidden="true" from="P5" to="P6">Poltrak z vrhom v P5 v smeri P6</Ray>
<Circle name="c2" n="9" hidden="true" fixed="2" midpoint="P5">Krožnica s središčem v točki P5 s polmerom 2.0</Circle>
<Intersection name="I1" n="10" hidden="true" first="r3" second="c2" which="first">Presečišče r3 in c2</Intersection>
<Intersection name="I2" n="11" hidden="true" first="r2" second="c1" which="first">Presečišče r2 in c1</Intersection>
<Intersection name="I3" n="12" hidden="true" first="r1" second="c1" which="first">Presečišče r1 in c1</Intersection>
<Circle3 name="c3" n="13" hidden="true" from="I3" to="I2" midpoint="I1">Krožnica s središčem v točki I1 s polmerom od I3 do I2</Circle3>
<Intersection name="I4" n="14" hidden="true" first="c3" second="c2" which="first">Presečišče c3 in c2</Intersection>
<Ray name="r4" n="15" hidden="true" from="P5" to="I4">Poltrak z vrhom v P5 v smeri I4</Ray>
<Circle name="c4" n="16" hidden="true" through="P6" midpoint="P5">Krožnica s središčem v točki P5 skozi točko P6</Circle>
<Intersection name="I5" n="17" target="true" first="r4" second="c4" which="first">Presečišče r4 in c4</Intersection>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Vrtež z danim kotom (11)">
<Parameter name="P1">O</Parameter>
<Parameter name="P2">A</Parameter>
<Comment>
<P>Zavrti dano točko A okrog točke O za dani kot.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.221870047543582" y="-0.48177496038034834">Punkt in -4.22187, -0.48177</Point>
<Point name="P2" n="1" mainparameter="true" x="2.244057052297941" y="0.4564183835182246">Punkt in 2.24406, 0.45642</Point>
<Angle name="kot" n="2" hidden="super" first="P2" root="P1" fixed="16">Fester Winkel P2 - P1 mit Größe 16</Angle>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Krožnica s središčem P1 skozi P2</Circle>
<Intersection name="S1" n="4" target="true" first="kot" second="k1" which="first">Presečišče med krakom kota in k1</Intersection>
</Objects>
<PromptFor object0="kot" prompt0="kot"/>
</Macro>
<Macro Name="Privzeti makroji/Vzporedni premik (12)">
<Parameter name="P1">A</Parameter>
<Parameter name="P2">B</Parameter>
<Parameter name="P4">T</Parameter>
<Comment>
<P>Vzporedno premakne točko za dani vektor AB.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-1.4314115308151092" y="-2.9343936381709743">Punkt in -1.43141, -2.93439</Point>
<Point name="P2" n="1" mainparameter="true" x="1.3677932405566597" y="-0.6282306163021866">Punkt in 1.36779, -0.62823</Point>
<Segment name="s1" n="2" hidden="true" from="P1" to="P2">Daljica od P1 do P2</Segment>
<Point name="P4" n="3" mainparameter="true" x="-3.0377733598409544" y="0.24652087475149112">Punkt in -3.03777, 0.24652</Point>
<Parallel name="p1" n="4" hidden="true" point="P4" line="s1">Vzporednica skozi P4 k s1</Parallel>
<Circle3 name="c1" n="5" hidden="true" from="P1" to="P2" midpoint="P4">Krožnica s središčem v točki P4 s polmerom od P1 do P2</Circle3>
<Intersection name="I1" n="6" target="true" first="p1" second="c1" which="first">Presečišče p1 in c1</Intersection>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Zrcaljenje točke čez krožnico (6)">
<Parameter name="c1">k</Parameter>
<Parameter name="P3">A</Parameter>
<Comment>
<P>Prezrcali točko A čez dano krožnico k.</P>
</Comment>
<Objects>
<Point name="P1" n="0" x="-2.2743538767395632" y="-0.32604373757455285">Punkt in -2.27435, -0.32604</Point>
<Circle name="c1" n="1" midpoint="P1">???</Circle>
<Point name="P3" n="2" x="2.0835" y="-0.37376000000000004">Punkt in 2.0835, -0.37376</Point>
<Ray name="r1" n="3" hidden="true" from="P1" to="P3">Poltrak z vrhom v P1 v smeri P3</Ray>
<Plumb name="pl1" n="4" hidden="true" point="P3" line="r1" valid="true">Pravokotnica skozi P3 na r1</Plumb>
<Intersection name="I1" n="5" hidden="true" first="pl1" second="c1" which="first">Presečišče pl1 in c1</Intersection>
<Intersection name="I2" n="6" hidden="true" first="pl1" second="c1" which="second">Presečišče pl1 in c1</Intersection>
<Segment name="s1" n="7" hidden="true" from="P1" to="I1">Daljica od P1 do I1</Segment>
<Segment name="s2" n="8" hidden="true" from="P1" to="I2">Daljica od P1 do I2</Segment>
<Plumb name="pl2" n="9" hidden="true" point="I1" line="s1" valid="true">Pravokotnica skozi I1 na s1</Plumb>
<Plumb name="pl3" n="10" hidden="true" point="I2" line="s2" valid="true">Pravokotnica skozi I2 na s2</Plumb>
<Intersection name="I3" n="11" target="true" first="pl2" second="pl3">Presečišče pl2 in pl3</Intersection>
<Midpoint name="M1" n="12" hidden="true" first="P3" second="P1">Središče daljice, določene s točkama P3 in P1</Midpoint>
<Circle name="c2" n="13" hidden="true" through="P1" midpoint="M1" acute="true">Krožnica s središčem v točki M1 skozi točko P1</Circle>
<Intersection name="I4" n="14" hidden="true" first="c1" second="c2" which="first">Presečišče c1 in c2</Intersection>
<Intersection name="I5" n="15" hidden="true" first="c1" second="c2" which="second">Presečišče c1 in c2</Intersection>
<Segment name="s3" n="16" hidden="true" from="I5" to="I4">Daljica od I5 do I4</Segment>
<Intersection name="I6" n="17" target="true" first="r1" second="s3">Presečišče r1 in s3</Intersection>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Zrcaljenje točke čez premico (5)">
<Parameter name="l1">l1</Parameter>
<Parameter name="P3">P3</Parameter>
<Comment>
<P>K dani točki in premici, čez katero zrcalimo, konstruira
zrcalno točko.</P>
</Comment>
<Objects>
<Line name="l1" n="0" mainparameter="true">???</Line>
<Point name="P3" n="1" mainparameter="true" x="-2.974155069582505" y="-0.00795228628230582">Punkt in -2.97416, -0.00795</Point>
<Plumb name="pl1" n="2" hidden="true" point="P3" line="l1" valid="true">Pravokotnica skozi P3 na l1</Plumb>
<Intersection name="I1" n="3" hidden="true" first="l1" second="pl1">Presečišče l1 in pl1</Intersection>
<Circle name="c1" n="4" hidden="true" through="P3" midpoint="I1">Krožnica s središčem v točki I1 skozi točko P3</Circle>
<Intersection name="I2" n="5" target="true" first="pl1" second="c1" awayfrom="P3" which="first">Presečišče pl1 in c1</Intersection>
</Objects>
</Macro>
<Macro Name="Privzeti makroji/Zrcaljenje točke čez točko (7)">
<Parameter name="P1">T</Parameter>
<Parameter name="P2">A</Parameter>
<Comment>
<P>Konstruira zrcalno sliko neke točke glede na dano točko.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-0.8906560636182902" y="0.2783300198807153">Punkt in -0.89066, 0.27833</Point>
<Point name="P2" n="1" mainparameter="true" x="-3.6898608349900606" y="0.357852882703777">Punkt in -3.68986, 0.35785</Point>
<Line name="l1" n="2" hidden="true" from="P2" to="P1">Premica skozi P2 in P1</Line>
<Circle name="c1" n="3" hidden="true" through="P2" midpoint="P1">Krožnica s središčem v točki P1 skozi točko P2</Circle>
<Intersection name="I1" n="4" target="true" first="l1" second="c1" awayfrom="P2" which="first">Presečišče l1 in c1</Intersection>
</Objects>
</Macro>
</CaR>
